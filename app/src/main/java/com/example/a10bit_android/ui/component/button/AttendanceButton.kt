package com.example.a10bit_android.ui.component.button

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttendanceButton (
    buttonname: String,
    buttonColor: Color,
    onclick: () -> Unit
) {
    Button(
        onClick = onclick,
        modifier = Modifier
            .width(302.dp)
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        contentColor = Color.White )
    ) {
        Text(text = buttonname,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium)
    }
}