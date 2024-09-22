package com.example.dayj.network.util


import com.example.dayj.data.repo.LoginAuthorizationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(private val loginAuthorizationRepository: LoginAuthorizationRepository) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().apply {
                addHeader("Content-Type", "application/json")
                val accessToken = runBlocking {
                    loginAuthorizationRepository.accessToken.first()
                }
                if(accessToken.isNotEmpty()){
                    addHeader("access", accessToken)
                }
            }.build()

        val response = chain.proceed(request)

        return response
    }
}