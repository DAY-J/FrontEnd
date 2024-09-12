package com.dayj.dayj.lounge.domain.repository

import com.dayj.dayj.lounge.domain.entity.CommentEntity
import com.dayj.dayj.lounge.domain.entity.LoungePostingEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface LoungeRepository {
    suspend fun writePosting(title: String, content: String, tag: String, anonymous: Boolean, images: List<MultipartBody.Part>): Flow<Boolean>
    suspend fun writeComment(postingId: Int, comment: String, anonymous: Boolean): Flow<CommentEntity?>
    suspend fun writeChildComment(postingId: Int, comment: String, commentId: Int, anonymous: Boolean): Flow<CommentEntity?>
    suspend fun deleteComment(postingId: Int, commentId: Int): Flow<Boolean>
    suspend fun editComment(postingId: Int, commentId: Int, comment: String, anonymous: Boolean): Flow<CommentEntity?>
    suspend fun getAllPostings(): Flow<List<LoungePostingEntity>>
}