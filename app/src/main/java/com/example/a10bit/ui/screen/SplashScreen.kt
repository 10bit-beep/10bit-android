package com.example.a10bit.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.a10bit.ui.theme.Maincolor
import com.example.a10bit.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }
    Box ( modifier = Modifier
        .fillMaxSize()
        .background(Maincolor)
    ){
        Icon(painter = painterResource(id = R.drawable.beeplogo_icon),
            contentDescription = "logo",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}