package com.example.a10bit_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.observe
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.a10bit_android.feature.attendance.AttendanceScreen
import com.example.a10bit_android.feature.attendance.AttendanceViewModel
import com.example.a10bit_android.feature.attendance.NfcHandler
import com.example.a10bit_android.feature.start.splash.SplashScreen
import com.example.a10bit_android.feature.start.login.LoginScreen
import com.example.a10bit_android.feature.start.signup.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    val tokenRepository = TokenRepository(applicationContext)
    private val viewModel: AttendanceViewModel by viewModels()
    private lateinit var nfcHandler: NfcHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        nfcHandler = NfcHandler(this) { tagId ->
            viewModel.onNfcTagScanned(tagId)
        }

        setContent {
            val navController = rememberNavController()
            MyAppNavHost(
                navController = navController
            )
        }

//        observeViewModel()
    }

//    private fun observeViewModel() {
//        viewModel.isNfcActive.observe(this) { active ->
//            if (active) nfcHandler.enable()
//            else nfcHandler.disable()
//        }
//
//        viewModel.attendanceStatus.collect { msg ->
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        nfcHandler.handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        nfcHandler.enable()
    }

    override fun onPause() {
        super.onPause()
        nfcHandler.disable()
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
            composable("signup") {
                SignUpScreen(navController)
            }
        }
        navigation(
         startDestination = "attendance",
            route = "main_graph"
        ) {
            composable("attendance") {
                AttendanceScreen(navController)
            }
        }
    }
}


