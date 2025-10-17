package com.example.a10bit_android.network.start.signup

import com.example.a10bit_android.remote.BeepUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST(BeepUrl.Auth.signup)
    suspend fun signup(
        @Body request: SignUpReQuest
    ): SignUpResponse
}