package com.dayj.dayj.home.component.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarSheet(
    pagerState: PagerState,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = modifier
    ) { page ->
        val date = LocalDate.of(
            CALENDAR_RANGE.startYear + page / 12,
            page % 12 + 1,
            1
        )
        CalendarMonth(
            selectedDate = selectedDate,
            currentDate = date,
            onSelectedDate = {
                onSelectedDate(it)
            }
        )
    }
}
