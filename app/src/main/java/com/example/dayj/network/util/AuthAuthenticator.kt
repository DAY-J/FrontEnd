package com.example.dayj.network.util

import android.util.Log
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.network.api.AuthService
import com.example.dayj.util.LoginResult
import com.example.dayj.util.Result
import com.example.dayj.util.toLoginResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.awaitResponse
import javax.inject.Inject


class AuthAuthenticator @Inject constructor(
    private val loginAuthorizationRepository: LoginAuthorizationRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            Log.d("결과", "AuthAuthenticator")
            val authService = Retrofit.Builder().baseUrl("http://35.216.13.139:8080").build()
                .create(AuthService::class.java)
            val refreshToken = loginAuthorizationRepository.refreshToken.first()

            Log.d("결과", refreshToken)

            val reissueResponse = authService.reissue(refreshToken).awaitResponse()
            if (reissueResponse.isSuccessful) {
                Log.d("결과", "Token refresh successful")
                val loginResult = (reissueResponse.toLoginResult() as Result.Success)
                Log.d("결과", loginResult.data.toString())
                loginAuthorizationRepository.updateUserID(
                    loginResult.data.accessToken,
                    loginResult.data.refreshToken
                )
                response.request.newBuilder()
                    .header("access", loginResult.data.accessToken)
                    .build()
            } else {
                Log.e("결과", "Token refresh failed: ${reissueResponse.code()}")
                null
            }
        }
    }
}