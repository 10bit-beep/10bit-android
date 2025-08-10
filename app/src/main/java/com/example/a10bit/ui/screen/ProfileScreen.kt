package com.example.a10bit.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a10bit.R
import com.example.a10bit.ui.server.ProfileEvent
import com.example.a10bit.ui.server.ProfileResponse
import com.example.a10bit.ui.server.ProfileUiState
import com.example.a10bit.ui.server.ProfileViewModel
import com.example.a10bit.ui.theme.Maincolor
import com.example.a10bit.ui.theme.Redcolor
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    // 로그아웃 이벤트 처리
    LaunchedEffect(Unit) {
        profileViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProfileEvent.LogoutCompleted -> {
                    // 로그인 화면으로 이동하고, 이전 화면 스택을 모두 제거
                    navController.navigate("auth_graph") {
                        popUpTo("main_graph") { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(Color(0xFFF0F2F5)),
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "프로필",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 24.dp)
                    .align(Alignment.Start)
            )

            if (uiState is ProfileUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                var profileData = ProfileResponse(
                    name = "박서영",
                    studentId = "1학년 2반 2번",
                    email = "kyukami7@dgsw.hs.kr",
                    room = "프로젝트 5 (B1ND)"
                )
                if (uiState is ProfileUiState.Success) {
                    profileData = (uiState as ProfileUiState.Success).profileData
                }

                ProfileHeader(name = profileData.name)
                Spacer(Modifier.height(24.dp))
                ProfileInfoCard(profileData = profileData)
                Spacer(Modifier.height(16.dp))
                ProfileSettingsCard()
                Spacer(Modifier.height(16.dp))
                LogoutCard(onLogoutClick = { profileViewModel.logout() })
            }
        }
    }
}

@Composable
private fun ProfileHeader(name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(90.dp).clip(CircleShape)
        )
        Spacer(Modifier.height(16.dp))
        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ProfileInfoCard(profileData: ProfileResponse) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
            ProfileInfoRow(label = "학번", value = profileData.studentId)
            ProfileInfoRow(label = "이메일", value = profileData.email)
            ProfileInfoRow(label = "실", value = profileData.room)
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.Gray, modifier = Modifier.width(80.dp))
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ProfileSettingsCard() {
    var isChecked by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "백그라운드 nfc 사용", color = Maincolor, fontWeight = FontWeight.Bold)
            Switch(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Maincolor,
                    checkedTrackColor = Maincolor.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
private fun LogoutCard(onLogoutClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onLogoutClick),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Box(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
            Text(text = "로그아웃", color = Redcolor, fontWeight = FontWeight.Bold)
        }
    }
}