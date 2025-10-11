package com.example.a10bit_android.ui.component.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.a10bit_android.ui.theme.errorColor

@Composable
fun ClubSelector(
    selectedClub: String,
    onClubSelected: (String) -> Unit,
    onclick: () -> Unit,
    isError: Boolean = false
) {
    val clubs = mapOf(
        "랩 21, 22실 (ALT)" to "LAB21_22",
        "랩 19, 20실 (B1ND)" to "LAB19_20",
        "프로젝트 6실 (3D)" to "PROJECT6",
        "프로젝트 4실 (Louter)" to "PROJECT4",
        "랩 17, 18실 (CNS)" to "LAB17_18",
        "프로젝트 5실 (MODI)" to "PROJECT5",
        "랩 6, 7실 (DUCAMI)" to "LAB6_7",
        "랩 10, 11실 (Chatty)" to "LAB10_11"
    )

    var expanded by remember { mutableStateOf(false) }

    Column {
        Row (
            modifier = Modifier.align(Alignment.Start)
        ) {
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "소속 동아리",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        val borderColor = if (isError) errorColor else Color.Black.copy(alpha = 0.7f)

        Box {
            OutlinedTextField(
                value = selectedClub,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                shape = RoundedCornerShape(17.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor
                ),
                placeholder = {
                    Text(
                        text = "소속 동아리를 선택해주세요",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { expanded != expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "null"
                        )
                    }
                },
                modifier = Modifier
                    .width(323.dp)
                    .height(52.dp)
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(323.dp)
            ) {
                clubs.forEach { club ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = club.key,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        onClick = { onclick
//                            onClubSelected(club.value)
//                            expanded = false
                        }
                    )
                }
                if (isError){
                    Text(text = "동아리를 선택해주세요.",
                        color = errorColor,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}