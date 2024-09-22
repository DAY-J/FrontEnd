package com.example.dayj.ui.home.component.todo

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.example.dayj.ext.formatLocalDate
import com.example.dayj.ext.formatLocalDateTime
import com.example.dayj.network.api.response.PlanOptionRequest
import com.example.dayj.ui.home.component.TodoSwitch
import com.example.dayj.ui.theme.CalendarSelectedDateColor
import com.example.dayj.ui.theme.GrayA6
import com.example.dayj.ui.theme.Green12
import com.example.dayj.ui.theme.SelectPlanTagColor
import com.example.dayj.ui.theme.TextBlack
import com.example.dayj.ui.theme.textStyle
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TodoOptions {

    @Composable
    operator fun invoke(
        type: PlanOptionType,
        isPublic: Boolean = true,
        onChangedIsPublic: (Boolean) -> Unit = {},
        option: PlanOptionRequest = PlanOptionRequest(),
        onChangedPlanOption: (PlanOptionRequest) -> Unit = {},
    ) {
        when (type) {
            PlanOptionType.ALLOW_FRIEND -> OptionPublicFriend(
                isPublic = isPublic,
                onChangedIsPublic = onChangedIsPublic
            )

            PlanOptionType.ALARM -> OptionAlarm(
                option = option,
                onChangedPlanOption = onChangedPlanOption
            )

            PlanOptionType.TIME -> OptionTime(
                option = option,
                onChangedPlanOption = onChangedPlanOption
            )

            PlanOptionType.REPEAT -> OptionRepeat(
                option = option,
                onChangedPlanOption = onChangedPlanOption
            )
        }
    }

    @Composable
    private fun OptionPublicFriend(
        type: PlanOptionType = PlanOptionType.ALLOW_FRIEND,
        isPublic: Boolean = true,
        onChangedIsPublic: (Boolean) -> Unit = {},
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = type.image), contentDescription = "")
                }
                Text(text = type.title, style = textStyle.copy(color = TextBlack))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable {

                }
            ) {
                //친구인 경우 not Public
                TodoSwitch(
                    checked = !isPublic,
                    onCheckedChange = {
                        onChangedIsPublic(!isPublic)
                    },
                )
            }
        }
    }

    @Composable
    private fun OptionAlarm(
        type: PlanOptionType = PlanOptionType.ALARM,
        option: PlanOptionRequest = PlanOptionRequest(),
        onChangedPlanOption: (PlanOptionRequest) -> Unit = {},
    ) {

        var isExpand by remember { mutableStateOf(false) }

        var currentTime by remember { mutableStateOf(LocalDateTime.now()) }

        val (text, style, icon) = if (option.planAlarmTime == null) {
            Triple(
                "없음", textStyle.copy(color = GrayA6), Icons.Default.Menu
            )
        } else {
            Triple(
                currentTime.format(DateTimeFormatter.ofPattern("a hh:mm")),
                textStyle.copy(color = Green12),
                Icons.Default.Clear
            )
        }

        LaunchedEffect(Unit) {
            Log.d("결과", option.planAlarmTime.toString())
            if (option.planAlarmTime != null) {
                currentTime =
                    LocalDateTime.parse(option.planAlarmTime, DateTimeFormatter.ISO_DATE_TIME)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = type.image), contentDescription = "")
                    }
                    Text(text = type.title, style = textStyle.copy(color = TextBlack))
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.clickable {
                            isExpand = !isExpand
                        }
                    ) {
                        Text(text = text, style = style)
                        Icon(
                            imageVector = icon,
                            contentDescription = "",
                            tint = if (option.planAlarmTime == null) GrayA6 else Green12,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    if (option.planAlarmTime != null) {
                                        onChangedPlanOption(option.copy(planAlarmTime = null))
                                        currentTime = LocalDateTime.now()
                                    } else {
                                        isExpand = true
                                        onChangedPlanOption(
                                            option.copy(
                                                planAlarmTime = formatLocalDateTime(
                                                    currentTime
                                                )
                                            )
                                        )
                                    }
                                }
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(10.dp),
                        expanded = isExpand, onDismissRequest = { isExpand = false }
                    ) {
                        WheelTimePicker(
                            selectorProperties = WheelPickerDefaults.selectorProperties(
                                color = SelectPlanTagColor,
                                border = BorderStroke(1.dp, CalendarSelectedDateColor)
                            ),
                            timeFormat = TimeFormat.AM_PM
                        ) { suspendTime ->
                            currentTime = currentTime.toLocalDate().atTime(suspendTime)
                            onChangedPlanOption(
                                option.copy(
                                    planAlarmTime = formatLocalDateTime(
                                        currentTime.toLocalDate().atTime(suspendTime)
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OptionTime(
        type: PlanOptionType = PlanOptionType.TIME,
        option: PlanOptionRequest = PlanOptionRequest(),
        onChangedPlanOption: (PlanOptionRequest) -> Unit = {},
    ) {
        var isExpand by remember { mutableStateOf(false) }

        var startTime by remember {
            mutableStateOf(LocalDate.now().atTime(0, 0, 0))
        }
        var endTime by remember {
            mutableStateOf(LocalDate.now().atTime(1, 0, 0))
        }
        var isShowStartTimePicker by remember { mutableStateOf(false) }
        var isShowEndTimePicker by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            if (option.planStartTime != null && option.planEndTime != null) {
                isExpand = true
                startTime =
                    LocalDateTime.parse(option.planStartTime, DateTimeFormatter.ISO_DATE_TIME)
                endTime = LocalDateTime.parse(option.planEndTime, DateTimeFormatter.ISO_DATE_TIME)
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = type.image), contentDescription = "")
                    }
                    Text(text = type.title, style = textStyle.copy(color = TextBlack))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable {

                    }
                ) {
                    TodoSwitch(
                        checked = isExpand,
                        onCheckedChange = {
                            isExpand = !isExpand
                            if (!isExpand) {

                                startTime = LocalDate.now().atTime(0, 0, 0)
                                endTime = LocalDate.now().atTime(1, 0, 0)
                                onChangedPlanOption(
                                    option.copy(
                                        planStartTime = formatLocalDateTime(startTime),
                                        planEndTime = formatLocalDateTime(endTime)
                                    )
                                )
                            }
                        },
                    )
                }
            }
            if (isExpand) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "시작 시간", style = textStyle)

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = startTime.format(DateTimeFormatter.ofPattern("a hh:mm")),
                                modifier = Modifier.clickable {
                                    isShowStartTimePicker = true
                                },
                                style = textStyle.copy(color = Green12)
                            )
                            DropdownMenu(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(10.dp),
                                expanded = isShowStartTimePicker,
                                onDismissRequest = { isShowStartTimePicker = false }
                            ) {
                                WheelTimePicker(
                                    selectorProperties = WheelPickerDefaults.selectorProperties(
                                        color = SelectPlanTagColor,
                                        border = BorderStroke(1.dp, CalendarSelectedDateColor)
                                    ),
                                    timeFormat = TimeFormat.AM_PM
                                ) { suspendTime ->
                                    startTime = startTime.toLocalDate().atTime(suspendTime)
                                    onChangedPlanOption(
                                        option.copy(planStartTime = formatLocalDateTime(startTime))
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "종료 시간", style = textStyle)

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = endTime.format(DateTimeFormatter.ofPattern("a hh:mm")),
                                modifier = Modifier.clickable {
                                    isShowEndTimePicker = true
                                },
                                style = textStyle.copy(color = Green12)
                            )
                            DropdownMenu(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(10.dp),
                                expanded = isShowEndTimePicker,
                                onDismissRequest = { isShowEndTimePicker = false }
                            ) {
                                WheelTimePicker(
                                    selectorProperties = WheelPickerDefaults.selectorProperties(
                                        color = SelectPlanTagColor,
                                        border = BorderStroke(1.dp, CalendarSelectedDateColor)
                                    ),
                                    timeFormat = TimeFormat.AM_PM
                                ) { suspendTime ->
                                    endTime = endTime.toLocalDate().atTime(suspendTime)
                                    onChangedPlanOption(
                                        option.copy(planEndTime = formatLocalDateTime(endTime))
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun OptionRepeat(
        type: PlanOptionType = PlanOptionType.REPEAT,
        option: PlanOptionRequest = PlanOptionRequest(),
        onChangedPlanOption: (PlanOptionRequest) -> Unit = {},
    ) {
        var isExpand by remember { mutableStateOf(false) }

        var startDate by remember {
            mutableStateOf(LocalDate.now())
        }
        var endDate by remember {
            mutableStateOf(LocalDate.now().plusDays(1))
        }

        var selectDays by remember {
            mutableStateOf(setOf<RepeatDayType>())
        }

        var isShowStartDatePicker by remember { mutableStateOf(false) }
        var isShowEndDateTimePicker by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            if (option.planRepeatStartDate != null && option.planRepeatEndDate != null && option.planDaysOfWeek != null) {
                startDate =
                    LocalDateTime.parse(option.planRepeatStartDate, DateTimeFormatter.ISO_DATE_TIME)
                        .toLocalDate()
                endDate =
                    LocalDateTime.parse(option.planRepeatEndDate, DateTimeFormatter.ISO_DATE_TIME)
                        .toLocalDate()
                selectDays = option.planDaysOfWeek.map { RepeatDayType.valueOf(it) }.toSet()
                isExpand = true
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = type.image), contentDescription = "")
                    }
                    Text(text = type.title, style = textStyle.copy(color = TextBlack))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable {

                    }
                ) {
                    TodoSwitch(
                        checked = isExpand,
                        onCheckedChange = {
                            isExpand = !isExpand

                            if (isExpand) {
                                onChangedPlanOption(
                                    option.copy(
                                        planRepeatStartDate = formatLocalDate(startDate),
                                        planRepeatEndDate = formatLocalDate(endDate),
                                        planDaysOfWeek = selectDays.map { it.name }
                                    )
                                )
                            } else {
                                onChangedPlanOption(
                                    option.copy(
                                        planRepeatStartDate = null,
                                        planRepeatEndDate = null,
                                        planDaysOfWeek = null
                                    )
                                )
                            }
                        },
                    )
                }
            }
            if (isExpand) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "시작 날짜", style = textStyle)

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = startDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                                modifier = Modifier.clickable {
                                    isShowStartDatePicker = true
                                },
                                style = textStyle.copy(color = Green12)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "종료 날짜", style = textStyle)

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = endDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                                modifier = Modifier.clickable {
                                    isShowEndDateTimePicker = true
                                },
                                style = textStyle.copy(color = Green12)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "반복 요일", style = textStyle)

                        RepeatDayOfWeek(
                            selectDays = selectDays,
                            onChangedDays = {
                                selectDays = it
                                onChangedPlanOption(
                                    option.copy(planDaysOfWeek = selectDays.map { day -> day.name })
                                )
                            }
                        )
                    }
                }
            }
        }


        if (isShowStartDatePicker) {
            RepeatDatePicker(
                date = startDate,
                onChangedDate = { changedDate ->
                    startDate = changedDate
                    onChangedPlanOption(
                        option.copy(planRepeatStartDate = formatLocalDate(startDate))
                    )
                },
                onDismiss = {
                    isShowStartDatePicker = false
                }
            )
        }

        if (isShowEndDateTimePicker) {
            RepeatDatePicker(
                date = endDate,
                limitDate = startDate,
                onChangedDate = { changedDate ->
                    endDate = changedDate
                    onChangedPlanOption(
                        option.copy(planRepeatEndDate = formatLocalDate(endDate))
                    )
                },
                onDismiss = {
                    isShowEndDateTimePicker = false
                }
            )
        }
    }
}
