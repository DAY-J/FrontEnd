package com.dayj.dayj.home.component.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dayj.dayj.ext.toCalenderTitle
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    updateSelectedDate: (LocalDate) -> Unit,
    onRouteTodo : () -> Unit
) {

    val initialPage =
        (selectedDate.year - CALENDAR_RANGE.startYear) * 12 + selectedDate.monthValue - 1
    val pageCount = (CALENDAR_RANGE.lastYear - CALENDAR_RANGE.startYear) * 12

    val pagerState = rememberPagerState(pageCount = { pageCount }, initialPage = initialPage)

    var currentDate by remember { mutableStateOf(selectedDate) }
    var currentPage by remember { mutableIntStateOf(initialPage) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = pagerState.currentPage) {
        val swipe = (pagerState.currentPage - currentPage).toLong()
        currentDate = currentDate.plusMonths(swipe)
        currentPage = pagerState.currentPage
    }
    Column(
        modifier = modifier
    ) {
        CalendarHeader(
            title = currentDate.toCalenderTitle(),
            onBeforeMonthClick = {
                scope.launch { pagerState.scrollToPage(currentPage - 1) }
            },
            onAfterMonthClick = {
                scope.launch { pagerState.scrollToPage(currentPage + 1) }
            },
            onAddTodo = onRouteTodo
        )
        CalendarSheet(pagerState = pagerState, selectedDate = selectedDate, onSelectedDate = updateSelectedDate)
    }
}

object CALENDAR_RANGE {
    const val startYear = 2010
    const val lastYear = 2030
}
