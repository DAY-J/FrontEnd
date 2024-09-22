package com.example.dayj.data.repo

import com.example.dayj.network.api.StatisticsService
import com.example.dayj.network.api.response.StatisticsDate
import retrofit2.Response
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(private val statisticsService: StatisticsService) :
    StatisticsRepository {
    override suspend fun getStatistics(
        userId: Int,
        startDate: String,
        endDate: String,
        tag: String?
    ): Response<List<StatisticsDate>> =
        statisticsService.getStatistics(userId, startDate, endDate, tag)
}