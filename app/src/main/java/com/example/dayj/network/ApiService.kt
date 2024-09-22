package com.example.dayj.network

import com.example.dayj.ui.friends.data.model.request.RequestGroupCreation
import com.example.dayj.ui.friends.data.model.request.RequestGroupGoal
import com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups
import com.example.dayj.ui.lounge.data.model.RequestCommentModel
import com.example.dayj.ui.lounge.data.model.RequestPostingCreation
import com.example.dayj.ui.lounge.data.model.ResponsePosting
import com.example.dayj.ui.mypage.data.RequestModifyNickName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ApiService {
    @POST("app-user/{userId}/friend-group")
    suspend fun postGroupCreation(
        @Path("userId") userId: Int,
        @Body requestGroupCreation: com.example.dayj.ui.friends.data.model.request.RequestGroupCreation
    ): Response<Unit>

    @POST("app-user/{email}/group-member/{groupId}")
    suspend fun postFriendInvitation(
        @Path("email") email: String,
        @Path("groupId") groupId: Int
    ): Response<Unit>

    @GET("app-user/{userId}/friend-group")
    suspend fun getAllGroups(
        @Path("userId") userId: Int
    ): Response<List<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups>>

    @GET("app-user/{userId}/friend-group/{groupId}")
    suspend fun getSpecificGroup(
        @Path("userId") userId: Int,
        @Path("groupId") groupId: Int
    ): Response<com.example.dayj.ui.friends.data.model.response.ResponseFriendGroups>

    @PATCH("app-user/{userId}/friend-group/{groupId}")
    suspend fun patchGroupGoalEdit(
        @Path("userId") userId: Int,
        @Path("groupId") groupId: Int,
        @Body requestGroupGoal: com.example.dayj.ui.friends.data.model.request.RequestGroupGoal
    ): Response<Unit>

    @Multipart
    @POST("post/app-user/{userId}")
    suspend fun postLoungePosting(
        @Path("userId") userId: Int,
        @Part("dto") dto: RequestBody,
        @Part multipartFile: List<MultipartBody.Part>?
    ): Response<Unit>

    @GET("post")
    suspend fun getAllPostings(): Response<List<com.example.dayj.ui.lounge.data.model.ResponsePosting>>

    @PATCH("app-user/{id}")
    suspend fun patchNickName(
        @Path("id") userId: Int,
        @Body requestModifyNickName: RequestModifyNickName
    ): Response<Any>

    @POST("post/{postingId}/app-user/{userId}/comment")
    suspend fun postComment(
        @Path("postingId") postingId: Int,
        @Path("userId") userId: Int,
        @Body requestComment: com.example.dayj.ui.lounge.data.model.RequestCommentModel
    ): Response<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel>

    @POST("post/{postingId}/app-user/{userId}/comment/{commentId}")
    suspend fun postChildComment(
        @Path("postingId") postingId: Int,
        @Path("userId") userId: Int,
        @Path("commentId") commentId: Int,
        @Body requestComment: com.example.dayj.ui.lounge.data.model.RequestCommentModel
    ): Response<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel>

    @DELETE("post/{postingId}/comment/{commentId}")
    suspend fun deleteComment(
        @Path("postingId") postingId: Int,
        @Path("commentId") commentId: Int
    ): Response<Unit>

    @PATCH("post/{postingId}/comment/{commentId}")
    suspend fun patchComment(
        @Path("postingId") postingId: Int,
        @Path("commentId") commentId: Int,
        @Body requestComment: com.example.dayj.ui.lounge.data.model.RequestCommentModel
    ): Response<com.example.dayj.ui.lounge.data.model.ResponsePosting.CommentModel>

    @DELETE("app-user/{app_user_id}/group-member/{group_id}")
    suspend fun exitGroup(
        @Path("app_user_id") appUserId: Int,
        @Path("group_id") groupId: Int
    ): Response<Unit>

    @PATCH("post/{post_id}/like")
    suspend fun likePosting(
        @Path("post_id") postId: Int
    ): Response<Unit>
}