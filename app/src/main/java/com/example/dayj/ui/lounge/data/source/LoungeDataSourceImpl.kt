package com.example.dayj.ui.lounge.data.source

import com.example.dayj.ui.lounge.data.model.RequestCommentModel
import com.example.dayj.ui.lounge.data.model.RequestPostingCreation
import com.example.dayj.ui.lounge.data.model.ResponsePosting
import com.example.dayj.network.ApiService
import com.example.dayj.util.Extensions.toApplicationJsonRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class LoungeDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): LoungeDataSource {
    override suspend fun writePosting(userId: Int, requestPostingCreation: com.example.dayj.ui.lounge.data.model.RequestPostingCreation, images: List<MultipartBody.Part>): Flow<Boolean> = flow {
            val response = apiService.postLoungePosting(
            userId = userId,
            dto =  Gson().toJson(requestPostingCreation).toApplicationJsonRequestBody(),
            multipartFile = images
        )
        emit(response.isSuccessful)
    }

    override suspend fun getAllPostings(): Flow<List<com.example.dayj.ui.lounge.data.model.ResponsePosting>> = flow {
        val response = apiService.getAllPostings()
        if(response.isSuccessful && response.body() != null) {
            emit(response.body()!!)
        } else {
            emit(listOf())
        }
    }

    override suspend fun writeComment(
        postingId: Int,
        userId: Int,
        requestCommentModel: com.example.dayj.ui.lounge.data.model.RequestCommentModel
    ): Flow<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel?> = flow {
        val response = apiService.postComment(
            postingId = postingId,
            userId = userId,
            requestComment = requestCommentModel
        )

        if(response.isSuccessful) {
            emit(response.body())
        } else {
            emit(null)
        }
    }

    override suspend fun writeChildComment(
        postingId: Int,
        userId: Int,
        commentId: Int,
        requestCommentModel: com.example.dayj.ui.lounge.data.model.RequestCommentModel
    ): Flow<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel?> = flow {
        val response = apiService.postChildComment(
            postingId = postingId,
            userId = userId,
            commentId = commentId,
            requestComment = requestCommentModel
        )

        if(response.isSuccessful) {
            emit(response.body())
        } else {
            emit(null)
        }
    }

    override suspend fun deleteComment(postingId: Int, commentId: Int): Flow<Boolean> = flow {
        val response = apiService.deleteComment(
            postingId = postingId,
            commentId = commentId
        )
        if(response.isSuccessful) {
            emit(true)
        } else {
            emit(false)
        }
    }

    override suspend fun editComment(
        postingId: Int,
        commentId: Int,
        requestCommentModel: com.example.dayj.ui.lounge.data.model.RequestCommentModel
    ): Flow<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel?> = flow {
        val response = apiService.patchComment(
            postingId = postingId,
            commentId = commentId,
            requestComment = requestCommentModel
        )

        if(response.isSuccessful) {
            emit(response.body())
        } else {
            emit(null)
        }
    }

    override suspend fun likePosting(postingId: Int): Flow<Boolean> = flow {
        val response = apiService.likePosting(postingId)
        emit(response.isSuccessful)
    }
}