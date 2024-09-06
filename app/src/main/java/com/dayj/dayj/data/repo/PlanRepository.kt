package com.dayj.dayj.data.repo

import com.dayj.dayj.network.api.response.PlanOption
import com.dayj.dayj.network.api.response.PlanResponse
import retrofit2.Response

interface PlanRepository {

    suspend fun createPlan(
        id: Int,
        plan: Map<String, Any?>,
        planOption: Map<String, Any?>
    ): Response<PlanResponse>


    suspend fun getPlans(
        id: Int,
        date: String
    ): Response<List<PlanResponse>>

    suspend fun getPlansByTag(
        id: Int,
        tag: String,
        date: String
    ): Response<List<PlanResponse>>


    suspend fun getPlan(
        id: Int,
        planId: Int,
    ): Response<PlanResponse>


    suspend fun patchPlan(
        id: Int,
        planId: Int,
        plan: Map<String, Any?>,
        planOption: Map<String, Any?>
    ): Response<PlanResponse>


    suspend fun deletePlan(
        id: Int,
        planId: Int,
    ): Response<Unit>

    suspend fun getPlanOption(
        id: Int,
        planId: Int
    ): Response<PlanOption>

    suspend fun updatePlanOption(
        id: Int,
        planId: Int,
        body: Map<String, Any?>
    ): Response<Unit>

    suspend fun recommendPlanTag(
        id: Int,
        tag: String,
    ): Response<List<String>>
}