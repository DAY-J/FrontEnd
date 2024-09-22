package com.example.dayj.network.api

import com.example.dayj.datastore.UserInfo
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/api/app-user")
    suspend fun getUsers(): Response<List<UserInfo>>

}