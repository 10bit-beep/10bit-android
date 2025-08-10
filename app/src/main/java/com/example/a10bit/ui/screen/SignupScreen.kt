package com.example.a10bit.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a10bit.R
import com.example.a10bit.ui.server.SignupUiState
import com.example.a10bit.ui.server.SignupViewModel
import com.example.a10bit.ui.theme.Maincolor

@Composable
fun SignupScreen(
    navController: NavController,
    signupViewModel: SignupViewModel = viewModel()
) {
    val signupState by signupViewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = signupState is SignupUiState.Loading
    val context = LocalContext.current

    val isFormValid = signupViewModel.studentId.value.isNotEmpty() &&
            signupViewModel.publicId.value.isNotEmpty() &&
            signupViewModel.password.value.isNotEmpty() &&
            signupViewModel.email.value.isNotEmpty()

    LaunchedEffect(signupState) {
        when (val state = signupState) {
            is SignupUiState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            is SignupUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                signupViewModel.resetStateToIdle()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(120.dp))

        Icon(
            painter = painterResource(id = R.drawable.beeplogo_icon),
            contentDescription = "logo", tint = Maincolor,
            modifier = Modifier.size(width = 114.55.dp, height = 104.dp)
        )
        Spacer(Modifier.height(40.dp))

        SignupTextField(label = "아이디", value = signupViewModel.publicId.value, onValueChange = { signupViewModel.publicId.value = it }, placeholder = "아이디를 입력하세요", enabled = !isLoading)
        SignupTextField(label = "비밀번호", value = signupViewModel.password.value, onValueChange = { signupViewModel.password.value = it }, placeholder = "비밀번호를 입력하세요", isPassword = true, enabled = !isLoading)
        SignupTextField(label = "학번", value = signupViewModel.studentId.value, onValueChange = { signupViewModel.studentId.value = it }, placeholder = "학번을 입력하세요 (예: 2411)", keyboardType = KeyboardType.Number, enabled = !isLoading)
        SignupTextField(label = "이메일", value = signupViewModel.email.value, onValueChange = { signupViewModel.email.value = it }, placeholder = "이메일을 입력하세요", keyboardType = KeyboardType.Email, enabled = !isLoading)


        Spacer(Modifier.height(30.dp))

        Button(
            onClick = { signupViewModel.signup() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Maincolor),
            shape = RoundedCornerShape(8.dp),
            enabled = isFormValid && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
            } else {
                Text("회원가입", fontSize = 18.sp, color = Color.White)
            }
        }
        Spacer(Modifier.height(20.dp))

        Row {
            Text("이미 계정이 있으신가요? ", color = Color.Gray)
            ClickableText(
                text = AnnotatedString("로그인"),
                onClick = { if (!isLoading) navController.popBackStack() },
                style = TextStyle(color = Maincolor, fontWeight = FontWeight.Bold)
            )
        }
        Spacer(Modifier.height(117.dp))

        Text(
            "Copyright 2023. Team 8bit All rights reserved.",
            fontSize = 12.sp, color = Color(224, 224, 224),
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignupTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = label, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(55.dp),
            placeholder = { Text(placeholder, color = Color.Gray) },
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Maincolor,
                unfocusedBorderColor = Color.LightGray,
            )
        )
    }
}