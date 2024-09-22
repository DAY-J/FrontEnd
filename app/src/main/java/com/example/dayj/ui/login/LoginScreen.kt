package com.example.dayj.ui.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dayj.R
import com.example.dayj.ui.theme.Black3A
import com.example.dayj.util.BuildConfigFieldUtils
import com.example.dayj.util.collectInLaunched
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navToHome: () -> Unit,
    navToRegister: (email: String) -> Unit
) {
    val idToken = BuildConfigFieldUtils.getGoogleClientFieldString()
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(idToken)
        .build()
    val googleClient = GoogleSignIn.getClient(context, gso)

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                viewModel.login(intent)
            } else {
                Toast.makeText(context, "구글 로그인 실패!", Toast.LENGTH_SHORT).show()
            }
        }

    viewModel.loginViewEffect.collectInLaunched { effect ->
        when (effect) {
            LoginEffect.RouteToMain -> {
                navToHome()
            }

            is LoginEffect.RouteToRegister -> {
                navToRegister(effect.email)
            }

            is LoginEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF13451f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.dayj_icon),
            contentDescription = null,
            modifier = Modifier.size(230.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    googleClient.signOut()
                    startForResult.launch(googleClient.signInIntent)
                }
                .padding(horizontal = 20.dp)
                .background(Color(0xFFe9e9e9), RoundedCornerShape(14.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.free_icon_google_2991148),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = 20.dp)
                    .align(Alignment.CenterStart),
            )
            Text(
                text = "구글로 시작하기",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black3A,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}