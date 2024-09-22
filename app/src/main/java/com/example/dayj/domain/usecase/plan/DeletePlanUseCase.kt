package com.example.dayj.domain.usecase.plan

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class DeletePlanUseCase @Inject constructor(private val planRepository: PlanRepository) {
    operator fun invoke(userId: Int, planId: Int) =
        planRepository::deletePlan.toResultFlowWithParam()(userId, planId)
}