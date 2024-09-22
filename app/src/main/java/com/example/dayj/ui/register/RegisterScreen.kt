package com.example.dayj.ui.register

import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dayj.ui.theme.textStyle
import com.example.dayj.util.collectInLaunched
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navToLogin: () -> Unit,
    navToHome: () -> Unit
) {
    val state = viewModel.registerViewState.collectAsState().value

    val context = LocalContext.current

    viewModel.registerViewEffect.collectInLaunched { effect ->
        when (effect) {
            RegisterEffect.RouteHome -> {
                navToHome()
            }

            is RegisterEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .background(Color(0xFFefeeed))
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier.clickable(onClick = navToLogin)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "회원가입", style = textStyle.copy(fontSize = 18.sp))
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "닉네임*", style = textStyle.copy(fontWeight = FontWeight.Bold))
            Text(
                text = state.nickNameErrorType.toMessage(),
                style = textStyle.copy(fontSize = 12.sp, color = Color.Red)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = state.nickName, onValueChange = viewModel::inputNickName,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedTextColor = Color.Black,
            ),
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "중복불가 / 한글 15자, 영문 30자까지 가능",
            modifier = Modifier.fillMaxWidth(),
            style = textStyle.copy(
                fontSize = 10.sp,
                color = Color(0xFF7f7f7e),
                textAlign = TextAlign.Start
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = viewModel::register,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
        ) {
            Text(
                text = "다음",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFe9e9e9)
            )
        }
    }
}
