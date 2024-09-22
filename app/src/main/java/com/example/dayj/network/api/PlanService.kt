package com.example.dayj.network.api

import com.example.dayj.network.api.response.PlanResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlanService {


    @Multipart
    @POST("/api/app-user/{app_user_id}/plan")
    suspend fun createPlan(
        @Path("app_user_id") id: Int,
        @Part plan: MultipartBody.Part,
        @Part planOption: MultipartBody.Part
    ): Response<PlanResponse>


    @GET("/api/app-user/{user_id}/plan")
    suspend fun getPlans(
        @Path("user_id") id: Int,
        @Query("date") date: String
    ): Response<List<PlanResponse>>

    @GET("/api/app-user/{user_id}/plan/tag/{tag}")
    suspend fun getPlansByTag(
        @Path("user_id") id: Int,
        @Path("tag") tag: String,
        @Query("date") date: String
    ): Response<List<PlanResponse>>

    @GET("/api/app-user/{user_id}/plan/{plan_id}")
    suspend fun getPlan(
        @Path("user_id") id: Int,
        @Path("plan_id") planId: Int,
    ): Response<PlanResponse>


    @Multipart
    @PATCH("/api/app-user/{user_id}/plan/{plan_id}")
    suspend fun patchPlan(
        @Path("user_id") id: Int,
        @Path("plan_id") planId: Int,
        @Part plan: MultipartBody.Part,
        @Part planOption: MultipartBody.Part
    ): Response<PlanResponse>

    @DELETE("/api/app-user/{user_id}/plan/{plan_id}")
    suspend fun deletePlan(
        @Path("user_id") id: Int,
        @Path("plan_id") planId: Int,
    ): Response<Unit>


    @GET("/api/app-user/{user_id}/plan/reminder/{tag}")
    suspend fun recommendPlanTag(
        @Path("user_id") id: Int,
        @Path("tag") tag: String,
    ) : Response<List<String>>
}