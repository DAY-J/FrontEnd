package com.dayj.dayj.statistics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayj.dayj.ext.toDayOfWeekName
import com.dayj.dayj.statistics.StatisticsViewState
import com.dayj.dayj.ui.theme.Black2A
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.Green5d
import com.dayj.dayj.ui.theme.textStyle
import java.time.LocalDate

@Composable
fun StatisticsResult(
    state: StatisticsViewState
) {
    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = state.tag.hashTag.replace("#", ""),
        style = TextStyle(
            color = Black3A,
            fontSize = 16.sp
        )
    )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = state.getStatisticsAllPercent(),
        style = TextStyle(
            color = Black2A,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    )
    Spacer(modifier = Modifier.height(20.dp))


    if (state.statistics.isEmpty()) {
        Text(
            text = "결과가 없습니다.",
            Modifier.fillMaxWidth(),
            style = textStyle.copy(textAlign = TextAlign.Center)
        )
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            state.statistics.sortedBy { LocalDate.parse(it.date).dayOfWeek.value }.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .fillMaxHeight(item.value / 100f)
                                .background(Green5d, RoundedCornerShape(9.dp))
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(text = LocalDate.parse(item.date).toDayOfWeekName(), style = textStyle)
                        Text(text = "${item.value}%", style = textStyle.copy(fontSize = 12.sp), maxLines = 1)
                    }
                }
            }
        }
    }


}