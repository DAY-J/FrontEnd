package com.example.dayj.datastore

import kotlinx.coroutines.flow.Flow

interface SelfUserAccountDataStore {
    val accessTokenFlow: Flow<String>
    val refreshTokenFlow: Flow<String>

    val userInfoFlow: Flow<UserInfo?>

    suspend fun setAccountInfo(accessToken: String, refreshToken: String)

    suspend fun setUserInfo(userInfo: UserInfo)

    suspend fun clearData()

    suspend fun clearUserInfo()
}