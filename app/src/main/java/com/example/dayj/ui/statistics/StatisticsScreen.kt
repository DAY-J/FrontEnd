package com.example.dayj.ui.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayj.ui.home.component.PlanTagSelector
import com.example.dayj.ui.statistics.component.StatisticsDateDialog
import com.example.dayj.ui.statistics.component.StatisticsResult
import com.example.dayj.ui.statistics.component.StatisticsTitle

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel()
) {

    val state = viewModel.statisticsViewState.collectAsState().value

    var isShowStartDateDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getStatistics()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        StatisticsTitle(
            selectDate = state.getStatisticsDateString(),
            onShowDialog = { isShowStartDateDialog = true }
        )
        PlanTagSelector(
            selectTag = state.tag,
            onChangedPlanTag = viewModel::updatePlanTag
        )
        StatisticsResult(state)
    }

    if (isShowStartDateDialog) {
        StatisticsDateDialog(
            selectedDate = state.startDate,
            onChangedDate = viewModel::updateStartDate,
            onDismiss = {
                isShowStartDateDialog = false
            }
        )
    }
}