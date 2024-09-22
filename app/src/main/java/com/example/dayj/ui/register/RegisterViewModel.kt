package com.example.dayj.ui.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.domain.usecase.login.RegisterUseCase
import com.example.dayj.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val getEmail = savedStateHandle.get<String>("email").orEmpty()

    private val _registerViewEffect = MutableSharedFlow<RegisterEffect>()
    val registerViewEffect = _registerViewEffect.asSharedFlow()

    private val _registerViewState = MutableStateFlow(RegisterState())
    val registerViewState = _registerViewState.asStateFlow()

    fun inputNickName(nickname: String) {
        _registerViewState.update {
            it.copy(nickName = nickname)
        }
    }

    fun register() {
        val inputNickName = _registerViewState.value.nickName

        _registerViewState.update {
            it.copy(nickNameErrorType = NickNameErrorType.Idle)
        }

        if(inputNickName.isEmpty()){
            _registerViewState.update {
                it.copy(nickNameErrorType = NickNameErrorType.NotInvalid)
            }
            return
        }
        if(inputNickName.isKoreanOnly()){
            if(inputNickName.length > 15){
                _registerViewState.update {
                    it.copy(nickNameErrorType = NickNameErrorType.NotInvalid)
                }
                return
            }
        }else{
            if(inputNickName.length > 30){
                _registerViewState.update {
                    it.copy(nickNameErrorType = NickNameErrorType.NotInvalid)
                }
                return
            }
        }

        if(getEmail.isEmpty()) return

        registerUseCase(username = getEmail, nickname =  inputNickName).onEach { result ->
            when(result){
                is Result.Fail.Exception -> {
                    if(result.errorCode== 409) {
                        _registerViewState.update {
                            it.copy(nickNameErrorType = NickNameErrorType.Duplicated)
                        }
                    }else{
                        _registerViewState.update {
                            it.copy(nickNameErrorType = NickNameErrorType.Idle)
                        }
                        _registerViewEffect.emit(RegisterEffect.ShowToast("회원가입을 실패하였습니다."))
                    }
                }
                is Result.Success -> {
                    _registerViewEffect.emit(RegisterEffect.RouteHome)
                }
            }
        }.launchIn(viewModelScope)
    }
}

private fun String.isKoreanOnly(): Boolean {
    val koreanRegex = Regex("^[가-힣ㄱ-ㅎㅏ-ㅣ]+$")
    return this.matches(koreanRegex)
}