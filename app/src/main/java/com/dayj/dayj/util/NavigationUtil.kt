package com.dayj.dayj.util

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson

object NavigationUtil {

    inline fun <reified T : Parcelable> assetParamTypeOf(): NavType<T> {
        return object : NavType<T>(isNullableAllowed = true) {
            override fun get(bundle: Bundle, key: String): T? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): T {
                return Gson().fromJson(value, T::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: T) {
                bundle.putParcelable(key, value)
            }
        }
    }

    fun passItem(model: Any): String {
        return Uri.encode(Gson().toJson(model))
    }
}
