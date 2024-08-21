package com.dayj.dayj.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.GrayDDD
import com.dayj.dayj.ui.theme.RedFF00

@Composable
fun GroupSettingDialog(
    expanded: Boolean,
    onClickInvite: () -> Unit,
    onClickExit: () -> Unit,
    onDismiss: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dialogWidth = screenWidth * 0.3
    
    DropdownMenu(
        modifier = Modifier
            .background(color = Color.White)
            .width(dialogWidth.dp)
            .wrapContentHeight(),
        expanded = expanded,
        offset = DpOffset((screenWidth - dialogWidth - 70).dp, 0.dp),
        onDismissRequest = { onDismiss() },
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    modifier = Modifier
                        .padding(vertical = 7.dp),
                    text = "친구초대",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                ) },
            onClick = {
                onClickInvite()
            }
        )

        DropdownMenuItem(
            text = {
                Text(
                    modifier = Modifier
                        .padding(vertical = 7.dp),
                    text = "그룹 나가기",
                    style = TextStyle(
                        color = RedFF00,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                ) },
            onClick = {
                onClickExit()
            }
        )
    }

//
//    Dialog(
//        onDismissRequest = {
//            onDismiss()
//        }
//    ) {
//        Surface(
//            modifier = Modifier
//                .width(dialogWidth.dp)
//                .wrapContentHeight()
//                .offset(x = 50.dp, y = 10.dp),
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 20.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//                Text(
//                    modifier = Modifier
//                        .padding(vertical = 7.dp),
//                    text = "친구초대",
//                    style = TextStyle(
//                        color = Black3A,
//                        fontWeight = FontWeight.Medium,
//                        fontSize = 14.sp,
//                        textAlign = TextAlign.Center
//                    )
//                )
//
//                Box(
//                    modifier = Modifier
//                        .height(1.dp)
//                        .drawBehind {
//                            drawLine(
//                                color = Black3A,
//                                start = Offset(0f, size.height - 1),
//                                end = Offset(size.width, size.height),
//                                strokeWidth = 1f
//                            )
//                        }
//                )
//            }
//        }
//    }
}