package com.dayj.dayj.home.addtodo


import com.dayj.dayj.network.api.response.PlanOption
import com.dayj.dayj.network.api.response.PlanOptionRequest
import com.dayj.dayj.network.api.response.PlanRequest
import com.dayj.dayj.network.api.response.PlanResponse

data class TodoViewState(
    val planRequest: PlanRequest = PlanRequest(),
    val planOptionRequest: PlanOptionRequest = PlanOptionRequest(),
    val isShowRecommendTodo: Boolean = false,
    val recommendTodoList: List<String> = emptyList()
) {
    fun isNeedCreateRequirements() : Boolean =
        planRequest.goal.isNotEmpty()
}

fun TodoViewState.toPlanResponse(id : String) : PlanResponse {
    return PlanResponse(
        id = id,
        planTag = planRequest.planTag,
        goal = planRequest.goal,
        isComplete = planRequest.isComplete,
        isPublic = planRequest.isPublic,
        planOption = PlanOption(
            id = id,
            planAlarmTime = planOptionRequest.planAlarmTime,
            planStartTime = planOptionRequest.planStartTime,
            planEndTime = planOptionRequest.planEndTime,
            planRepeatStartDate = planOptionRequest.planRepeatStartDate,
            planRepeatEndDate = planOptionRequest.planRepeatEndDate,
            planDaysOfWeek = planOptionRequest.planDaysOfWeek
        )
    )
}

