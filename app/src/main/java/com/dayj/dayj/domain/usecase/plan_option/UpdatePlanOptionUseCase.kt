package com.dayj.dayj.domain.usecase.plan_option

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.network.api.response.PlanOption
import com.dayj.dayj.util.Reflect
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class UpdatePlanOptionUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, planId: Int, planOption: PlanOption) =
        planRepository::updatePlanOption.toResultFlowWithParam()(
            userId,
            planId,
            Reflect.toMap(planOption)
        )
}