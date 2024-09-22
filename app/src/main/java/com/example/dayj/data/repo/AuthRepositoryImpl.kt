package com.example.dayj.data.repo

import com.example.dayj.network.api.AuthService
import retrofit2.Response
import java.net.URLEncoder
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {

    override suspend fun login(username: String): Response<Unit> =
        authService.login(username)

    override suspend fun join(
        username: String,
        nickname: String
    ): Response<Unit> =
        authService.join(username = username, nickname = nickname)


    override suspend fun logout(refresh: String): Response<Unit> =
        authService.logout(refresh)

}