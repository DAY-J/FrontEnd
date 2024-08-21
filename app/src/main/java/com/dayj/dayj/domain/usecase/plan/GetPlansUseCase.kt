package com.dayj.dayj.domain.usecase.plan

import com.dayj.dayj.data.repo.PlanRepository
import com.dayj.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetPlansUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, date: String) =
        planRepository::getPlans.toResultFlowWithParam()(userId, date)
}