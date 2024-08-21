package com.dayj.dayj.domain.usecase.plan

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetPlansByTagUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, tag: PlanTag, date: String) =
        planRepository::getPlansByTag.toResultFlowWithParam()(userId, tag.name, date)
}