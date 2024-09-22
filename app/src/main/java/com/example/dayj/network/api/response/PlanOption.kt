package com.example.dayj.network.api.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanOption(
    val id : String = "",
    val planAlarmTime : String? = null,
    val planStartTime : String? = null,
    val planEndTime : String? = null,
    val planRepeatStartDate : String? = null,
    val planRepeatEndDate : String? = null,
    val planDaysOfWeek : List<String>? = null,
) : Parcelable