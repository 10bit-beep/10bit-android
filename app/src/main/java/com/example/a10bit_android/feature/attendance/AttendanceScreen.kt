package com.example.a10bit_android.feature.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.a10bit_android.ui.theme.HomeBackground
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.component.button.AttendanceButton
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.a10bit_android.ui.component.navbar.HomeNavBar


@Composable
fun AttendanceScreen (
    navController: NavHostController
) {
    val viewModel: AttendanceViewModel = hiltViewModel()
    var isChecked by remember { mutableStateOf(false) }
    val status by viewModel.attendanceStatus.collectAsState()

    val context = LocalContext.current

    //홈 배경 설정
    Box(modifier = Modifier
        .fillMaxSize()
        .background(HomeBackground)
    ) {
        //전체
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier .height(60.dp))

            //위에 바
            Box ( modifier = Modifier .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Row ( modifier = Modifier
                    .align(Alignment.CenterStart)
                ){
                    Spacer(modifier = Modifier .width(20.dp))

                    Image( painter = painterResource(R.drawable.mint_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .height(48.dp)
                    )
                }

                Row ( modifier = Modifier
                    .align(Alignment.CenterEnd)
                ){
                    Image(painter = painterResource(R.drawable.info),
                        contentDescription = null,
                        modifier = Modifier
                            .size(28.dp) )

                    Spacer(modifier = Modifier .width(25.dp))
                }
            }

            Spacer(modifier = Modifier .height(60.dp))

            //박스
            Column( modifier = Modifier
                .width(346.dp)
//                .height(414.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                Spacer(modifier = Modifier .height(20.dp))

                Row {
                    Spacer(modifier = Modifier .width(22.dp))

                    Text(text = "출석 체크",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier .height(18.dp))

                Image(painter = painterResource(R.drawable.phone_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(238.dp)
                        .height(258.dp)
                        .align(Alignment.CenterHorizontally) )

                Spacer(modifier = Modifier .height(18.dp))

                Box( modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                ) {
                    AttendanceButton(
                        firstname = "출석하기",
                        secondname = "퇴실하기",
                        onclickCheck = {
                            Toast.makeText(context, "nfc태그를 인식해주세요.", Toast.LENGTH_LONG).show()

                            viewModel.startAttendance()

                            if ( status == "출석 완료" ) {
                                Toast.makeText(context, "출석 완료", Toast.LENGTH_LONG).show()
                                isChecked = true
                            } else if (status == "출석 실패") {
                                Toast.makeText(context, "출석 실패", Toast.LENGTH_LONG).show()
                            }
                        },
                        onclickReset = {
                            viewModel.startResetAttendance()

                            if ( status == "출석 초기화 완료" ) {
                                Toast.makeText(context, "퇴실 완료", Toast.LENGTH_LONG).show()
                                isChecked = false
                            } else if (status == "출석 초기화 실패") {
                                Toast.makeText(context, "퇴실 실패", Toast.LENGTH_LONG).show()
                            }
                        },
                        ischecked = isChecked
                    )
                }

                Spacer(modifier = Modifier .height(20.dp))
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth() ) {
            HomeNavBar(
                navController = navController,
                thisScreen = "Attendance"
            )
        }
    }
}