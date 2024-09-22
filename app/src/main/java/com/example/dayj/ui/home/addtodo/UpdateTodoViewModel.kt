package com.example.dayj.ui.home.addtodo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.alarm.AlarmCenter
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.domain.usecase.plan.UpdatePlanUseCase
import com.example.dayj.ext.formatLocalTime
import com.example.dayj.network.api.response.PlanOptionRequest
import com.example.dayj.network.api.response.PlanResponse
import com.example.dayj.network.api.response.PlanTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import com.example.dayj.util.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class UpdateTodoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    loginAuthorizationRepository: LoginAuthorizationRepository,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val alarmCenter: AlarmCenter
) : ViewModel() {

    private val passItem = savedStateHandle.get<PlanResponse>("passItem")

    private val getUserId = loginAuthorizationRepository.userInfoFlow.map { it?.id ?: 1 }

    private val _todoViewState = MutableStateFlow(TodoViewState())
    val updateTodoViewState = _todoViewState.asStateFlow()

    private val _todoViewEffect = MutableSharedFlow<TodoViewEffect>()
    val updateTodoViewEffect = _todoViewEffect.asSharedFlow()

    init {
        passItem?.let { item ->
            _todoViewState.update {
                it.copy(
                    planRequest = item.toPlanRequest(),
                    planOptionRequest = item.toPlanOptionRequest()
                )
            }
        }
    }

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
        Log.d("결과-updatePlanOption", planOptionRequest.toString())
        _todoViewState.update {
            it.copy(planOptionRequest = planOptionRequest)
        }
    }

    fun update() {
        if (_todoViewState.value.isNeedCreateRequirements()) {
            val planRequest = _todoViewState.value.planRequest
            val planOptionRequest =
                if (_todoViewState.value.planOptionRequest.planStartTime == null && _todoViewState.value.planOptionRequest.planEndTime == null) {
                    _todoViewState.value.planOptionRequest.copy(
                        planStartTime = formatLocalTime(LocalTime.of(0, 0, 0)),
                        planEndTime = formatLocalTime(LocalTime.of(1, 0, 0)),
                    )
                } else {
                    _todoViewState.value.planOptionRequest
                }

            viewModelScope.launch {
                passItem?.let { item ->
                    updatePlanUseCase(
                        userId = getUserId.first(),
                        planId = item.id.toInt(),
                        planRequest,
                        planOptionRequest
                    ).onEach { result ->
                        when (result) {
                            is Result.Fail.Exception -> {
                                Log.d("결과-update", planOptionRequest.toString())
                                //todo 확인필요. 성공시 왜
                                //Exception(errorCode=0, message=Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $, payload=) 이렇게 떨어지는지.
                                if (result.errorCode == 0) {
                                    val response = _todoViewState.value.toPlanResponse(id = "1")
                                    if(response.isRegisterAlarm()) {
                                        alarmCenter.register(response)
                                    }
                                    _todoViewEffect.emit(TodoViewEffect.ShowToast("저장되었습니다."))
                                    _todoViewEffect.emit(TodoViewEffect.CompleteSave)
                                } else {
                                    _todoViewEffect.emit(TodoViewEffect.ShowToast("저장을 실패하였습니다."))
                                }
                            }

                            is Result.Success -> {
                                Log.d("결과", result.data.toString())
                                if(result.data.isRegisterAlarm()) {
                                    alarmCenter.register(result.data)
                                }
                                _todoViewEffect.emit(TodoViewEffect.ShowToast("저장되었습니다."))
                                _todoViewEffect.emit(TodoViewEffect.CompleteSave)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

        }
    }
}