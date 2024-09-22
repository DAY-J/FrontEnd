package com.example.dayj.domain.usecase.plan

import com.example.dayj.data.repo.PlanRepository
import com.example.dayj.util.toResultFlowWithParam
import javax.inject.Inject

class GetPlansUseCase @Inject constructor(private val planRepository: PlanRepository) {

    operator fun invoke(userId: Int, date: String) =
        planRepository::getPlans.toResultFlowWithParam()(userId, date)
}