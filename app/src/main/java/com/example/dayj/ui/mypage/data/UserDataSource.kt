package com.example.dayj.ui.mypage.data

import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun modifyNickName(nickName: String): Flow<String>
}