package com.example.a10bit_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.a10bit_android.feature.start.splash.SplashScreen
import com.example.a10bit_android.feature.start.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyAppNavHost(
                navController = navController
            )
        }
    }
}

@Composable
fun MyAppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "auth_graph") {
        navigation(
            startDestination = "splash",
            route = "auth_graph"
        ) {
            composable("splash") {
                SplashScreen(navController)
            }
            composable("login") {
                LoginScreen(navController)
            }
        }
    }
}


