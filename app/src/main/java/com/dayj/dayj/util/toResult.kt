package com.dayj.dayj.util

import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            Result.Success(body)
        } else {
            Result.Fail.Exception(code(), "Response body is null")
        }
    } else {
        errorBody()?.string()?.let {
            try {
                val errorObject = JSONObject(it)
                val message = errorObject.getString("message")
                val code = errorObject.getInt("errorCode")

                val payload = if (errorObject.has("payload")) {
                    errorObject.getString("payload")
                } else {
                    ""
                }
                Result.Fail.Exception(code, message, payload)
            } catch (e: Exception) {
                Result.Fail.Exception(code(), message())
            }
        } ?: Result.Fail.Exception(code(), message())
    }
}