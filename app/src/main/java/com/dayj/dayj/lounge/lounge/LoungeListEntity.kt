package com.dayj.dayj.lounge.lounge

import com.dayj.dayj.lounge.LoungeTagEnum

data class LoungeListEntity(
    val id: Int,
    val title: String,
    val writerId: Int,
    val isNameHidden: Boolean,
    val writerName: String,
    val createdDate: String,
    val tags: List<LoungeTagEnum>,
    val likeCount: Int,
    val commentCount: Int
)