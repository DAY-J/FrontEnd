package com.example.dayj.network.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String = "dayjuser"
    ): Response<Unit>

    @FormUrlEncoded
    @POST("/join")
    suspend fun join(
        @Field("username") username: String,
        @Field("password") password: String = "dayjuser",
        @Field("nickname") nickname: String
    ): Response<Unit>

    @POST("/reissue")
    fun reissue(
        @Header("refresh") refresh: String
    ): Call<Unit>

    @FormUrlEncoded
    @POST("/logout")
    suspend fun logout(
        @Field("refresh") refresh: String
    ): Response<Unit>
}
