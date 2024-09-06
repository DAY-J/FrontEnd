package com.dayj.dayj.data.repo

import com.dayj.dayj.network.api.response.StatisticsDate
import retrofit2.Response

interface StatisticsRepository {

    suspend fun getStatistics(
        userId: Int,
        startDate: String,
        endDate: String,
        tag: String?
    ) : Response<List<StatisticsDate>>
}