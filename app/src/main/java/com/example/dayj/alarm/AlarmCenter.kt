package com.example.dayj.alarm

import com.example.dayj.network.api.response.PlanResponse

interface AlarmCenter {

    fun register(item : PlanResponse)
    fun cancel(item : PlanResponse)
}