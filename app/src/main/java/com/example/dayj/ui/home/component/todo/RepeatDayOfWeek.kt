package com.example.dayj.ui.home.component.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dayj.ui.theme.GrayA6
import com.example.dayj.ui.theme.Green12
import com.example.dayj.ui.theme.textStyle

@Composable
fun RepeatDayOfWeek(
    modifier: Modifier = Modifier,
    selectDays: Set<RepeatDayType>,
    onChangedDays: (Set<RepeatDayType>) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RepeatDayType.entries.forEach { item ->
            DayOfWeekItem(item, selectDays.contains(item),
                toggleSelected = {
                    if (selectDays.contains(it)) {
                        onChangedDays(selectDays - it)
                    } else {
                        onChangedDays(selectDays + it)
                    }
                }
            )
        }
    }
}

@Composable
fun DayOfWeekItem(
    item: RepeatDayType,
    isSelected: Boolean,
    toggleSelected: (RepeatDayType) -> Unit,
) {

    val (backgroundColor, textColor) = if (isSelected) {
        Green12 to Color.White
    } else {
        GrayA6 to Color.White
    }
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(backgroundColor, CircleShape)
            .clickable { toggleSelected(item) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = item.day, style = textStyle.copy(color = textColor))
    }
}
