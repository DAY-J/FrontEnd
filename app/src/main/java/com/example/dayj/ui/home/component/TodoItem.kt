package com.example.dayj.ui.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.dayj.R
import com.example.dayj.network.api.response.PlanResponse
import com.example.dayj.ui.theme.CalendarSelectedDateColor
import com.example.dayj.ui.theme.CheckBoxColor
import com.example.dayj.ui.theme.CompleteColor
import com.example.dayj.ui.theme.textStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    item: PlanResponse,
    onItemClick: (PlanResponse) -> Unit,
    onItemLongClick: (PlanResponse) -> Unit,
    onUpdateCheck: (PlanResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(80.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .then(modifier)
            .combinedClickable(
                onClick = { onItemClick(item) },
                onLongClick = { onItemLongClick(item) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.goal,
                style = if (item.isComplete) {
                    textStyle.copy(
                        color = CompleteColor,
                        textDecoration = TextDecoration.LineThrough
                    )
                } else {
                    textStyle
                }
            )

            if (item.hasTimeOption()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "",
                        tint = if (item.isComplete) {
                            CompleteColor
                        } else {
                            CalendarSelectedDateColor
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.getStartEndTime(),
                        style = textStyle.copy(
                            color = if (item.isComplete) {
                                CompleteColor
                            } else {
                                CalendarSelectedDateColor
                            }
                        )
                    )
                }
            }
        }

        Checkbox(
            checked = item.isComplete,
            onCheckedChange = { onUpdateCheck(item) },
            colors = CheckboxDefaults.colors(
                uncheckedColor = CheckBoxColor[0],
                checkedColor = CheckBoxColor[1]
            )
        )
    }
}
