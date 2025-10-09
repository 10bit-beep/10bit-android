package com.example.a10bit_android.feature.login

import androidx.compose.foundation.Image
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.a10bit_android.ui.component.textfield.LoginTextField
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.component.button.LoginButton

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Box (
        modifier = Modifier
            .fillMaxSize()

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
            var isError by remember { mutableStateOf(false) }

            LoginTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "아이디",
                isError = isError
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "비밀번호",
                isError = isError,
                errortext = "아이디와 비밀번호를 확인해주세요."
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
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        loginViewModel.login( username, password )
                    } else {
                        isError = true
                    }
                }
            )

            Spacer(modifier = Modifier .height(19.dp))


            Box ( ){
                Row ( modifier = Modifier
//                    .fillMaxSize()
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
            .padding(47.dp)) {

        }
    }

}