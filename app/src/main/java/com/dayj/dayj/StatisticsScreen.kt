package com.dayj.dayj

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dayj.dayj.ui.theme.Black3A

@Composable
fun StatisticsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Statistics Screen",
            style = TextStyle(
                color = Black3A,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        )
    }
}