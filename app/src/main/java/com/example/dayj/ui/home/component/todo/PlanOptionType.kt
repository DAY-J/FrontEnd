package com.example.dayj.ui.home.component.todo

import com.example.dayj.R


enum class PlanOptionType(
    val title: String,
    val image: Int
) {
    ALLOW_FRIEND("친구공개", R.drawable.ic_friend),
    ALARM("알람", R.drawable.ic_alarm),
    TIME("시간", R.drawable.ic_time),
    REPEAT("반복", R.drawable.ic_repeat)
}