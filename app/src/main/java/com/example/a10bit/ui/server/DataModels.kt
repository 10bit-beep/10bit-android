package com.example.a10bit.ui.server

import com.google.gson.annotations.SerializedName

//로그인
data class LoginRequest(
    @SerializedName("publicId") val publicId: String,
    @SerializedName("password") val password: String
)
//수정 필요 (로그인 후 받는 값)
data class LoginResponse(
    val token: String?,
    val message: String?
)

//회원가입
//수정 필요 (회원가입시 보낼 값)
data class SignupRequest(
    @SerializedName("studentNumber") val studentNumber: Int,
    @SerializedName("name")          val name: String,
    @SerializedName("publicId")      val publicId: String,
    @SerializedName("password")      val password: String,
    @SerializedName("email")         val email: String
)

//수정 필요 (이메일 인증 후 받는 값)
data class GenericApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)


//출석
data class AttendanceRequest(
    @SerializedName("nfc_tag_id") val nfcTagId: String
)
data class AttendanceResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("isCheckedIn") val isCheckedIn: Boolean
)
data class StatusResponse(
    @SerializedName("isCheckedIn") val isCheckedIn: Boolean
)

data class ProfileResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("studentId")
    val studentId: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("room")
    val room: String
)