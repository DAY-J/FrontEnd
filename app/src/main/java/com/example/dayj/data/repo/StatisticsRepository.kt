package com.example.dayj.data.repo

import com.example.dayj.network.api.response.StatisticsDate
import retrofit2.Response

interface StatisticsRepository {

    suspend fun getStatistics(
        userId: Int,
        startDate: String,
        endDate: String,
        tag: String?
    ) : Response<List<StatisticsDate>>
}