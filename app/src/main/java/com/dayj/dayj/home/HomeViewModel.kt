package com.dayj.dayj.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.alarm.AlarmCenter
import com.dayj.dayj.domain.usecase.plan.DeletePlanUseCase
import com.dayj.dayj.domain.usecase.plan.GetPlansUseCase
import com.dayj.dayj.domain.usecase.plan.UpdatePlanUseCase
import com.dayj.dayj.ext.formatLocalDateTime
import com.dayj.dayj.ext.toConvertPlanDate
import com.dayj.dayj.network.api.response.PlanOption
import com.dayj.dayj.network.api.response.PlanResponse
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlansUseCase: GetPlansUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
    private val alarmCenter: AlarmCenter
) : ViewModel() {

    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState = _homeViewState.asStateFlow()

    fun updateDate(date: LocalDate) {
        _homeViewState.update {
            it.copy(selectedData = date)
        }
        getPlans(date = date)
    }

    fun getPlans(userId: Int = 1, date: LocalDate) {
        getPlansUseCase(userId = userId, date = date.toConvertPlanDate()).onEach { result ->
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

    fun changeSelectPlanTag(tag: PlanTag) {
        _homeViewState.update {
            it.copy(selectTag = tag)
        }
    }

    fun updatePlanCheck(plan: PlanResponse) {
        updatePlanUseCase(
            userId = 1,
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

    fun deletePlan(item: PlanResponse) {
        deletePlanUseCase(userId = 1, planId = item.id.toInt()).onEach { result ->
            when (result) {
                is Result.Fail.Exception -> {
                    Log.d("결과-deletePlan", result.toString())
                }

                is Result.Success -> {
                    alarmCenter.cancel(item)
                    getPlans(userId = 1, _homeViewState.value.selectedData)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun List<PlanResponse>.onChangedSortTodoList() : List<PlanResponse> =
        (listOf(
            false,
            true
        ).flatMap { isComplete ->
            filter { it.isComplete == isComplete }
                .sortedBy { it.planOption.planStartTime }
        })
}