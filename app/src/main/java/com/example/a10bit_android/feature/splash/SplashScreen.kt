package com.example.a10bit_android.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a10bit_android.ui.theme.mainColor
import kotlinx.coroutines.delay
import com.example.a10bit_android.R
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(mainColor)
    ){
        Image( painter = painterResource(R.drawable.white_logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.Center)
                .width(138.41.dp)
                .height(125.9.dp)
        )
    }
}