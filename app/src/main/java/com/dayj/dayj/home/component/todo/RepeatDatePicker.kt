package com.dayj.dayj.home.component.todo

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.dayj.dayj.ui.theme.CalendarSelectedDateColor
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatDatePicker(
    date: LocalDate,
    limitDate: LocalDate = LocalDate.now(),
    onChangedDate: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {

    val dateState = rememberDatePickerState(
        yearRange = LocalDate.now().year..LocalDate.now().year,
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = date.atStartOfDay().toInstant(ZoneOffset.UTC)
            .toEpochMilli(),
        selectableDates = PastOrPresentSelectableDates.also {
            it.isSelectableDate(System.currentTimeMillis())
            it.isSelectableYear(LocalDate.now().year)
        }
    )

    val context = LocalContext.current

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(containerColor = Color.White),
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = CalendarSelectedDateColor
                ),
                onClick = {
                    if (dateState.selectedDateMillis == null) {
                        Toast.makeText(context, "날짜를 선택해 주세요", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val selectedDate = Instant.ofEpochMilli(
                            dateState.selectedDateMillis ?: 0L
                        ).atZone(ZoneId.systemDefault()).toLocalDate()
                        val between = ChronoUnit.DAYS.between(limitDate, selectedDate)
                        if (between >= 0) {
                            onChangedDate(selectedDate)
                            onDismiss()
                        } else {
                            Toast.makeText(context, "날짜를 다시 선택해 주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CalendarSelectedDateColor
                ),
            ) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = CalendarSelectedDateColor,
                selectedDayContentColor = Color.White,
                todayDateBorderColor = CalendarSelectedDateColor,
            )
        )
    }
}