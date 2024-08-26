package com.dayj.dayj.friends.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupParticipantEntity(
    val user: UserEntity,
    val achievementRate: Int = 0,
    val plans: List<GoalByParicipantEntity> = listOf()
): Parcelable