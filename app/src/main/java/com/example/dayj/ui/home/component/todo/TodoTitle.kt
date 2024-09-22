package com.example.dayj.ui.home.component.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dayj.ui.theme.Green12
import com.example.dayj.ui.theme.textStyle

@Composable
fun TodoTitle(
    title: String,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() }
            )
            Text(text = title, style = textStyle.copy(fontWeight = FontWeight.Bold))
        }

        Text(
            text = "완료",
            style = textStyle.copy(fontWeight = FontWeight.Bold, color = Green12),
            modifier = Modifier.clickable {
                onSave()
            }
        )
    }
}