package com.example.dayj.ui.lounge.presentation.lounge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dayj.ui.theme.Gray6F
import com.example.dayj.ui.theme.Green12
import com.example.dayj.ui.theme.GreenC0D

@Composable
fun LoungeTag(
    selected: Boolean,
    tagText: String,
    onClickListener: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (selected) GreenC0D else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (selected) GreenC0D else Gray6F,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onClickListener()
            }
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 7.dp, horizontal = 12.dp),
            text = "#${tagText}",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = if(selected) Green12 else Gray6F
        )
    }
}