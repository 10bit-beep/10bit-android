package com.example.a10bit.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a10bit.ui.theme.Maincolor
import com.example.a10bit.ui.theme.Redcolor
import com.example.a10bit.R

@Composable
fun LoginScreen(navController: NavController) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    val isLoginEnabled = username.isNotEmpty() && password.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
            Column(
                modifier = Modifier
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(painter = painterResource(id = R.drawable.beeplogo_icon),
                    contentDescription = "logo",
                    tint = Maincolor,
                    modifier = Modifier
                        .width(114.55.dp)
                        .height(104.dp)
                )

                Spacer(modifier = Modifier.height(87.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(59.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    placeholder = { Text("아이디를 입력하세요") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(59.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    placeholder = { Text("비밀번호를 입력하세요") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val iconId = if (passwordVisible) R.drawable.openeye_icon else R.drawable.closeeye_icon
                        val description = if (passwordVisible) "비밀번호 숨기기" else "비밀번호 보이기"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(id = iconId),
                                contentDescription = description,
                                modifier = Modifier
                                    .width(22.dp)
                                    .height(19.8.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (showError) {
                    Text(
                        text = "비밀번호가 잘못되었습니다.",
                        color = Redcolor,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .align(Alignment.End)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { /* 로그인 로직 구현해야함 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Maincolor),
                    shape = RoundedCornerShape(8.dp),
                    enabled = isLoginEnabled
                ) {
                    Text("로그인", fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ClickableText(text = AnnotatedString("아이디찾기"),
                        onClick = {}, style = TextStyle(color = Color.Gray))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("|", color = Color.LightGray)

                    Spacer(modifier = Modifier.width(8.dp))

                    ClickableText(text = AnnotatedString("비밀번호 찾기"),
                        onClick = {}, style = TextStyle(color = Color.Gray))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("|", color = Color.LightGray)

                    Spacer(modifier = Modifier.width(8.dp))

                    ClickableText(
                        text = AnnotatedString("회원가입"),
                        onClick = { navController.navigate("signup") }, // "signup" 라우트로 이동
                        style = TextStyle(color = Color.Gray)
                    )
                }

                Spacer(modifier = Modifier.height(117.dp))

                Text(
                    text = "Copyright 2023. Team 8bit All rights reserved.",
                    fontSize = 12.sp,
                    color = Color(224,224,224) )
            }
        }
    }