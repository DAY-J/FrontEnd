package com.dayj.dayj.mypage.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayj.dayj.R
import com.dayj.dayj.friends.domain.entity.UserEntity
import com.dayj.dayj.ui.theme.Background
import com.dayj.dayj.ui.theme.Black3A
import com.dayj.dayj.ui.theme.GrayDDD
import com.dayj.dayj.ui.theme.Green12
import com.dayj.dayj.ui.theme.RedFF00
import com.dayj.dayj.utils.Extensions.noRippleClickable

@Composable
fun MyPageScreen(
    navToChangeNickName: (UserEntity) -> Unit,
    navToLinkedAccount: () -> Unit
) {
    var receivePushMessage by remember { mutableStateOf(true) }
    var userEntity  = UserEntity(userName = "호돌이", userId = 123)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Text(
                    text = "마이 페이지",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )

                Row(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(70.dp),
                        painter = painterResource(id = R.drawable.account_circle_selected),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.padding(end = 15.dp))

                    Text(
                        text = "호돌이",
                        style = TextStyle(
                            color = Black3A,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.padding(end = 10.dp))

                    Image(
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navToChangeNickName(userEntity)
                            },
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.padding(top = 27.dp))

                Text(
                    text = "계정 관리",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    Column {
                        MyPageButton(buttonText = "연결된 계정") {
                            navToLinkedAccount()
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(top = 15.dp))

                Text(
                    text = "알림",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.padding(top = 15.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 19.dp),
                                text = "알림 받기",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start
                            )

                            Spacer(modifier = Modifier.weight(1f))


                            Switch(
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(44.dp),
                                checked = receivePushMessage,
                                onCheckedChange = {
                                    receivePushMessage = it
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    uncheckedBorderColor = Color.White,
                                    checkedTrackColor = Green12,
                                    uncheckedTrackColor = GrayDDD
                                )
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.padding(top = 15.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    Column {
                        MyPageButton(buttonText = "로그아웃") {

                        }

                        MyPageButtonDivider()

                        MyPageButton(buttonText = "회원탈퇴", textColor = RedFF00) {

                        }
                    }
                }

                Spacer(modifier = Modifier.padding(top = 40.dp))

            }
        }
    }
}

@Composable
fun MyPageButtonDivider() {
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = GrayDDD)
    )
}


@Composable
fun MyPageButton(
    buttonText: String,
    textColor: Color = Black3A,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 19.dp),
            text = buttonText,
            style = TextStyle(
                color = textColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Start
        )
    }

}
