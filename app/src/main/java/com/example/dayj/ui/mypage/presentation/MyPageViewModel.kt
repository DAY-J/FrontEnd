package com.example.dayj.ui.mypage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dayj.data.PreferenceManager
import com.example.dayj.datastore.SelfUserAccountDataStore
import com.example.dayj.datastore.UserInfo
import com.example.dayj.ui.friends.domain.entity.UserEntity
import com.example.dayj.ui.mypage.data.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.prefs.Preferences
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userAccountDataSource: SelfUserAccountDataStore
): ViewModel() {
    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _nickName = MutableStateFlow<String>("")
    val nickName = _nickName.asStateFlow()

    private val _userEntity = MutableStateFlow<UserEntity?>(null)
    val userEntity = _userEntity.asStateFlow()

    fun getCachedUser() {
        viewModelScope.launch {
            userAccountDataSource.userInfoFlow.first()?.let { userInfo ->
                userInfo.let {
                    UserEntity(
                        userName = it.username,
                        userEmail = it.username,
                        userNickName = it.nickname,
                        userId = it.id
                    ).let {
                        _userEntity.value = it
                        _nickName.value = it.userNickName
                    }
                }
            }
        }
    }

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
            runCatching {
                userDataSource.modifyNickName(nickName.value).collect { errorMessage ->
                    if(errorMessage.isNotEmpty()) {
                        _errorMessage.value = errorMessage
                        _nickName.value = ""
                    } else {
                        userAccountDataSource.userInfoFlow.first()?.let { userInfo ->
                            userAccountDataSource.setUserInfo(userInfo.copy(nickname = nickName.value))
                            successListener()
                        }
                    }
                }
            }.getOrElse {

            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userAccountDataSource.setUserInfo(
                UserInfo(
                    id = -1,
                    username = "",
                    password = "",
                    role = "",
                    nickname = "",
                    profilePhoto = null,
                    isAlarm = false
                )
            )
        }
    }
}