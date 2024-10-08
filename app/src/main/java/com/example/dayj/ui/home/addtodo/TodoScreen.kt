package com.example.dayj.ui.home.addtodo

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dayj.network.api.response.PlanOptionRequest
import com.example.dayj.network.api.response.PlanTag
import com.example.dayj.ui.home.component.PlanTagSelector
import com.example.dayj.ui.home.component.TodoSwitch
import com.example.dayj.ui.home.component.todo.PlanOptionType
import com.example.dayj.ui.home.component.todo.RecommendItem
import com.example.dayj.ui.home.component.todo.TodoOptions
import com.example.dayj.ui.home.component.todo.TodoTitle
import com.example.dayj.ui.theme.Gray6F
import com.example.dayj.ui.theme.GrayC0
import com.example.dayj.ui.theme.textStyle
import com.example.dayj.util.collectInLaunched

object TodoScreen {


    @Composable
    fun Add(
        navController: NavController,
        viewModel: AddTodoViewModel = hiltViewModel()
    ) {

        val context = LocalContext.current
        val state = viewModel.addTodoViewState.collectAsState().value

        viewModel.addTodoViewEffect.collectInLaunched { result ->
            when (result) {
                TodoViewEffect.CompleteSave -> {
                    navController.navigateUp()
                }

                is TodoViewEffect.ShowToast -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        BaseTodoScreen(
            "할 일 추가하기",
            goal = state.planRequest.goal,
            onChangedGoal = viewModel::updateGoal,
            planTag = PlanTag.valueOf(state.planRequest.planTag),
            onChangedPlanTag = viewModel::updatePlanTag,
            recommendTodoList = state.recommendTodoList,
            isPublic = state.planRequest.isPublic,
            onChangedIsPublic = viewModel::updateIsPublic,
            option = state.planOptionRequest,
            onChangedPlanOption = viewModel::updatePlanOption,
            onSave = viewModel::save,
            onBack = { navController.navigateUp() }
        )
    }

    @Composable
    fun Update(
        navController: NavController,
        viewModel: UpdateTodoViewModel = hiltViewModel()
    ) {
        val context = LocalContext.current
        val state = viewModel.updateTodoViewState.collectAsState().value

        viewModel.updateTodoViewEffect.collectInLaunched { result ->
            when (result) {
                TodoViewEffect.CompleteSave -> {
                    navController.navigateUp()
                }

                is TodoViewEffect.ShowToast -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        BaseTodoScreen("할 일 수정하기", goal = state.planRequest.goal,
            onChangedGoal = viewModel::updateGoal,
            planTag = PlanTag.valueOf(state.planRequest.planTag),
            onChangedPlanTag = viewModel::updatePlanTag,
            isPublic = state.planRequest.isPublic,
            recommendTodoList = state.recommendTodoList,
            onChangedIsPublic = viewModel::updateIsPublic,
            option = state.planOptionRequest,
            onChangedPlanOption = viewModel::updatePlanOption,
            onSave = viewModel::update,
            onBack = { navController.navigateUp() }
        )
    }

    @Composable
    private fun BaseTodoScreen(
        title: String,
        goal: String,
        onChangedGoal: (String) -> Unit = {},
        planTag: PlanTag = PlanTag.HEALTH,
        recommendTodoList: List<String> = emptyList(),
        onChangedPlanTag: (PlanTag) -> Unit = {},
        isPublic: Boolean = true,
        onChangedIsPublic: (Boolean) -> Unit = {},
        option: PlanOptionRequest = PlanOptionRequest(),
        onChangedPlanOption: (PlanOptionRequest) -> Unit = {},
        onSave: () -> Unit = {},
        onBack: () -> Unit = {}
    ) {

        var isRecommendTodo by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            TodoTitle(title, onSave, onBack)

            OutlinedTextField(
                value = goal,
                onValueChange = onChangedGoal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true,
                placeholder = {
                    Text(text = "할 일을 적어주세요", style = textStyle.copy(color = GrayC0))
                },
                suffix = {
                    Text(
                        text = "${goal.length}/50",
                        style = textStyle.copy(color = GrayC0, fontSize = 12.sp)
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
            ) {
                PlanTagSelector(
                    selectTag = planTag,
                    onChangedPlanTag = onChangedPlanTag,
                    isShowAllPlan = false,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f, fill = true),
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (isRecommendTodo) {
                            if (recommendTodoList.isNotEmpty()) {
                                recommendTodoList.forEachIndexed { index, todo ->
                                    RecommendItem(
                                        item = todo,
                                        isShowDivider = index != recommendTodoList.lastIndex,
                                        onClick = {
                                            onChangedGoal(todo)
                                        }
                                    )
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp),
                                    contentAlignment = Center
                                ) {
                                    Text(
                                        text = "리마인더가 없습니다.",
                                        style = textStyle.copy(color = Gray6F),
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "리마인더",
                            style = textStyle.copy(fontSize = 12.sp, color = Gray6F)
                        )
                        TodoSwitch(
                            checked = isRecommendTodo,
                            onCheckedChange = { isRecommendTodo = !isRecommendTodo },
                        )
                    }

                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
            ) {
                PlanOptionType.entries.forEach { type ->
                    TodoOptions(
                        type = type,
                        isPublic = isPublic,
                        onChangedIsPublic = onChangedIsPublic,
                        option = option,
                        onChangedPlanOption = onChangedPlanOption
                    )
                }
            }
        }
    }

}