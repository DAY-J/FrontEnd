package com.example.dayj.ui.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.domain.usecase.statistics.GetStatisticsUseCase
import com.example.dayj.ext.LocalDateFormat
import com.example.dayj.ext.formatLocalDate
import com.example.dayj.network.api.response.PlanTag
import com.example.dayj.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    private val getStatisticsUseCase: GetStatisticsUseCase
) : ViewModel() {

    private val getUserId = loginAuthorizationRepository.userInfoFlow.map { it?.id ?: 1 }

    private val _statisticsViewState = MutableStateFlow(StatisticsViewState())
    val statisticsViewState = _statisticsViewState.asStateFlow()

    fun updatePlanTag(tag: PlanTag) {
        _statisticsViewState.update {
            it.copy(tag = tag)
        }
        getStatistics()
    }

    fun updateStartDate(
        startDate: LocalDate,
    ) {
        _statisticsViewState.update {
            it.copy(startDate = startDate)
        }
        getStatistics()
    }

    fun getStatistics() {

        val startDate =
            formatLocalDate(_statisticsViewState.value.startDate, LocalDateFormat.STATISTIC_FORMAT)
        val endDate = formatLocalDate(
            _statisticsViewState.value.startDate.plusDays(6),
            LocalDateFormat.STATISTIC_FORMAT
        )

        viewModelScope.launch {
            getStatisticsUseCase(
                userId = getUserId.first(),
                startDate = startDate,
                endDate = endDate,
                tag = _statisticsViewState.value.getPlanTag()
            ).onEach { result ->
                when (result) {
                    is Result.Fail.Exception -> {
                        Log.d("결과-getStatistics", result.toString())
                        _statisticsViewState.update {
                            it.copy(statistics = emptyList())
                        }
                    }

                    is Result.Success -> {
                        Log.d("결과-getStatistics", result.data.toString())
                        _statisticsViewState.update {
                            it.copy(statistics = result.data)
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}