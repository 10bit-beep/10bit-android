package com.example.a10bit_android.feature.start.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.component.textfield.SignUpErrorType
import com.example.a10bit_android.ui.component.textfield.SignUpTextField
import com.example.a10bit_android.ui.theme.AuthBackground

@Composable
fun SignUpScreen (
    navController: NavHostController
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(AuthBackground)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Image(
                painter = painterResource(R.drawable.mint_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(114.33.dp)
                    .height(104.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier .height(52.dp))

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var studentId by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }

            var usernameErrorType by remember { mutableStateOf(SignUpErrorType.NONE) }
            var passwordErrorType by remember { mutableStateOf(SignUpErrorType.NONE) }
            var studentIdErrorType by remember { mutableStateOf(SignUpErrorType.NONE) }
            var emailErrorType by remember { mutableStateOf(SignUpErrorType.NONE) }

            SignUpTextField( "아이디",
                value = username,
                onValueChange = { username = it },
                placeholder = "아이디를 입력해주세요.",
                errorType = usernameErrorType
            )

            Spacer(modifier = Modifier .height(20.dp))

            SignUpTextField(
                "비밀번호",
                value = password,
                onValueChange = { password = it },
                placeholder = "비밀번호를 입력해주세요.",
                errorType = passwordErrorType,
            )

            Spacer(modifier = Modifier.height(20.dp))

            SignUpTextField(
                "학번",
                value = studentId,
                onValueChange = { studentId = it },
                placeholder = "학번을 입력해주세요.",
                errorType = studentIdErrorType
            )

            Spacer(modifier = Modifier.height(20.dp))

            SignUpTextField(
                "이메일",
                value = email,
                onValueChange = { email = it },
                placeholder = "이메일을 입력해주세요.",
                errorType = emailErrorType,
            )



        }
    }
}