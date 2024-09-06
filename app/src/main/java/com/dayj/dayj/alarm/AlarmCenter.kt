package com.dayj.dayj.alarm

import com.dayj.dayj.network.api.response.PlanResponse

interface AlarmCenter {

    fun register(item : PlanResponse)
    fun cancel(item : PlanResponse)
}