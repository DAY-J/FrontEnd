package com.dayj.dayj.statistics

import com.dayj.dayj.ext.formatLocalDate
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.network.api.response.StatisticsDate
import java.time.LocalDate
import kotlin.math.roundToInt

data class StatisticsViewState(
    val startDate: LocalDate = LocalDate.now(),
    val tag: PlanTag = PlanTag.ALL,
    val statistics: List<StatisticsDate> = emptyList()
) {
    fun getPlanTag(): PlanTag? {
        return when (tag) {
            PlanTag.ALL -> null
            else -> tag
        }
    }

    fun getStatisticsDateString(): String =
        "${formatLocalDate(startDate, "yyyy년 MM월 dd일")} ~ ${
            formatLocalDate(
                startDate.plusDays(7),
                "MM월 dd일"
            )
        }"

    fun getStatisticsAllPercent(): String =
        if (statistics.isEmpty()) {
            "0% 달성"
        } else {
            "${statistics.map { it.value }.average().roundToInt()}% 달성"
        }
}
