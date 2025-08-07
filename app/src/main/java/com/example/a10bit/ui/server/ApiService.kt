package com.example.a10bit.ui.server

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "http://localhost:8080" //서버 주소로 변경

object RetrofitClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {

    //로그인
    @POST("/auth/login")
    suspend fun login(@Body requestBody: LoginRequest): Response<String>

    //이메일 인증
    @GET("/auth/email")
    suspend fun requestEmailCode(@Query("email") email: String): Response<Unit> // 성공 시 내용이 없으므로 Unit

    //이메일 인증 코드 확인-
    @GET("/auth/verify")
    suspend fun verifyEmailCode(
        @Query("email") email: String,
        @Query("inputCode") code: String
    ): Response<Unit>

    //회원가입
    @POST("/auth/signup")
    suspend fun signup(@Body requestBody: SignupRequest): Response<GenericApiResponse>


    //출석
    @POST("v1/attendance/check")
    suspend fun checkAttendance(
        @Header("Authorization") token: String,
        @Body request: AttendanceRequest
    ): Response<AttendanceResponse>

    @GET("v1/attendance/status")
    suspend fun getAttendanceStatus(
        @Header("Authorization") token: String
    ): Response<StatusResponse>

    //받을 내용 수정 필요
    @GET("v1/user/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<ProfileResponse>

}

