package com.example.dayj.ui.friends.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    val userName: String,
    val userNickName: String,
    val userEmail: String,
    val userId: Int
): Parcelable