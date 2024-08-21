package com.dayj.dayj.home.component.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun CalendarMonth(
    selectedDate: LocalDate,
    currentDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val lastDay by remember { mutableIntStateOf(currentDate.lengthOfMonth()) }    // 해당 달의 마지막 날
    val firstDayOfWeek by remember { mutableIntStateOf(currentDate.dayOfWeek.value) }  // 1일
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }    // 해당 달의 총 일수 리스트

    Column(modifier = modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 1일이 시작하는 요일 전까지 공백 생성, 일요일부터 시작할 수 있도록 +1
            if(firstDayOfWeek +1 != 8) {
                for (i in 1 until firstDayOfWeek +1) {
                    item {
                        Box(modifier = Modifier.size(32.dp))
                    }
                }
            }

            items(items = days) { day ->
                val date = currentDate.withDayOfMonth(day)

                CalendarDay(
                    date = date,
                    isSelected = date.isEqual(selectedDate),
                    onSelectedDate = {
                        onSelectedDate(it)
                    }
                )
            }
        }
    }
}