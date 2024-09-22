package com.example.dayj.data.repo

import com.example.dayj.datastore.UserInfo
import com.example.dayj.network.api.UserService
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userService: UserService) :
    UserRepository {
    override suspend fun getUsers(): Response<List<UserInfo>> =
        userService.getUsers()
}