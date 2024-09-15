package com.dayj.dayj.mypage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayj.dayj.friends.domain.entity.UserEntity
import com.dayj.dayj.mypage.data.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userDataSource: UserDataSource
): ViewModel() {
    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _nickName = MutableStateFlow<String>("")
    val nickName = _nickName.asStateFlow()

    private val _userEntity = MutableStateFlow<UserEntity?>(null)
    val userEntity = _userEntity.asStateFlow()


    fun updateUserEntity(user: UserEntity?) {
        _userEntity.value = user
        _nickName.value = user?.userName ?: ""
    }

    fun updateNickName(name: String) {
        _nickName.value = name
    }

    fun checkNickNameLength(nickName: String) {
        _errorMessage.value = if(nickName.length > 15) "닉네임은 15자까지 가능합니다." else ""
    }


    /**
     * 닉네임 수정하기 [Remote]
     * **/
    fun completeModification(successListener: () ->Unit) {
        viewModelScope.launch {
            userDataSource.modifyNickName(nickName.value).collect { errorMessage ->
                if(errorMessage.isNotEmpty()) {
                    _errorMessage.value = errorMessage
                    _nickName.value = ""
                } else {
                    successListener()
                }
            }
        }
    }
}