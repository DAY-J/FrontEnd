package com.dayj.dayj.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.domain.usecase.plan.DeletePlanUseCase
import com.dayj.dayj.domain.usecase.plan.GetPlansUseCase
import com.dayj.dayj.domain.usecase.plan.UpdatePlanUseCase
import com.dayj.dayj.ext.toConvertPlanDate
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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlansUseCase: GetPlansUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase
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
                    _homeViewState.update {
                        it.copy(planList = emptyList())
                    }
                }

                is Result.Success -> {
                    _homeViewState.update {
                        it.copy(planList = result.data, selectTag = PlanTag.ALL)
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

                }

                is Result.Success -> {
                    val currentPlanList = _homeViewState.value.planList
                    val newPlanList = _homeViewState.value.planList.toMutableList()

                    currentPlanList.firstOrNull { it.id == plan.id }?.let {
                        newPlanList[currentPlanList.indexOf(it)] =
                            it.copy(isComplete = !it.isComplete)

                        _homeViewState.update {
                            it.copy(planList = newPlanList)
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

                }

                is Result.Success -> {
                    getPlans(userId = 1, _homeViewState.value.selectedData)
                }
            }
        }.launchIn(viewModelScope)
    }
}