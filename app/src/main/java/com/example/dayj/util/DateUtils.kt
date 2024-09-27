package com.example.dayj.util

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateUtils {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.ss")
    val postingDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ss", Locale.KOREAN)
    val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    fun String.calculateUpdatedAgo(): String {
        return runCatching {
            val todayDateString = postingDateFormat.format(Date())
            val postingDate = LocalDateTime.parse(this, formatter)
            val todayDate = LocalDateTime.parse(todayDateString, formatter)
            getTimeDifference(postingDate, todayDate)
        }.getOrElse {
            val todayDateString = dateFormat.format(Date())
            val postingDate = LocalDateTime.parse(this)
            val todayDate = LocalDateTime.parse(todayDateString)
            getTimeDifference(postingDate, todayDate)
        }
    }


    fun getTimeDifference(start: LocalDateTime, end: LocalDateTime): String {
        // 두 날짜의 차이를 초 단위로 계산
        val duration = Duration.between(start, end)

        // 차이를 일, 시간, 분으로 변환
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60

        // 차이에 따른 결과 반환
        return when {
            days > 0 -> "${days}일 전"
            hours > 0 -> "${hours}시간 전"
            minutes > 0 -> "${minutes}분 전"
            else -> "방금 전"
        }
    }
}