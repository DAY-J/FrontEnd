package com.example.dayj.ui.home.component.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarHeader(
    title: String,
    modifier: Modifier = Modifier,
    onBeforeMonthClick: () -> Unit,
    onAfterMonthClick: () -> Unit,
    onAddTodo: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.size(40.dp))

        Box(modifier = modifier.fillMaxWidth()) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {

                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        onBeforeMonthClick()
                    }
                )

                Spacer(modifier = Modifier.width(25.dp))

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(25.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        onAfterMonthClick()
                    })
            }

            Icon(
                imageVector = Icons.Default.Add, contentDescription = "", modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .clickable {
                        onAddTodo()
                    }
            )
        }
        Spacer(modifier = Modifier.size(20.dp))

        Row(modifier) {
            val dayOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
            dayOfWeek.forEach { day ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .weight(1f),
                    text = day,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}