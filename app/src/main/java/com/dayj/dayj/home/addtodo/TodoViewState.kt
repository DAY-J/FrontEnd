package com.dayj.dayj.home.addtodo


import com.dayj.dayj.network.api.response.PlanOptionRequest
import com.dayj.dayj.network.api.response.PlanRequest

data class TodoViewState(
    val planRequest: PlanRequest = PlanRequest(),
    val planOptionRequest: PlanOptionRequest = PlanOptionRequest(),
    val isShowRecommendTodo: Boolean = false,
    val recommendTodoList: List<String> = emptyList()
) {
    fun isNeedCreateRequirements() : Boolean =
        planRequest.goal.isNotEmpty()
}
