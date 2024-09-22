package com.example.dayj.domain.usecase.login

import com.example.dayj.data.repo.AuthRepository
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.util.Result
import com.example.dayj.util.toLoginResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginAuthorizationRepository: LoginAuthorizationRepository
) {
    operator fun invoke(username: String, nickname: String) = flow {
        loginAuthorizationRepository.clearData()
        when (val result =
            authRepository.join(username = username, nickname = nickname).toLoginResult()) {
            is Result.Fail.Exception -> emit(result)
            is Result.Success -> {
                loginAuthorizationRepository.updateUserID(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken
                )
                emit(result)
            }
        }
    }

}