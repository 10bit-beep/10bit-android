package com.example.a10bit_android.network.start.login

import com.example.a10bit_android.remote.BeepUrl
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @Headers("userAgent: Android-123")
    @POST(BeepUrl.Auth.login)
    suspend fun login(
        @Body request: LoginReQuest
    ): LoginResponse
}
