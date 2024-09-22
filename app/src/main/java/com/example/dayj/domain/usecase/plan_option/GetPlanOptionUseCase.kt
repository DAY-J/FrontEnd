package com.example.dayj.domain.usecase.plan_option

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetPlanOptionUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, planId: Int) =
        planRepository::getPlanOption.toResultFlowWithParam()(
            userId,
            planId,
        )
}