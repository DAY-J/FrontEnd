package com.example.dayj.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dayj.ui.theme.switchColors

@Composable
fun TodoSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = {
        Box(
            modifier = Modifier
                .background(Color.White, CircleShape)
                .size(20.dp)
        )
    },
    enabled: Boolean = true,
    colors: SwitchColors = switchColors,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Switch(
        checked, onCheckedChange, modifier, thumbContent, enabled, colors, interactionSource
    )
}