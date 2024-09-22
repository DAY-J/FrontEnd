package com.example.dayj.domain.usecase.plan

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.network.api.response.PlanOptionRequest
import com.example.dayj.network.api.response.PlanRequest

import com.example.dayj.util.Reflect
import com.example.dayj.util.toResultFlowWithParam
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
