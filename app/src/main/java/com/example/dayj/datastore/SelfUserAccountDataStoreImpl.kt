package com.example.dayj.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.dayj.datastore.ext.joinToString
import com.example.dayj.datastore.ext.jsonStringToModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SelfUserAccountDataStoreImpl constructor(private val context: Context) :
    SelfUserAccountDataStore {


    private val accessTokenKey =
        stringPreferencesKey("field1")

    private val refreshTokenKey =
        stringPreferencesKey("field2")

    private val userInfoKey =
        stringPreferencesKey("field3")

    override val accessTokenFlow: Flow<String>
        get() = context.dataFileStore.data.catch { exception ->
            when (exception) {
                is IOException -> {
                    emit(emptyPreferences())
                }

                else -> throw exception
            }
        }.map { preferences ->
            preferences[accessTokenKey] ?: ""
        }
    override val refreshTokenFlow: Flow<String>
        get() = context.dataFileStore.data.catch { exception ->
            when (exception) {
                is IOException -> {
                    emit(emptyPreferences())
                }

                else -> throw exception
            }
        }.map { preferences ->
            preferences[refreshTokenKey] ?: ""
        }
    override val userInfoFlow: Flow<UserInfo?>
        get() = context.dataFileStore.data.catch { exception ->
            when (exception) {
                is IOException -> {
                    emit(emptyPreferences())
                }

                else -> throw exception
            }
        }.map { preferences ->
            preferences[userInfoKey]?.jsonStringToModel()
        }


    override suspend fun setAccountInfo(accessToken: String, refreshToken: String) {
        setAccountToken(accessToken)
        setRefreshToken(refreshToken)
    }

    override suspend fun setUserInfo(userInfo: UserInfo) {
        context.dataFileStore.edit { mutablePreferences ->
            mutablePreferences[userInfoKey] = userInfo.joinToString()
        }
    }

    private suspend fun setAccountToken(accessTokenString: String) {
        context.dataFileStore.edit { mutablePreferences ->
            mutablePreferences[accessTokenKey] = accessTokenString

        }
    }

    private suspend fun setRefreshToken(refreshTokenString: String) {
        context.dataFileStore.edit { mutablePreferences ->
            mutablePreferences[refreshTokenKey] = refreshTokenString
        }
    }

    override suspend fun clearData() {
        setAccountToken("")
        setRefreshToken("")
    }

    override suspend fun clearUserInfo() {
        context.dataFileStore.edit { mutablePreferences ->
            mutablePreferences[userInfoKey] = ""
        }
    }

}


