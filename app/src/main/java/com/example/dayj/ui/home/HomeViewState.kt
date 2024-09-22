package com.example.dayj.ui.home

import com.example.dayj.network.api.response.PlanResponse
import com.example.dayj.network.api.response.PlanTag
import java.time.LocalDate

data class HomeViewState(
    val selectedData: LocalDate = LocalDate.now(),
    val planList: List<PlanResponse> = emptyList(),
    val selectTag: PlanTag = PlanTag.ALL
) {

    fun getFilterPlans(): List<PlanResponse> =
        if (selectTag == PlanTag.ALL) {
            planList
        } else {
            planList.filter { it.planTag == selectTag.name }
        }
}
