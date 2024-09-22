package com.example.dayj.domain.usecase.login

import com.example.dayj.data.repo.AuthRepository
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.util.Result
import com.example.dayj.util.toResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginAuthorizationRepository: LoginAuthorizationRepository
) {
    operator fun invoke() = flow {
        when (authRepository.logout(loginAuthorizationRepository.refreshToken.first()).toResult()) {
            is Result.Fail.Exception -> {
                loginAuthorizationRepository.clearData()
                emit(false)
            }

            is Result.Success -> {
                loginAuthorizationRepository.clearData()
                emit(true)
            }
        }
    }
}