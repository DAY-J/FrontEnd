package com.example.dayj.datastore

data class UserInfo(
    val id: Int,
    val username: String,
    val password: String,
    val role: String,
    val nickname: String,
    val profilePhoto: String? = null,
    val isAlarm: Boolean
)