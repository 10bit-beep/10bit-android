package com.example.a10bit_android.feature.start.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.theme.AuthBackground

@Composable
fun SignUpScreen (
    navController: NavHostController
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(AuthBackground)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Image(
                painter = painterResource(R.drawable.mint_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(114.33.dp)
                    .height(104.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier .height(52.dp))



        }
    }
}