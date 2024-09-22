package com.example.dayj.data.repo

import com.example.dayj.datastore.UserInfo
import kotlinx.coroutines.flow.Flow

interface LoginAuthorizationRepository {

    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    val userInfoFlow: Flow<UserInfo?>

    suspend fun updateUserID(accessToken: String, refreshToken: String)
    suspend fun clearData()

    suspend fun updateUserInfo(userInfo: UserInfo)
    suspend fun clearUserInfo()

}