package com.example.dayj.ui.mypage.data

import com.example.dayj.data.PreferenceManager
import com.example.dayj.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferenceManager: PreferenceManager
): UserDataSource {
    override suspend fun modifyNickName(nickName: String): Flow<String> = flow {
        val userId = preferenceManager.getUserId()
        val response = apiService.patchNickName(
            userId = userId,
            requestModifyNickName = RequestModifyNickName(nickname = nickName)
        )
        emit("")
//        if(response.stat) {
//
//        } else if(response.code() == 409){
//            emit("중복된 닉네임입니다.")
//        } else {
//            emit(response.errorBody()?.string() ?: "")
//        }
    }
}