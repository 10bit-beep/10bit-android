package com.example.a10bit.ui.screen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a10bit.R
import com.example.a10bit.ui.server.LoginUiState
import com.example.a10bit.ui.server.LoginViewModel
import com.example.a10bit.ui.theme.Maincolor
import com.example.a10bit.ui.theme.Redcolor

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    var publicId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val loginState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = loginState is LoginUiState.Loading
    val isLoginEnabled = publicId.isNotEmpty() && password.isNotEmpty() && !isLoading
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is LoginUiState.Success -> {
                Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
            is LoginUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                loginViewModel.resetState()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.beeplogo_icon),
                contentDescription = "logo", tint = Maincolor,
                modifier = Modifier.width(114.55.dp).height(104.dp)
            )
            Spacer(modifier = Modifier.height(87.dp))

            OutlinedTextField(
                value = publicId,
                onValueChange = { publicId = it },
                modifier = Modifier.fillMaxWidth().height(59.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                placeholder = { Text("아이디를 입력하세요") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().height(59.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                placeholder = { Text("비밀번호를 입력하세요") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painterResource(if (passwordVisible) R.drawable.openeye_icon else R.drawable.closeeye_icon),
                            "toggle password visibility",
                            modifier = Modifier
                                .width(22.dp)
                                .height(19.8.dp))
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { loginViewModel.login(publicId, password) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Maincolor),
                shape = RoundedCornerShape(8.dp),
                enabled = isLoginEnabled
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("로그인", fontSize = 18.sp, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                ClickableText(AnnotatedString("아이디찾기"),
                    onClick = {}, style = TextStyle(color = Color.Gray))

                Spacer(Modifier.width(8.dp));
                Text("|", color = Color.LightGray);
                Spacer(Modifier.width(8.dp))

                ClickableText(AnnotatedString("비밀번호 찾기"),
                    onClick = {}, style = TextStyle(color = Color.Gray))

                Spacer(Modifier.width(8.dp));
                Text("|", color = Color.LightGray);
                Spacer(Modifier.width(8.dp))

                ClickableText(AnnotatedString("회원가입"),
                    onClick = { navController.navigate("signup") }, style = TextStyle(color = Color.Gray))
            }
            Spacer(modifier = Modifier.height(117.dp))

            Text("Copyright 2023. Team 8bit All rights reserved.",
                fontSize = 12.sp, color = Color(224, 224, 224))
        }
    }
}