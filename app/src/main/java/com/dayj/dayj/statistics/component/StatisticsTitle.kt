package com.dayj.dayj.statistics.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.DividerColor
import com.dayj.dayj.ui.theme.Gray6F
import com.dayj.dayj.ui.theme.Green12
import com.dayj.dayj.ui.theme.textStyle

@Composable
fun StatisticsTitle(
    selectDate: String,
    onShowDialog: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "통계",
        style = TextStyle(
            color = Black3A,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp
        )
    )

    Spacer(modifier = Modifier.height(14.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = selectDate,
            style = textStyle.copy(fontSize = 18.sp, color = Gray6F)
        )
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onShowDialog),
            tint = Green12
        )
    }

    Spacer(modifier = Modifier.height(14.dp))

    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = DividerColor
    )

    Spacer(modifier = Modifier.height(16.dp))

}