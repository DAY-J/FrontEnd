package com.dayj.dayj.domain.usecase.plan

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.network.api.response.PlanOptionRequest
import com.dayj.dayj.network.api.response.PlanRequest

import com.dayj.dayj.util.Reflect
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class CreatePlanUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(
        userId: Int,
        planRequest: PlanRequest,
        planOptionRequest: PlanOptionRequest
    ) =
        planRepository::createPlan.toResultFlowWithParam()(
            userId,
            Reflect.toMap(planRequest),
            Reflect.toMap(planOptionRequest)
        )
}
