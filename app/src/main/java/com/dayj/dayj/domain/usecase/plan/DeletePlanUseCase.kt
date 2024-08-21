package com.dayj.dayj.domain.usecase.plan

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class DeletePlanUseCase @Inject constructor(private val planRepository: PlanRepository) {
    operator fun invoke(userId: Int, planId: Int) =
        planRepository::deletePlan.toResultFlowWithParam()(userId, planId)
}