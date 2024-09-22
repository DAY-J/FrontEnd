package com.example.dayj.ui.friends.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GoalByParicipantEntity(
    val userId: Int,
    val planId: Int,
    val goalTitle: String,
    val achieved: Boolean
): Parcelable