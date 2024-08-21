package com.dayj.dayj.domain.usecase.plan_option

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetPlanOptionUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, planId: Int) =
        planRepository::getPlanOption.toResultFlowWithParam()(
            userId,
            planId,
        )
}