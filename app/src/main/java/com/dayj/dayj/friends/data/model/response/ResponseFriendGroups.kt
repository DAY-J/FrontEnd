package com.dayj.dayj.friends.data.model.response

data class ResponseFriendGroups(
    val id: Int,
    val groupGoal: String? = "",
    val groupName: String? ="",
    val createdAt: String? ="",
    val achievementList: List<AchievementInfo> = listOf(),
    val groupMemberList: List<ParticipantInfo> = listOf()
) {
    data class AchievementInfo(
        val id: Int,
        val appUserId: Int,
        val nickname: String,
        val achievementRate: AchievementRateInfo? = null,
        val groupMemberPlans: List<MemberPlanInfo>?
    )

    data class AchievementRateInfo(
        val id: Int,
        val date: String = "",
        val achievementRate: Int = 0
    )

    data class MemberPlanInfo(
        val id: Int,
        val goal: String,
        val complete: Boolean
    )

    data class ParticipantInfo(
        val id: Int,
        val appUserId: Int,
        val nickname: String,
        val achievementRate: AchievementRateInfo? = null,
        val groupMemberPlan: List<MemberPlanInfo>?
    )
}