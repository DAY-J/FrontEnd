package com.example.dayj.data.repo

import retrofit2.Response

interface AuthRepository {

    suspend fun login(
        username: String,
    ): Response<Unit>

    suspend fun join(
        username: String,
        nickname: String
    ): Response<Unit>

    suspend fun logout(refresh: String): Response<Unit>
}