package com.example.dayj.network.api.response

data class PlanRequest(
    val planTag: String = PlanTag.HEALTH.name,
    val goal: String = "",
    val isComplete: Boolean = false,
    val isPublic: Boolean = true
)