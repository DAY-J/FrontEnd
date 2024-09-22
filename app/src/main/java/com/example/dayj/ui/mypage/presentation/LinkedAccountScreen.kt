package com.example.dayj.ui.mypage.presentation

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dayj.R
import com.example.dayj.ui.theme.Background
import com.example.dayj.ui.theme.Black3A
import com.example.dayj.ui.theme.Gray9C9

@Composable
fun LinkedAccountScreen(
    onClickBack: () -> Unit,
) {
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

            Text(
                "연결된 계정",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Black3A
                )
            )

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
                Text(
                    "useremail@gmail.com",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Gray9C9
                    )
                )
            }
        }
    }
}