package com.dayj.dayj.lounge.data.source

import com.dayj.dayj.lounge.data.model.RequestCommentModel
import com.dayj.dayj.lounge.data.model.RequestPostingCreation
import com.dayj.dayj.lounge.data.model.ResponsePosting
import com.dayj.dayj.network.ApiService
import com.dayj.dayj.utils.Extensions.toApplicationJsonRequestBody
import com.dayj.dayj.utils.Extensions.toTextPlainRequestBody
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class LoungeDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): LoungeDataSource {
    override suspend fun writePosting(userId: Int, requestPostingCreation: RequestPostingCreation, images: List<MultipartBody.Part>): Flow<Boolean> = flow {
            val response = apiService.postLoungePosting(
            userId = userId,
            dto =  Gson().toJson(requestPostingCreation).toApplicationJsonRequestBody(),
            multipartFile = images
        )
        emit(response.isSuccessful)
    }

    override suspend fun getAllPostings(): Flow<List<ResponsePosting>> = flow {
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
        requestCommentModel: RequestCommentModel
    ): Flow<ResponsePosting.CommentModel?> = flow {
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
        requestCommentModel: RequestCommentModel
    ): Flow<ResponsePosting.CommentModel?> = flow {
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
        requestCommentModel: RequestCommentModel
    ): Flow<ResponsePosting.CommentModel?> = flow {
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
}