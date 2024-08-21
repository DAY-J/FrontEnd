package com.dayj.dayj.domain.usecase.plan

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.network.api.response.PlanOptionRequest
import com.dayj.dayj.network.api.response.PlanRequest
import com.dayj.dayj.util.Reflect
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class UpdatePlanUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(
        userId: Int, planId: Int,
        planRequest: PlanRequest,
        planOptionRequest: PlanOptionRequest
    ) =
        planRepository::patchPlan.toResultFlowWithParam()(
            userId,
            planId,
            Reflect.toMap(planRequest),
            Reflect.toMap(planOptionRequest)
        )
}