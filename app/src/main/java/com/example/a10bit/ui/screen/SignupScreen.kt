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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a10bit.R
import com.example.a10bit.ui.theme.Maincolor

@Composable
fun SignupScreen(navController: NavController) {

    var id by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var studentId by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.beeplogo_icon),
                    contentDescription = "logo",
                    tint = Maincolor,
                    modifier = Modifier
                        .width(114.55.dp)
                        .height(104.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                SignupTextField(label = "아이디", placeholder = "아이디를 입력하세요", value = id, onValueChange = { id = it })
                SignupTextField(label = "비밀번호", placeholder = "비밀번호를 입력하세요", value = password, onValueChange = { password = it })
                SignupTextField(label = "학번", placeholder = "학번을 입력하세요", value = studentId, onValueChange = { studentId = it })
                SignupTextField(label = "이메일", placeholder = "이메일을 입력하세요", value = email, onValueChange = { email = it })

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { /* 회원가입 로직 구현 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Maincolor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("회원가입", fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Text("이미 계정이 있으신가요?  ", color = Color.Gray)
                    ClickableText(
                        text = AnnotatedString("로그인"),
                        onClick = { navController.popBackStack() },
                        style = TextStyle(color = Maincolor, fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Copyright 2023. Team 8bit All rights reserved.",
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

@Composable
private fun SignupTextField(label: String, placeholder: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(17.dp),
            singleLine = true,
        )
    }
}
