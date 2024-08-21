package com.dayj.dayj

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)

    fun String.calculateUpdatedAgo(): String {
        return runCatching {
            val date = dateFormat.parse(this)
            val diff = (Date().time - date.time)

            if((diff / 86400000) >= 1) {
                "${diff / 86400000}일 전"
            } else if((diff / 3600000) >= 1) {
                "${diff / 3600000}시간 전"
            } else {
                "${diff / 60000}분 전"
            }
        }.getOrNull() ?: ""
    }
}