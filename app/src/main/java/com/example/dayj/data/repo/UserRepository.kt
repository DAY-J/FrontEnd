package com.example.dayj.data.repo

import com.example.dayj.datastore.UserInfo
import retrofit2.Response

interface UserRepository {

    suspend fun getUsers(): Response<List<UserInfo>>
}