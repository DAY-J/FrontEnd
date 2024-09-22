package com.example.dayj.domain.usecase.plan_option

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.network.api.response.PlanOption
import com.example.dayj.util.Reflect
import com.example.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class UpdatePlanOptionUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, planId: Int, planOption: PlanOption) =
        planRepository::updatePlanOption.toResultFlowWithParam()(
            userId,
            planId,
            Reflect.toMap(planOption)
        )
}