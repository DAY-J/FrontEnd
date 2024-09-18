package com.dayj.dayj.lounge.data.source

import com.dayj.dayj.lounge.data.model.RequestCommentModel
import com.dayj.dayj.lounge.data.model.RequestPostingCreation
import com.dayj.dayj.lounge.data.model.ResponsePosting
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface LoungeDataSource {
    suspend fun writePosting(userId: Int, requestPostingCreation: RequestPostingCreation, images: List<MultipartBody.Part>): Flow<Boolean>
    suspend fun getAllPostings(): Flow<List<ResponsePosting>>
    suspend fun writeComment(postingId: Int, userId: Int, requestCommentModel: RequestCommentModel): Flow<ResponsePosting.CommentModel?>
    suspend fun writeChildComment(postingId: Int, userId: Int, commentId: Int, requestCommentModel: RequestCommentModel): Flow<ResponsePosting.CommentModel?>
    suspend fun deleteComment(postingId: Int, commentId: Int): Flow<Boolean>
    suspend fun editComment(postingId: Int, commentId: Int, requestCommentModel: RequestCommentModel): Flow<ResponsePosting.CommentModel?>
    suspend fun likePosting(postingId: Int): Flow<Boolean>
}