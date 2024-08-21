package com.dayj.dayj.ui.theme

import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


val textStyle = TextStyle(
    fontSize = 14.sp,
    lineHeight = 22.sp,
    letterSpacing = (-0.28).sp,
    color = TextColor,
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal
)


val switchColors
    @Composable get() = SwitchDefaults.colors(
        uncheckedBorderColor = GrayA6,
        uncheckedTrackColor = GrayA6,
        uncheckedIconColor = Color.White,
        uncheckedThumbColor = Color.White,
        checkedBorderColor = Green12,
        checkedTrackColor = Green12,
        checkedIconColor = Color.White,
        checkedThumbColor = Color.White
    )
