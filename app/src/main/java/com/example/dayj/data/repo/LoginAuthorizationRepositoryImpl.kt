package com.example.dayj.data.repo

import com.example.dayj.datastore.SelfUserAccountDataStore
import com.example.dayj.datastore.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginAuthorizationRepositoryImpl @Inject constructor(private val userAccountDataStore: SelfUserAccountDataStore) :
    LoginAuthorizationRepository {
    override val accessToken: Flow<String>
        get() = userAccountDataStore.accessTokenFlow
    override val refreshToken: Flow<String>
        get() = userAccountDataStore.refreshTokenFlow
    override val userInfoFlow: Flow<UserInfo?>
        get() = userAccountDataStore.userInfoFlow

    override suspend fun updateUserID(accessToken: String, refreshToken: String) {
        userAccountDataStore.setAccountInfo(accessToken, refreshToken)
    }

    override suspend fun clearData() {
        userAccountDataStore.clearData()
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        userAccountDataStore.setUserInfo(userInfo)
    }

    override suspend fun clearUserInfo() {
        userAccountDataStore.clearUserInfo()
    }
}