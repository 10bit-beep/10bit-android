package com.example.a10bit

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.a10bit.ui.screen.HomeScreen
import com.example.a10bit.ui.screen.LoginScreen
import com.example.a10bit.ui.screen.ProfileScreen
import com.example.a10bit.ui.screen.SignupScreen
import com.example.a10bit.ui.screen.SplashScreen
import com.example.a10bit.ui.server.HomeViewModel
import com.example.a10bit.ui.server.ProfileViewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private var nfcAdapter: NfcAdapter? = null
    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyAppNavHost(
                navController = navController,
                homeViewModel = homeViewModel,
                profileViewModel = profileViewModel
            )
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tagIdBytes = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)
            val nfcTagValue = tagIdBytes?.joinToString("") { "%02x".format(it) } ?: ""
            if (nfcTagValue.isNotEmpty()) {
                homeViewModel.processNfcTag(nfcTagValue)
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel
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
                SignupScreen(navController)
            }
        }

        navigation(
            startDestination = "home",
            route = "main_graph"
        ) {
            composable("home") {
                HomeScreen(navController = navController, homeViewModel = homeViewModel)
            }
            composable("profile") {
                ProfileScreen(navController = navController, profileViewModel = profileViewModel)
            }
        }
    }
}