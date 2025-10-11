package com.example.a10bit_android.ui.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a10bit_android.ui.theme.errorColor

enum class SignUpErrorType(val message: String) {
    NONE(""),
    EMPTY("값을 입력해주세요."),
    INVALID("올바른 형식이 아닙니다."),
    MISMATCH_COLOR(""),
    MISMATCH_TEXT("이미 존재하는 계정이거나 잘못된 입력이 있습니다.")
}

@Composable
fun SignUpTextField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorType: SignUpErrorType = SignUpErrorType.NONE,
    placeholderColor: Color = Color.Gray,
) {
    Column {
        Row (modifier = Modifier
            .align(Alignment.Start)
        ){
            Spacer(modifier = Modifier .width(14.dp))

            Text(text = title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier .height(2.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(17.dp),
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = errorColor,
                errorTextColor = errorColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = placeholderColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            isError = errorType != SignUpErrorType.NONE,
            modifier = Modifier
                .width(323.dp)
                .height(52.dp)
        )
        if (errorType != SignUpErrorType.NONE && errorType != SignUpErrorType.MISMATCH_COLOR) {
            Text(
                text = errorType.message,
                color = errorColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}