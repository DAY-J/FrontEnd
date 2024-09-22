package com.example.dayj.domain.usecase.login


import com.example.dayj.data.repo.AuthRepository
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.util.Result
import com.example.dayj.util.toLoginResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginAuthorizationRepository: LoginAuthorizationRepository
) {
    operator fun invoke(username: String) = flow {
        loginAuthorizationRepository.clearData()
        loginAuthorizationRepository.clearUserInfo()
        when (val result = authRepository.login(username).toLoginResult()) {
            is Result.Fail.Exception -> {
                emit(result)
            }
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
