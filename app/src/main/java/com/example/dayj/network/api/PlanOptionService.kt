package com.example.dayj.network.api

import com.example.dayj.network.api.response.PlanOption
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PlanOptionService {

    @GET("/api/app-user/{app_user_id}/plan/{plan_id}/option")
    suspend fun getPlanOption(
        @Path("app_user_id") id: Int,
        @Path("plan_id") planId: Int
    ): Response<PlanOption>

    @JvmSuppressWildcards
    @PATCH("/api/app-user/{app_user_id}/plan/{plan_id}/option")
    suspend fun updatePlanOption(
        @Path("app_user_id") id: Int,
        @Path("plan_id") planId: Int,
        @Body body: Map<String, Any?>
    ): Response<Unit>
}