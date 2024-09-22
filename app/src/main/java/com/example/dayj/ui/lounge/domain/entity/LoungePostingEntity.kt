package com.example.dayj.ui.lounge.domain.entity

import android.os.Parcelable
import com.example.dayj.ui.lounge.domain.LoungeTagEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoungePostingEntity(
    val id: Int,
    val title: String,
    val writerId: Int,
    val isNameHidden: Boolean,
    val writerName: String,
    val createdDate: String,
    val tag: LoungeTagEnum,
    val content: String,
    val images: List<String>,
    val likeCount: Int,
    val comments: List<CommentEntity>,
    val commentCount: Int,
    val isLiked: Boolean
): Parcelable