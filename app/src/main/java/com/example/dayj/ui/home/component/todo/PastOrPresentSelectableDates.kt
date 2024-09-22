package com.example.dayj.ui.home.component.todo

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}