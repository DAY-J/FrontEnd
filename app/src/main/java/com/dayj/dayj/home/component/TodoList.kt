package com.dayj.dayj.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dayj.dayj.network.api.response.PlanResponse
import com.dayj.dayj.ui.theme.GrayA6
import com.dayj.dayj.ui.theme.RedFF00
import com.dayj.dayj.ui.theme.textStyle

@Composable
fun TodoList(
    list: List<PlanResponse>,
    onItemClick: (PlanResponse) -> Unit,
    onUpdatePlanCheck: (PlanResponse) -> Unit,
    onDelete: (PlanResponse) -> Unit,
    onRouteUpdate: (PlanResponse) -> Unit
) {

    var isShowItemDialog by remember { mutableStateOf(false) }
    var selectItem by remember { mutableStateOf<PlanResponse?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        if (list.isEmpty()) {
            item {
                Text(
                    text = "선택한 날짜에 일정이 없습니다!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(list) {
                TodoItem(
                    item = it,
                    onItemClick = onItemClick,
                    onUpdateCheck = onUpdatePlanCheck,
                    onItemLongClick = {
                        selectItem = it
                        isShowItemDialog = true
                    }
                )
            }
        }
    }
    selectItem?.let { item ->
        if (isShowItemDialog) {
            Dialog(
                onDismissRequest = {
                    isShowItemDialog = false
                    selectItem = null
                },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {

                Surface(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "수정",
                            Modifier
                                .padding(horizontal = 40.dp, vertical = 15.dp)
                                .clickable {
                                    onRouteUpdate(item)
                                    isShowItemDialog = false
                                    selectItem = null
                                },
                            style = textStyle.copy(fontWeight = FontWeight.Bold, color = RedFF00)
                        )

                        androidx.compose.material3.HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = GrayA6
                        )

                        Text(
                            text = "삭제",
                            Modifier
                                .padding(horizontal = 40.dp, vertical = 15.dp)
                                .clickable {
                                    onDelete(item)
                                    isShowItemDialog = false
                                    selectItem = null
                                },
                            style = textStyle.copy(fontWeight = FontWeight.Bold, color = GrayA6)
                        )
                    }
                }
            }

        }
    }


}