package com.dayj.dayj.statistics.component

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsDateDialog(
    selectedDate: LocalDate,
    onChangedDate: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val dateState = rememberDatePickerState(
        yearRange = 2023..2024,
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = selectedDate.atStartOfDay().toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (dateState.selectedDateMillis == null) {
                        Toast.makeText(context, "날짜를 선택해 주세요", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        onChangedDate(
                            Instant.ofEpochMilli(
                                dateState.selectedDateMillis ?: 0L
                            ).atZone(ZoneId.systemDefault()).toLocalDate()
                        )
                        onDismiss()

                    }
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = false
        )
    }
}