package com.example.dayj.ui.mypage.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayj.R
import com.example.dayj.ui.friends.domain.entity.UserEntity
import com.example.dayj.ui.theme.Background
import com.example.dayj.ui.theme.Black2A
import com.example.dayj.ui.theme.Black3A
import com.example.dayj.ui.theme.Gray9A
import com.example.dayj.ui.theme.GrayDDD
import com.example.dayj.ui.theme.Green12
import com.example.dayj.ui.theme.RedFF00

@Composable
fun ChangeNickNameScreen(
    userEntity: UserEntity?,
    onClickBack: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.updateUserEntity(userEntity)
    }
    val context = LocalContext.current

    val nickName = viewModel.nickName.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val completable = errorMessage.isEmpty() && nickName.isNotEmpty()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 24.dp, end = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        onClickBack()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.left_arrow_black),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(end = 4.dp))
                Text(
                    text = "마이 페이지",
                    style = TextStyle(
                        color = Black3A,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.padding(top = 40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "닉네임*",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Black3A
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                if(errorMessage.isNotEmpty()) {
                    Text(
                        errorMessage,
                        style = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = RedFF00
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.padding(top = 4.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterStart),
                    value = nickName,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Black2A,
                        textAlign = TextAlign.Start,
                    ),
                    onValueChange = {
                        viewModel.updateNickName(it)
                        viewModel.checkNickNameLength(it)
                    }
                )
            }

            Spacer(modifier = Modifier.padding(top = 4.dp))

            Text(
                "중복불가 / 15자까지 가능",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = Gray9A
                )
            )

            Spacer(modifier = Modifier.padding(top = 60.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if(completable) Green12 else GrayDDD,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clip(shape = RoundedCornerShape(14.dp))
                    .padding(vertical = 16.dp)
                    .clickable {
                        if(completable)
                            viewModel.completeModification {
                            Toast.makeText(context, "닉네임 수정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                            onClickBack()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "저장",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = if(completable) Color.White else Gray9A
                    )
                )
            }
        }
    }
}