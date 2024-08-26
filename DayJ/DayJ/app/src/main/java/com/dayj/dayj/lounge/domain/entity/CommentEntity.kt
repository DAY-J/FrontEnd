package com.dayj.dayj.lounge.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentEntity(
    val id: Int,
    val parentId: Int?,
    val userId: Int,
    val userName: String,
    val isNameHidden: Boolean,
    val createdDate: String,
    val comment: String,
    val children: List<CommentEntity>
): Parcelable