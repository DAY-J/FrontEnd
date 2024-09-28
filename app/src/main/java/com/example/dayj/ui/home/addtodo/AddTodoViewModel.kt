package com.example.dayj.ui.home.addtodo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.alarm.AlarmCenter
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.domain.usecase.plan.CreatePlanUseCase
import com.example.dayj.domain.usecase.plan.GetRecommendPlanTagUseCase
import com.example.dayj.ext.formatLocalDateTime
import com.example.dayj.network.api.response.PlanOptionRequest
import com.example.dayj.network.api.response.PlanTag
import com.example.dayj.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    loginAuthorizationRepository: LoginAuthorizationRepository,
    private val createPlanUseCase: CreatePlanUseCase,
    private val recommendPlanTagUseCase: GetRecommendPlanTagUseCase,
    private val alarmCenter: AlarmCenter
) : ViewModel() {

    private val _todoViewState = MutableStateFlow(TodoViewState())
    val addTodoViewState = _todoViewState.asStateFlow()

    private val _todoViewEffect = MutableSharedFlow<TodoViewEffect>()
    val addTodoViewEffect = _todoViewEffect.asSharedFlow()

    private val selectDate = savedStateHandle.get<String>("selectDate")

    private val getUserId = loginAuthorizationRepository.userInfoFlow.map { it?.id ?: 1 }

    init {
        updatePlanTag(PlanTag.HEALTH)
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
        viewModelScope.launch {
            recommendPlanTagUseCase(
                userId = getUserId.first(),
                planTag = planTag
            ).onEach { result ->
                when (result) {
                    is Result.Fail.Exception -> {
                        Log.d("결과-recommendPlanTag", result.toString())
                        _todoViewState.update {
                            it.copy(recommendTodoList = emptyList())
                        }
                    }

                    is Result.Success -> {
                        _todoViewState.update {
                            it.copy(recommendTodoList = result.data)
                        }
                    }
                }
            }.launchIn(viewModelScope)
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

    fun save() {
        if (_todoViewState.value.isNeedCreateRequirements()) {
            val planRequest = _todoViewState.value.planRequest
            var planOptionRequest = _todoViewState.value.planOptionRequest

            if (planOptionRequest.planStartTime == null && planOptionRequest.planEndTime == null) {
                val startDateTime =
                    LocalDate.parse(selectDate, DateTimeFormatter.ISO_DATE).atTime(0, 0, 0)
                val endDateTime =
                    LocalDate.parse(selectDate, DateTimeFormatter.ISO_DATE).atTime(1, 0, 0)
                planOptionRequest = _todoViewState.value.planOptionRequest.copy(
                    planStartTime = formatLocalDateTime(startDateTime),
                    planEndTime = formatLocalDateTime(endDateTime)
                )
            } else {

                val selectDate = LocalDate.parse(selectDate, DateTimeFormatter.ISO_DATE)

                val convertStartDateTime =
                    LocalDateTime.parse(_todoViewState.value.planOptionRequest.planStartTime)
                        .withYear(selectDate.year).withMonth(selectDate.monthValue)
                        .withDayOfMonth(selectDate.dayOfMonth)
                val convertEndDateTime =
                    LocalDateTime.parse(_todoViewState.value.planOptionRequest.planEndTime)
                        .withYear(selectDate.year).withMonth(selectDate.monthValue)
                        .withDayOfMonth(selectDate.dayOfMonth)

                planOptionRequest = _todoViewState.value.planOptionRequest.copy(
                    planStartTime = formatLocalDateTime(convertStartDateTime),
                    planEndTime = formatLocalDateTime(convertEndDateTime)
                )
            }

            viewModelScope.launch {
                createPlanUseCase(
                    userId = getUserId.first(),
                    planRequest,
                    planOptionRequest
                ).onEach { result ->
                    when (result) {
                        is Result.Fail.Exception -> {
                            Log.d("결과-save", result.toString())
                            //todo 확인필요. 성공시 왜
                            //Exception(errorCode=0, message=Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $, payload=) 이렇게 떨어지는지.
                            if (result.errorCode == 0) {

                                val response = _todoViewState.value.toPlanResponse(id = "1")

                                if (response.isRegisterAlarm()) {
                                    alarmCenter.register(response)
                                }
                                _todoViewEffect.emit(TodoViewEffect.ShowToast("저장되었습니다."))
                                _todoViewEffect.emit(TodoViewEffect.CompleteSave)
                            } else {
                                _todoViewEffect.emit(TodoViewEffect.ShowToast("저장을 실패하였습니다."))
                            }
                        }

                        is Result.Success -> {
                            if (result.data.isRegisterAlarm()) {
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