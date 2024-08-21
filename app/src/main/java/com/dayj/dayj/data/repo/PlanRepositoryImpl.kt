package com.dayj.dayj.data.repo

import com.dayj.dayj.network.api.PlanOptionService
import com.dayj.dayj.network.api.PlanService
import com.dayj.dayj.network.api.response.PlanOption
import com.dayj.dayj.network.api.response.PlanResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val planService: PlanService,
    private val planOptionService: PlanOptionService
) : PlanRepository {

    override suspend fun createPlan(
        id: Int,
        plan: Map<String, Any?>,
        planOption: Map<String, Any?>
    ): Response<PlanResponse> {

        val planJsonString = mapToJson(plan)
        val planJsonBody = planJsonString.toRequestBody("application/json".toMediaTypeOrNull())
        val planPart = MultipartBody.Part.createFormData("plan", "plan.json", planJsonBody)

        val planOptionJsonString = mapToJson(planOption)
        val planOptionJsonBody =
            planOptionJsonString.toRequestBody("application/json".toMediaTypeOrNull())
        val planOptionPart =
            MultipartBody.Part.createFormData("planOption", "planOption.json", planOptionJsonBody)

        return planService.createPlan(id, planPart, planOptionPart)
    }


    override suspend fun getPlans(id: Int, date: String): Response<List<PlanResponse>> =
        planService.getPlans(id, date)

    override suspend fun getPlansByTag(
        id: Int,
        tag: String,
        date: String
    ): Response<List<PlanResponse>> =
        planService.getPlansByTag(id, tag, date)

    override suspend fun getPlan(id: Int, planId: Int): Response<PlanResponse> =
        planService.getPlan(id, planId)

    override suspend fun patchPlan(
        id: Int,
        planId: Int,
        plan: Map<String, Any?>,
        planOption: Map<String, Any?>
    ): Response<PlanResponse> {

        val planJsonString = mapToJson(plan)
        val planJsonBody = planJsonString.toRequestBody("application/json".toMediaTypeOrNull())
        val planPart = MultipartBody.Part.createFormData("plan", "plan.json", planJsonBody)

        val planOptionJsonString = mapToJson(planOption)
        val planOptionJsonBody =
            planOptionJsonString.toRequestBody("application/json".toMediaTypeOrNull())
        val planOptionPart =
            MultipartBody.Part.createFormData("planOption", "planOption.json", planOptionJsonBody)

        return planService.patchPlan(id, planId, planPart, planOptionPart)
    }


    override suspend fun deletePlan(id: Int, planId: Int): Response<Unit> =
        planService.deletePlan(id, planId)

    override suspend fun getPlanOption(id: Int, planId: Int): Response<PlanOption> =
        planOptionService.getPlanOption(id, planId)

    override suspend fun updatePlanOption(
        id: Int, planId: Int,
        body: Map<String, Any?>
    ): Response<Unit> =
        planOptionService.updatePlanOption(id, planId, body)

    private fun mapToJson(map: Map<String, Any?>): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(Map::class.java)
        return adapter.toJson(map)
    }
}