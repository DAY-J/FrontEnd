package com.example.dayj.datastore.ext

import com.example.dayj.datastore.UserInfo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


inline fun <reified T> T.joinToString(): String {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val type = Types.newParameterizedType(T::class.java, UserInfo::class.java)
    val adapter: JsonAdapter<T> = moshi.adapter(type)
    return adapter.toJson(this)
}


inline fun <reified T> String.jsonStringToModel(): T? {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val adapter: JsonAdapter<T> =
        moshi.adapter(Types.newParameterizedType(T::class.java, UserInfo::class.java))
    return adapter.fromJson(this)
}
