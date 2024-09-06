package com.dayj.dayj.ext

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun LocalDate.toConvertPlanDate(): String {
    return "${this.year}-${monthValue.toString().padStart(2, '0')}-${
        this.dayOfMonth.toString().padStart(2, '0')
    }"
}

fun LocalDate.toCalenderTitle(): String =
    "${monthValue.toString().padStart(2, '0')}월 $year"

fun LocalDate.toDayOfWeekName() : String =
    when(this.dayOfWeek.value) {
        1 -> "월"
        2 -> "화"
        3 -> "수"
        4 -> "목"
        5 -> "금"
        6 -> "토"
        7 -> "일"
        else -> "일"
    }


fun formatLocalTime(localTime: LocalTime): String {
    val currentDate = LocalDateTime.now().toLocalDate()
    val dateTime = LocalDateTime.of(currentDate, localTime)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    return dateTime.format(formatter)
}

fun formatLocalDateTime(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    return localDateTime.format(formatter)
}

fun formatLocalDate(localDate: LocalDate, format: String = LocalDateFormat.PLAN_FORMAT): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    val dateTime = LocalDateTime.of(localDate, LocalTime.now())
    return dateTime.format(formatter)
}

object LocalDateFormat {
    const val PLAN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"

    const val STATISTIC_FORMAT = "yyyy-MM-dd"
}


fun extractTime(dateTimeString: String): String {
    val regex = Regex("""T(\d{2}:\d{2})""")
    val matchResult = regex.find(dateTimeString)
    return matchResult?.groupValues?.get(1) ?: "Time not found"
}