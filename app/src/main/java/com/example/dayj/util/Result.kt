package com.example.dayj.util

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>

    sealed class Fail : Result<Nothing> {
        data class Exception(val errorCode: Int, val message: String, val payload: String = "") : Fail()
    }
}