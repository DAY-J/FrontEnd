package com.dayj.dayj.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayj.dayj.R
import com.dayj.dayj.ui.theme.Background
import com.dayj.dayj.ui.theme.Black2A
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen() {
    var currentYear by remember { mutableStateOf(LocalDate.now().year) }
    var currentMonth by remember { mutableStateOf(LocalDate.now().monthValue) }
    val weekList = listOf("월", "화", "수", "목", "금", "토", "일")

    fun daysInMonth(month: Int, year: Int): Int {
        return when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    val firstDayOfMonth = LocalDate.of(
        currentYear,
        currentMonth,
        1,
    )
    val dayOfWeek = firstDayOfMonth.dayOfWeek.value
    val daysInCurrentMonth = daysInMonth(currentMonth, currentYear)

    val dates: List<CalendarDate> = (1..daysInCurrentMonth).map { day ->
        CalendarDate(day, currentMonth, currentYear)
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 77.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.Absolute.Left) {
                Text(
                    text = "${currentYear}년 ${currentMonth}월",
                    modifier = Modifier
                        .size(width = 200.dp, height = 40.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = "keyboardArrowLeft",
                    modifier = Modifier
                        .clickable {
                            if (currentMonth - 1 < 1) {
                                currentYear--
                                currentMonth = 12
                            } else currentMonth--
                        }
                )
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "keyboardArrowRight",
                    modifier = Modifier
                        .clickable {
                            if (currentMonth + 1 > 12) {
                                currentYear++
                                currentMonth = 1
                            } else currentMonth++
                        }
                )
            }
            Row() {
                Button(modifier = Modifier.size(width = 24.dp, height = 34.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Background,
                        contentColor = Black2A,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = Black2A,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(modifier = Modifier.size(width = 24.dp, height = 34.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Background,
                        contentColor = Black2A,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    onClick = { /* 수정 */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        tint = Black2A,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 135.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        FlowRow(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            weekList.forEach { day ->
                Text(
                    text = day,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        lineHeight = 22.sp,
                    ),
                    color = Black2A,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        FlowRow(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                )
                .fillMaxWidth(),
            maxItemsInEachRow = 7,
        ) {
            var startOfMonth = 1
            var select by remember { mutableStateOf("") }
            dates.forEach { date ->
                if (dayOfWeek > startOfMonth) {
                    for (i in 2..dayOfWeek) {
                        Spacer(modifier = Modifier.weight(1f))
                        startOfMonth++
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .weight(1f)
                        .clickable {
                            select = "${date.year}${date.month}${date.day}"
                        }
                ) {
                    startOfMonth++
                    if (select == "${date.year}${date.month}${date.day}") {
                        Image(
                            painter = painterResource(id = R.drawable.ellipse_1),
                            contentDescription = "borderIcon",
                            modifier = Modifier.fillMaxWidth(),
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ellipse_1),
                            contentDescription = "defaultIcon",
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    Text(
                        text = (date.day).toString(),
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 22.sp,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = if (
                            LocalDate.now().dayOfMonth == date.day &&
                            LocalDate.now().monthValue == date.month &&
                            LocalDate.now().year == date.year
                        ) Color(0xFF12461f) else Color.Black,
                    )
                }
                if (startOfMonth == daysInCurrentMonth + dayOfWeek) {
                    for (i in 1..7) Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
