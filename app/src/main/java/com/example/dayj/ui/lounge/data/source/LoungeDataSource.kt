package com.example.dayj.ui.lounge.data.source

import com.example.dayj.ui.lounge.data.model.RequestCommentModel
import com.example.dayj.ui.lounge.data.model.RequestPostingCreation
import com.example.dayj.ui.lounge.data.model.ResponsePosting
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface LoungeDataSource {
    suspend fun writePosting(userId: Int, requestPostingCreation: com.example.dayj.ui.lounge.data.model.RequestPostingCreation, images: List<MultipartBody.Part>): Flow<Boolean>
    suspend fun getAllPostings(): Flow<List<com.example.dayj.ui.lounge.data.model.ResponsePosting>>
    suspend fun writeComment(postingId: Int, userId: Int, requestCommentModel: com.example.dayj.ui.lounge.data.model.RequestCommentModel): Flow<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel?>
    suspend fun writeChildComment(postingId: Int, userId: Int, commentId: Int, requestCommentModel: com.example.dayj.ui.lounge.data.model.RequestCommentModel): Flow<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel?>
    suspend fun deleteComment(postingId: Int, commentId: Int): Flow<Boolean>
    suspend fun editComment(postingId: Int, commentId: Int, requestCommentModel: com.example.dayj.ui.lounge.data.model.RequestCommentModel): Flow<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel?>
    suspend fun likePosting(postingId: Int): Flow<Boolean>
}