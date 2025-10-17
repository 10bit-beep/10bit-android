package com.example.a10bit_android.feature.start.login

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.a10bit_android.ui.component.textfield.LoginTextField
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.component.button.LoginButton
import com.example.a10bit_android.ui.component.textfield.LoginErrorType
import com.example.a10bit_android.ui.component.textfield.LoginPwTextField
import com.example.a10bit_android.ui.theme.AuthBackground
import com.example.a10bit_android.ui.theme.FooterTextColor


@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(AuthBackground)

    ){

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

            Spacer(modifier = Modifier .height(87.dp))

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var usernameErrorType by remember { mutableStateOf(LoginErrorType.NONE) }
            var passwordErrorType by remember { mutableStateOf(LoginErrorType.NONE) }

            LoginTextField(
                value = username,
                onValueChange = {
                    username = it
                    if (usernameErrorType != LoginErrorType.NONE) usernameErrorType = LoginErrorType.NONE
                },
                placeholder = "아이디",
                errorType = usernameErrorType
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginPwTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (passwordErrorType != LoginErrorType.NONE) passwordErrorType = LoginErrorType.NONE
                },
                placeholder = "비밀번호",
                errorType = passwordErrorType
            )

            Spacer(modifier = Modifier.height(20.dp))

//            if (isError) {
//                Text(
//                    text = "아이디와 비밀번호를 확인해주세요.",
//                    color = errorColor,
//                    fontSize = 16.sp
//                )
//            }

            Spacer(modifier = Modifier.height(20.dp))

            LoginButton(
                "로그인",
                onClick = {
                    usernameErrorType = when {
                        username.isEmpty() -> LoginErrorType.EMPTY
                        else -> LoginErrorType.NONE
                    }
                    passwordErrorType = when {
                        password.isEmpty() -> LoginErrorType.EMPTY
                        else -> LoginErrorType.NONE
                    }
                    if (usernameErrorType == LoginErrorType.NONE && passwordErrorType == LoginErrorType.NONE) {
                        loginViewModel.login(username, password)
                        if ( loginViewModel.loginSuccess.value == true ) {
                            navController.navigate("home")
                        } else {
                            usernameErrorType = LoginErrorType.MISMATCH_COLOR
                            passwordErrorType = LoginErrorType.MISMATCH_TEXT
                        }
                    }
                }
            )

            Spacer(modifier = Modifier .height(19.dp))


            Box {
                Row ( modifier = Modifier
                    .align(Alignment.Center)
                ){
                    Text(
                        text = "아이디 찾기",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier .width(25.dp))

                    Text(
                        text = "|",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier .width(25.dp))

                    Text(
                        text = "비밀번호 찾기",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier .width(25.dp))

                    Text(
                        text = "|",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier .width(25.dp))

                    Text(
                        text = "회원가입",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable{
                            navController.navigate("signup")
                        }
                    )
                }
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