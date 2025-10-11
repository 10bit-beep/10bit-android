package com.example.a10bit_android.ui.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a10bit_android.ui.theme.errorColor

enum class LoginErrorType(val message: String) {
    NONE(""),
    EMPTY("값을 입력해주세요."),
    MISMATCH("아이디와 비밀번호를 확인해주세요.")
}

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorType: LoginErrorType = LoginErrorType.NONE,
    placeholderColor: Color = Color.Gray,
    ) {
    Column (modifier = Modifier
        .width(314.dp)
    ) {
        Spacer(modifier = Modifier.height(7.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = errorColor,
                errorTextColor = errorColor
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = placeholderColor,
                    fontSize = 12.sp
                )
            },
            isError = errorType != LoginErrorType.NONE,
            modifier = Modifier .fillMaxWidth()
        )
        if (errorType != LoginErrorType.NONE) {
//            Spacer(modifier = Modifier.height(20.dp))
//            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = errorType.message,
                color = errorColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}