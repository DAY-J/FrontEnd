package com.dayj.dayj.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.dayj.dayj.ui.theme.GrayDDD


@Composable
fun CreateGroupDialog(
    onDismiss: () -> Unit,
    onCreateGroup: (groupName: String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dialogWidth = screenWidth * 0.9
    val groupName = remember { mutableStateOf("") }

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
                    text = "추가할 그룹명을 입력하세요.",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )

                BasicTextField(
                    modifier = Modifier
                        .padding(top = 18.dp, start = 6.dp, end = 6.dp)
                        .fillMaxWidth()
                        .border(1.dp, Color.Transparent)
                        .background(
                            color = GrayDDD,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    textStyle = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 5.dp)
                        ) {
                            innerTextField()
                        }
                    },
                    value = groupName.value,
                    onValueChange = {
                        groupName.value = it
                    }
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
                            .drawBehind {
                                drawLine(
                                    color = Black3A,
                                    start = Offset(size.width - 1, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 1f
                                )
                            }
                            .padding(vertical = 12.dp)
                            .clickable {
                                onDismiss()
                            },
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
                            .padding(vertical = 12.dp)
                            .clickable {
                                onCreateGroup(groupName.value)
                            },
                        text = "확인",
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
}