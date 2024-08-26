package com.dayj.dayj.friends.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FriendsGroupEntity(
    val groupId: Int,
    val groupName: String,
    val participantsCount: Int,
    val groupGoal: String,
    val participants: List<GroupParticipantEntity>
): Parcelable