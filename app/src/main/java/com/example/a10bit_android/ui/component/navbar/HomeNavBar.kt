package com.example.a10bit_android.ui.component.navbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a10bit_android.R
import com.example.a10bit_android.ui.theme.iconCheckColor
import com.example.a10bit_android.ui.theme.iconOGColor
import com.example.a10bit_android.ui.theme.textCheckColor
import com.example.a10bit_android.ui.theme.textOGColor

@Composable
fun HomeNavBar(
    navController: NavHostController,
    thisScreen: String
) {
    val isHomeCheck = if (thisScreen == "Attendance") true else false
//    val isProfileCheck = if (thisScreen == "Profile") true else false

    Column(modifier = Modifier
        .height(90.dp)
        .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier .height(16.dp))

        Row ( modifier = Modifier
            .align(Alignment.CenterHorizontally)
        ){
            Column () {
                Icon(painter = painterResource(R.drawable.home_icon),
                    contentDescription = null,
                    tint = if (isHomeCheck) iconCheckColor else iconOGColor,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally) )

                Spacer(modifier = Modifier .height(4.dp))

                Text(text = "홈",
                    color = if (isHomeCheck) textCheckColor else textOGColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally) )
            }

//            Spacer(modifier = Modifier .width(112.dp))
//
//            Column () {
//                Icon(painter = painterResource(R.drawable.profile_icon),
//                    contentDescription = null,
//                    tint = if (isProfileCheck) iconCheckColor else iconOGColor,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .align(Alignment.CenterHorizontally) )
//
//                Spacer(modifier = Modifier .height(4.dp))
//
//                Text(text = "프로필",
//                    color = if (isProfileCheck) textCheckColor else textOGColor,
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally) )
//            }

        }

        Spacer(modifier = Modifier .height(32.dp))
    }
}