package com.dayj.dayj.home.addtodo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.domain.usecase.plan.CreatePlanUseCase
import com.dayj.dayj.network.api.response.PlanOptionRequest
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val createPlanUseCase: CreatePlanUseCase,
) : ViewModel() {

    private val _todoViewState = MutableStateFlow(TodoViewState())
    val addTodoViewState = _todoViewState.asStateFlow()

    private val _todoViewEffect = MutableSharedFlow<TodoViewEffect>()
    val addTodoViewEffect = _todoViewEffect.asSharedFlow()


    fun updateGoal(goal: String) {
        _todoViewState.update {
            it.copy(planRequest = _todoViewState.value.planRequest.copy(goal = goal))
        }
    }

    fun updatePlanTag(planTag: PlanTag) {
        _todoViewState.update {
            it.copy(planRequest = _todoViewState.value.planRequest.copy(planTag = planTag.name))
        }
    }

    fun updateIsPublic(isPublic: Boolean) {
        _todoViewState.update {
            it.copy(planRequest = _todoViewState.value.planRequest.copy(isPublic = isPublic))
        }
    }

    fun updatePlanOption(planOptionRequest: PlanOptionRequest) {
        Log.d("결과", planOptionRequest.toString())
        _todoViewState.update {
            it.copy(planOptionRequest = planOptionRequest)
        }
    }

    fun save() {
        if (_todoViewState.value.isNeedCreateRequirements()) {
            val planRequest = _todoViewState.value.planRequest
            val planOptionRequest = _todoViewState.value.planOptionRequest
            createPlanUseCase(
                userId = 1,
                planRequest,
                planOptionRequest
            ).onEach { result ->
                when (result) {
                    is Result.Fail.Exception -> {
                        Log.d("결과", result.toString())
                        //todo 확인필요. 성공시 왜
                        //Exception(errorCode=0, message=Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $, payload=) 이렇게 떨어지는지.
                        if (result.errorCode == 0) {
                            _todoViewEffect.emit(TodoViewEffect.ShowToast("저장되었습니다."))
                            _todoViewEffect.emit(TodoViewEffect.CompleteSave)
                        } else {
                            _todoViewEffect.emit(TodoViewEffect.ShowToast("저장을 실패하였습니다."))
                        }
                    }

                    is Result.Success -> {
                        _todoViewEffect.emit(TodoViewEffect.ShowToast("저장되었습니다."))
                        _todoViewEffect.emit(TodoViewEffect.CompleteSave)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}