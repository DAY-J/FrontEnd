package com.dayj.dayj.domain.usecase.statistics

import com.dayj.dayj.data.repo.StatisticsRepository
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetStatisticsUseCase @Inject constructor(private val statisticsRepository: StatisticsRepository) {

    operator fun invoke(
        userId: Int,
        startDate: String,
        endDate: String,
        tag: PlanTag?
    ) = statisticsRepository::getStatistics.toResultFlowWithParam()(
        userId,
        startDate,
        endDate,
        tag?.name
    )
}