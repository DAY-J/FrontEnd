package com.dayj.dayj.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
fun ExitGroupDialog(
    groupName: String,
    onDismiss: () -> Unit,
    onClickExit: () -> Unit
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
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${groupName}",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )

                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = "그룹을 나가시겠어요?",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .drawBehind {
                            drawLine(
                                color = Black3A,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 1f),
                                strokeWidth = 1f
                            )
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDismiss() }
                            .drawBehind {
                                drawLine(
                                    color = Black3A,
                                    start = Offset(size.width - 1, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 1f
                                )
                            }
                            .padding(vertical = 12.dp),
                        text = "취소",
                        style = TextStyle(
                            color = Black3A,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onClickExit() }
                            .padding(vertical = 12.dp),
                        text = "나가기",
                        style = TextStyle(
                            color = RedFF00,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}