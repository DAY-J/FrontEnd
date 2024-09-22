package com.example.dayj.domain.usecase.plan

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.network.api.response.PlanTag
import com.example.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetPlansByTagUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, tag: PlanTag, date: String) =
        planRepository::getPlansByTag.toResultFlowWithParam()(userId, tag.name, date)
}