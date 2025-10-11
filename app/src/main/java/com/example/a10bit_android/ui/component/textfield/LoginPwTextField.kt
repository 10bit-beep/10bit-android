package com.example.a10bit_android.ui.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a10bit_android.ui.theme.errorColor
import com.example.a10bit_android.R
//import com.example.a10bit_android.ui.component.textfield.LoginErrorType

@Composable
fun LoginPwTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorType: LoginErrorType = LoginErrorType.NONE,
    placeholderColor: Color = Color.Gray,
    ) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painterResource(if (passwordVisible) R.drawable.openeye_icon else R.drawable.closeeye_icon),
                        "toggle password visibility",
                        modifier = Modifier
                            .width(22.dp)
                            .height(19.8.dp)
                    )
                }
            },
            isError = errorType != LoginErrorType.NONE,
            modifier = Modifier .fillMaxWidth()
        )
        if (errorType != LoginErrorType.NONE && errorType != LoginErrorType.MISMATCH_COLOR) {
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