package com.dayj.dayj.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.domain.usecase.statistics.GetStatisticsUseCase
import com.dayj.dayj.ext.LocalDateFormat
import com.dayj.dayj.ext.formatLocalDate
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getStatisticsUseCase: GetStatisticsUseCase
) : ViewModel() {

    private val _statisticsViewState = MutableStateFlow(StatisticsViewState())
    val statisticsViewState = _statisticsViewState.asStateFlow()

    init {
        getStatistics()
    }

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

    private fun getStatistics() {

        val startDate =
            formatLocalDate(_statisticsViewState.value.startDate, LocalDateFormat.STATISTIC_FORMAT)
        val endDate = formatLocalDate(
            _statisticsViewState.value.startDate.plusDays(7),
            LocalDateFormat.STATISTIC_FORMAT
        )

        getStatisticsUseCase(
            userId = 1,
            startDate = startDate,
            endDate = endDate,
            tag = _statisticsViewState.value.getPlanTag()
        ).onEach { result ->
            when (result) {
                is Result.Fail.Exception -> {
                    Log.d("결과", result.toString())
                    _statisticsViewState.update {
                        it.copy(statistics = emptyList())
                    }
                }

                is Result.Success -> {
                    _statisticsViewState.update {
                        it.copy(statistics = result.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}