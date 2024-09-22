package com.example.dayj.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayj.network.api.response.PlanResponse
import com.example.dayj.ui.home.component.PlanTagSelector
import com.example.dayj.ui.home.component.TodoList
import com.example.dayj.ui.home.component.calendar.HorizontalCalendar
import com.example.dayj.ui.theme.DividerColor
import java.time.LocalDate


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navToAddToDo: (LocalDate) -> Unit,
    navToUpdateTodo : (PlanResponse) -> Unit
) {

    val state = viewModel.homeViewState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getPlans(date = state.selectedData)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalCalendar(
            selectedDate = state.selectedData,
            updateSelectedDate = viewModel::updateDate,
            onRouteTodo = navToAddToDo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            thickness = 1.dp,
            color = DividerColor
        )

        PlanTagSelector(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
            selectTag = state.selectTag,
            onChangedPlanTag = viewModel::changeSelectPlanTag
        )

        TodoList(
            list = state.getFilterPlans(),
            onUpdatePlanCheck = viewModel::updatePlanCheck,
            onItemClick = {

            },
            onDelete = viewModel::deletePlan,
            onRouteUpdate = navToUpdateTodo
        )
    }
}
