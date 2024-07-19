package com.dayj.dayj.lounge.posting

import com.dayj.dayj.lounge.LoungeTagEnum

data class LoungePostingEntity(
    val tags: List<LoungeTagEnum>,
    val id: Int,
    val title: String,
    val content: String,
    val images: List<String>,
    val userName: String,
    val createdDate: String,
    val isNameHidden: Boolean,
    val likeCount: Int,
    val comments: List<CommentEntity>,
    val liked: Boolean
)

data class CommentEntity(
    val userId: Int,
    val userName: String,
    val isNameHidden: Boolean,
    val createdDate: String,
    val comment: String,
)