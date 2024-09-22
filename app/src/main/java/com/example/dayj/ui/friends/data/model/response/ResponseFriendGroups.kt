package com.example.dayj.ui.friends.data.model.response

data class ResponseFriendGroups(
    val id: Int,
    val groupGoal: String? = "",
    val groupName: String? ="",
    val createdAt: String? ="",
    val achievementList: List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups.AchievementInfo>? = listOf(),
    val groupMemberList: List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups.ParticipantInfo>? = listOf()
) {
    data class AchievementInfo(
        val id: Int,
        val appUserId: Int,
        val nickname: String,
        val achievementRate: com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups.AchievementRateInfo? = null,
        val groupMemberPlans: List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups.MemberPlanInfo>?
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
        val achievementRate: com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups.AchievementRateInfo? = null,
        val groupMemberPlans: List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups.MemberPlanInfo>?
    )
}