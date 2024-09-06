package com.dayj.dayj.network.api

import com.dayj.dayj.network.api.response.StatisticsDate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StatisticsService {

    @GET("/api/app-user/{app_user_id}/statistics/{start_date}/{end_date}")
    suspend fun getStatistics(
        @Path("app_user_id") userId: Int,
        @Path("start_date") startDate: String,
        @Path("end_date") endDate: String,
        @Query("tag") tag: String? = null
    ): Response<List<StatisticsDate>>
}
