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
import com.example.a10bit_android.ui.theme.mainColor

@Composable
fun AttendanceScreen (
    navController: NavHostController
) {
    val viewModel: AttendanceViewModel = hiltViewModel()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(HomeBackground)
    ) {
        Column(modifier = Modifier
            .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier .height(60.dp))

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

            Column( modifier = Modifier
                .width(346.dp)
//                .height(414.dp)
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
                        .align(Alignment.CenterHorizontally) )

                Spacer(modifier = Modifier .height(18.dp))

                AttendanceButton(
                    buttonname = "출석하기",
                    buttonColor = mainColor,
                    onclick = {
                        viewModel.startAttendance()
                    }
                )
            }
        }
    }
}