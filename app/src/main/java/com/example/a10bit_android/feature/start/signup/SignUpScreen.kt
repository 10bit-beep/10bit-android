package com.example.a10bit_android.feature.start.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.component.button.SignUpButton
import com.example.a10bit_android.ui.component.textfield.SignUpErrorType
import com.example.a10bit_android.ui.component.textfield.SignUpTextField
import com.example.a10bit_android.ui.theme.AuthBackground
import com.example.a10bit_android.ui.theme.FooterTextColor

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

            SignUpTextField(
                "아이디",
                value = username,
                onValueChange = {
                    username = it
                    if (usernameErrorType != SignUpErrorType.NONE) usernameErrorType = SignUpErrorType.NONE
                },
                placeholder = "아이디를 입력해주세요.",
                errorType = usernameErrorType
            )

            Spacer(modifier = Modifier .height(20.dp))

            SignUpTextField(
                "비밀번호",
                value = password,
                onValueChange = {
                    password = it
                    if (passwordErrorType != SignUpErrorType.NONE) passwordErrorType = SignUpErrorType.NONE
                },
                placeholder = "비밀번호를 입력해주세요.",
                errorType = passwordErrorType,
            )

            Spacer(modifier = Modifier.height(20.dp))

            SignUpTextField(
                "학번",
                value = studentId,
                onValueChange = {
                    studentId = it
                    if (studentIdErrorType != SignUpErrorType.NONE) studentIdErrorType = SignUpErrorType.NONE
                },
                placeholder = "학번을 입력해주세요.",
                errorType = studentIdErrorType
            )

            Spacer(modifier = Modifier.height(20.dp))

            SignUpTextField(
                "이메일",
                value = email,
                onValueChange = {
                    email = it
                    if (emailErrorType != SignUpErrorType.NONE) emailErrorType = SignUpErrorType.NONE
                },
                placeholder = "이메일을 입력해주세요.",
                errorType = emailErrorType,
            )

            Spacer(modifier = Modifier.height(33.dp))

            SignUpButton(
                buttonname = "회원가입",
                onclick = {
                    // 아이디, 비밀번호 공백인지 검사
                    usernameErrorType =
                        if (username.isEmpty()) SignUpErrorType.EMPTY else SignUpErrorType.NONE
                    passwordErrorType =
                        if (password.isEmpty()) SignUpErrorType.EMPTY else SignUpErrorType.NONE

                    // 학번 4자리 숫자인지 검사
                    studentIdErrorType = when {
                        studentId.isEmpty() -> SignUpErrorType.EMPTY
                        !studentId.matches(Regex("\\d{4}")) -> SignUpErrorType.INVALID
                        else -> SignUpErrorType.NONE
                    }

                    // 이메일 형식 검사
                    emailErrorType = when {
                        email.isEmpty() -> SignUpErrorType.EMPTY
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                            .matches() -> SignUpErrorType.INVALID

                        else -> SignUpErrorType.NONE
                    }

                    // 회원가입 처리
                    if (usernameErrorType == SignUpErrorType.NONE &&
                        passwordErrorType == SignUpErrorType.NONE &&
                        studentIdErrorType == SignUpErrorType.NONE &&
                        emailErrorType == SignUpErrorType.NONE
                    ) {
                        // TODO: 실제 회원가입 API 호출
                        val isSignUpSuccess = true // 예시, 실제로는 ViewModel 호출

                        if (isSignUpSuccess) {
                            // 회원가입 성공시 화면 이동
                            navController.navigate("home")
                        } else {
                            // 회원가입 실패시 에러 표시
                            usernameErrorType = SignUpErrorType.MISMATCH_COLOR
                            passwordErrorType = SignUpErrorType.MISMATCH_COLOR
                            studentIdErrorType = SignUpErrorType.MISMATCH_COLOR
                            emailErrorType = SignUpErrorType.MISMATCH_TEXT
                        }
                    }
                }
            )

            Spacer(modifier = Modifier .height(20.dp))

            Row (modifier = Modifier
                .align(Alignment.CenterHorizontally)
            ){
                Text(text = "이미 계정이 있으신가요?",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Normal)

                Spacer(modifier = Modifier .width(20.dp))

                Text(text = "로그인",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable{
                        navController.navigate("login")
                        }
                    )
            }
        }

        Box (modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 47.dp)) {
            Text(text = "Copyright 2023. Team 8bit All rights reserved.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = FooterTextColor
            )
        }
    }
}