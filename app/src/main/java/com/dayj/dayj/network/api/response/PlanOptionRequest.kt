package com.dayj.dayj.network.api.response

data class PlanOptionRequest(
    val planAlarmTime : String? = null,
    val planStartTime : String? = null,
    val planEndTime : String? = null,
    val planRepeatStartDate : String? = null,
    val planRepeatEndDate : String? = null,
    val planDaysOfWeek : List<String>? = null,
)
