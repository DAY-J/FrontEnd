package com.dayj.dayj.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayj.dayj.network.api.response.PlanTag
import com.dayj.dayj.ui.theme.BorderColor
import com.dayj.dayj.ui.theme.Green12
import com.dayj.dayj.ui.theme.SelectPlanTagColor

@Composable
fun PlanTagSelector(
    modifier: Modifier = Modifier,
    isShowAllPlan: Boolean = true,
    selectTag: PlanTag,
    onChangedPlanTag: (PlanTag) -> Unit
) {
    val tagList = if (isShowAllPlan) PlanTag.entries.toList()
    else PlanTag.entries.toList().filter { it != PlanTag.ALL }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tagList) {
            PlanTagItem(item = it, isSelected = selectTag == it, onItemClick = onChangedPlanTag)
        }
    }
}

@Composable
fun PlanTagItem(
    item: PlanTag,
    isSelected: Boolean,
    onItemClick: (PlanTag) -> Unit
) {

    val (modifier, textStyle) = if (isSelected) {
        Modifier.background(
            SelectPlanTagColor,
            RoundedCornerShape(12.dp)
        ) to TextStyle(
            fontSize = 12.sp,
            lineHeight = 19.sp,
            letterSpacing = (-0.24).sp,
            color = Green12,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    } else {
        Modifier
            .background(
                Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(12.dp)
            ) to TextStyle(
            fontSize = 12.sp,
            lineHeight = 19.sp,
            letterSpacing = (-0.24).sp,
            textAlign = TextAlign.Center,
            color = BorderColor,
            fontWeight = FontWeight.Normal
        )
    }

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(30.dp)
            .then(modifier)
            .clickable { onItemClick(item) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = item.hashTag, style = textStyle, overflow = TextOverflow.Ellipsis)
    }

}