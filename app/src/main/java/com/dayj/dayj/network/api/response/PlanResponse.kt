package com.dayj.dayj.network.api.response

import android.os.Parcelable
import com.dayj.dayj.ext.extractTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanResponse(
    val id: String,
    val planTag: String,
    val goal: String,
    val isComplete: Boolean,
    val isPublic: Boolean,
    val planOption: PlanOption
) : Parcelable{

    fun isRegisterAlarm() : Boolean =
        planOption.planStartTime != null && planOption.planEndTime != null && planOption.planAlarmTime != null

    fun hasTimeOption(): Boolean =
        planOption.planStartTime != null && planOption.planEndTime != null

    fun toPlanRequest(): PlanRequest =
        PlanRequest(
            planTag = planTag,
            goal = goal,
            isComplete = isComplete,
            isPublic = isPublic
        )

    fun toPlanOptionRequest(): PlanOptionRequest =
        PlanOptionRequest(
            planAlarmTime = planOption.planAlarmTime,
            planStartTime = planOption.planStartTime,
            planEndTime = planOption.planEndTime,
            planRepeatStartDate = planOption.planRepeatStartDate,
            planRepeatEndDate = planOption.planRepeatEndDate,
            planDaysOfWeek = planOption.planDaysOfWeek,
        )

    fun getStartEndTime(): String =
        "${extractTime(planOption.planStartTime.orEmpty())} ~ ${extractTime(planOption.planEndTime.orEmpty())}"
}
