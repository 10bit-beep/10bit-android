package com.example.a10bit.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.a10bit.R
import com.example.a10bit.ui.server.HomeUiEvent
import com.example.a10bit.ui.server.HomeViewModel
import com.example.a10bit.ui.theme.Maincolor
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val context = LocalContext.current
    val isCheckedIn by homeViewModel.isCheckedIn.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        homeViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.background(Color(0xFFF0F2F5)), // <-- 1. 전체 배경색 지정
        topBar = {
            TopAppBar( modifier = Modifier
                .padding(top = 30.dp, start = 15.dp, end = 15.dp),
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.beeplogo_icon),
                        contentDescription = "App Logo",
                        tint = Maincolor,
                        modifier = Modifier.size(width = 52.87.dp, height = 48.dp) // 로고 사이즈 조정
                    )
                },
                actions = {
                        Icon(Icons.Outlined.Info, contentDescription = "정보", tint = Maincolor,
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterVertically) )
                },
                // [수정] TopAppBar의 배경을 투명하게 설정
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent // <-- 2. TopAppBar 배경 투명 처리
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        // [수정] containerColor는 여기서 제거하거나, 동일한 색상으로 두어도 괜찮습니다.
        // 일관성을 위해 modifier에서 관리하는 것이 더 좋습니다.
        containerColor = Color.Transparent // <-- 3. 컨텐츠 영역도 투명하게 하여 modifier의 배경색을 따름
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // TopBar와 BottomBar 영역을 제외한 안전한 영역 확보
                .padding(horizontal = 24.dp), // 좌우 패딩
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp)) // TopAppBar와 카드 사이의 간격
            AttendanceCard(isCheckedIn = isCheckedIn)
        }
    }
}

// AttendanceCard와 BottomNavigationBar Composable은 이전과 동일하게 유지합니다.
@Composable
fun AttendanceCard(isCheckedIn: Boolean) {
    val buttonColor = if (isCheckedIn) Color(0xFFE57373) else Maincolor
    val buttonText = if (isCheckedIn) "퇴실하기" else "출석하기"
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "출석 체크",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.phone_image),
                contentDescription = "NFC 스캔 안내",
                modifier = Modifier
                    .width(238.dp)
                    .height(258.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    Toast.makeText(context, "NFC 태그를 휴대폰 뒷면에 스캔해주세요.", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = buttonText, fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        "home" to Icons.Outlined.Home,
        "profile" to Icons.Outlined.Person
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { (route, icon) ->
            val label = when (route) {
                "home" -> "홈"
                "profile" -> "프로필"
                else -> ""
            }
            val isSelected = currentRoute == route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(icon, contentDescription = label, tint = if (isSelected) Maincolor else Color.Gray) },
                label = { Text(label, color = if (isSelected) Maincolor else Color.Gray) }
            )
        }
    }
}