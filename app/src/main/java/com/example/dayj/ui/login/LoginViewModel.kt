package com.example.dayj.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.data.repo.LoginAuthorizationRepository
import com.example.dayj.data.repo.UserRepository
import com.example.dayj.datastore.UserInfo
import com.example.dayj.domain.usecase.login.LoginUseCase
import com.example.dayj.domain.usecase.user.GetUsersUseCase
import com.example.dayj.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val loginAuthorizationRepository: LoginAuthorizationRepository
) : ViewModel() {

    private val _loginViewEffect = MutableSharedFlow<LoginEffect>()
    val loginViewEffect = _loginViewEffect.asSharedFlow()

    fun tempLogin() {
        viewModelScope.launch {
            loginAuthorizationRepository.updateUserInfo(
                UserInfo(
                    id = 1,
                    username = "dfdf",
                    password = "",
                    role = "",
                    nickname = "sdfasdf",
                    profilePhoto = null,
                    isAlarm = false
                )
            )

        }

    }

    fun login(intent: Intent?) {
        if (intent != null) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(intent)
            Log.d("결과-login", "intent not null")
            task.result?.let {
                Log.d("결과-login", "result not null")
                val email = it.email
                val idToken = it.idToken
                Log.d("결과-login", "email $email idToken $idToken")
                if (email != null && idToken != null) {
                    loginUseCase(email).onEach { result ->
                        when (result) {
                            is Result.Fail.Exception -> {
                                if (result.errorCode == 401) {
                                    _loginViewEffect.emit(LoginEffect.RouteToRegister(email))
                                } else {
                                    _loginViewEffect.emit(LoginEffect.ShowToast(result.message))
                                }
                            }

                            is Result.Success -> {
                                updateUserInfo(email)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        } else {
            Log.d("결과-login", "intent null")
            viewModelScope.launch {
                _loginViewEffect.emit(LoginEffect.ShowToast("구글 로그인 실패!"))
            }
        }
    }

    private fun updateUserInfo(userEmail: String) {
        getUsersUseCase().onEach { result ->
            when (result) {
                is Result.Fail.Exception -> {
                    Log.d("결과", "updateUserInfo error :" + result.message)
                }

                is Result.Success -> {
                    Log.d("결과", "gerUserIfno : ${result.data}")
                    result.data.firstOrNull { it.username == userEmail }?.let { userInfo ->
                        loginAuthorizationRepository.updateUserInfo(userInfo)
                        viewModelScope.launch {
                            _loginViewEffect.emit(LoginEffect.RouteToMain)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

