package com.example.dayj.data

import android.content.Context
import android.os.Parcelable
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

class PreferenceManager(
    @ApplicationContext private val context: Context
) {
    private val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun putUserId(userId: Int) {
        putValue(USER_ID, userId)
    }

    fun putUserName(name: String) {
        putValue(USER_NAME, name)
    }

    fun getUserId(): Int {
        return getInt(USER_ID)
    }

    fun getUserName(): String {
        return getString(USER_NAME)
    }

    fun getString(key: String): String {
        return sharedPrefs.getString(key, "") ?: ""
    }

    fun getInt(key: String): Int {
        return sharedPrefs.getInt(key, -1)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPrefs.getBoolean(key, false)
    }

    fun putValue(key: String, value: Any) {
        val editor = sharedPrefs.edit()
        when(value) {
            is String -> { editor.putString(key, value) }
            is Int -> { editor.putInt(key, value) }
            is Float -> { editor.putFloat(key, value) }
            is Long -> { editor.putLong(key, value) }
            is Boolean -> { editor.putBoolean(key, value) }
            is Parcelable -> {
                val jsonData = Gson().toJson(value)
                editor.putString(key, jsonData)
            }
            else -> {}
        }
        editor.apply()
    }


    companion object {
        private const val USER_ID = "USER_ID"
        private const val USER_NAME = "USER_NAME"
        private const val PREF_NAME = "DAY_J_PREFERENCES"
    }
}