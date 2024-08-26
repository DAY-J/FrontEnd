package com.dayj.dayj.lounge.presentation.lounge

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.RedFF00

@Composable
fun PostSettingDialog(
    onDismiss: () -> Unit,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dialogWidth = screenWidth * 0.9

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .width(dialogWidth.dp)
                .height(100.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .clickable { onClickDelete() }
                        .padding(vertical = 12.dp),
                    text = "삭제",
                    style = TextStyle(
                        color = RedFF00,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Black3A)
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onClickEdit() }
                        .padding(vertical = 12.dp),
                    text = "수정",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}