package com.dayj.dayj.home.component.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayj.dayj.ui.theme.GrayDDD
import com.dayj.dayj.ui.theme.textStyle

@Composable
fun RecommendItem(
    modifier: Modifier = Modifier,
    item: String,
    isShowDivider: Boolean = true,
    onClick: (String) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(40.dp).then(modifier),
    ) {
        Text(
            text = item,
            style = textStyle,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(10.dp)
                .clickable { onClick(item) }
        )
        if(isShowDivider){
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = GrayDDD
            )
        }
    }
}