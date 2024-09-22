package com.example.dayj.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.alarm.AlarmCenter
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.domain.usecase.plan.DeletePlanUseCase
import com.example.dayj.domain.usecase.plan.GetPlansUseCase
import com.example.dayj.domain.usecase.plan.UpdatePlanUseCase
import com.example.dayj.ext.toConvertPlanDate
import com.example.dayj.network.api.response.PlanResponse
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
class HomeViewModel @Inject constructor(
    loginAuthorizationRepository: LoginAuthorizationRepository,
    private val getPlansUseCase: GetPlansUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val alarmCenter: AlarmCenter
) : ViewModel() {

    private val getUserId = loginAuthorizationRepository.userInfoFlow.map { it?.id ?: 1 }

    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState = _homeViewState.asStateFlow()

    fun updateDate(date: LocalDate) {
        _homeViewState.update {
            it.copy(selectedData = date)
        }
        viewModelScope.launch {
            getPlans(date = date)
        }
    }

    fun getPlans(date: LocalDate) {
        viewModelScope.launch {
            getPlansUseCase(
                userId = getUserId.first(),
                date = date.toConvertPlanDate()
            ).onEach { result ->
                when (result) {
                    is Result.Fail.Exception -> {
                        //빈값이면 404 로 떨어지고 있음
                        Log.d("결과-getPlans", result.toString())
                        _homeViewState.update {
                            it.copy(planList = emptyList())
                        }
                    }

                    is Result.Success -> {
                        _homeViewState.update {
                            it.copy(
                                planList = result.data.onChangedSortTodoList(),
                                selectTag = PlanTag.ALL
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    fun changeSelectPlanTag(tag: PlanTag) {
        _homeViewState.update {
            it.copy(selectTag = tag)
        }
    }

    fun updatePlanCheck(plan: PlanResponse) {
        viewModelScope.launch {
            updatePlanUseCase(
                userId = getUserId.first(),
                planId = plan.id.toInt(),
                planRequest = plan.toPlanRequest().copy(isComplete = !plan.isComplete),
                planOptionRequest = plan.toPlanOptionRequest(),
            ).onEach { result ->
                when (result) {
                    is Result.Fail.Exception -> {
                        Log.d("결과-updatePlanCheck", result.toString())
                    }

                    is Result.Success -> {
                        val currentPlanList = _homeViewState.value.planList
                        val newPlanList = _homeViewState.value.planList.toMutableList()

                        currentPlanList.firstOrNull { it.id == plan.id }?.let {
                            newPlanList[currentPlanList.indexOf(it)] =
                                it.copy(isComplete = !it.isComplete)

                            val convertItem = newPlanList[currentPlanList.indexOf(it)]

                            if (convertItem.isRegisterAlarm()) {
                                alarmCenter.register(convertItem)
                            } else {
                                alarmCenter.cancel(convertItem)
                            }

                            _homeViewState.update {
                                it.copy(planList = newPlanList.onChangedSortTodoList())
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    fun deletePlan(item: PlanResponse) {
        viewModelScope.launch {
            deletePlanUseCase(
                userId = getUserId.first(),
                planId = item.id.toInt()
            ).onEach { result ->
                when (result) {
                    is Result.Fail.Exception -> {
                        Log.d("결과-deletePlan", result.toString())
                    }

                    is Result.Success -> {
                        alarmCenter.cancel(item)
                        getPlans(_homeViewState.value.selectedData)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun List<PlanResponse>.onChangedSortTodoList(): List<PlanResponse> =
        (listOf(
            false,
            true
        ).flatMap { isComplete ->
            filter { it.isComplete == isComplete }
                .sortedBy { it.planOption.planStartTime }
        })
}