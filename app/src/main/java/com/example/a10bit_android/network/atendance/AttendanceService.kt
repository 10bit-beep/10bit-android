package com.example.a10bit_android.network.atendance

import com.example.a10bit_android.remote.BeepUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface AttendanceService {
    @POST(BeepUrl.Attendance.check)
    suspend fun attendanceCheck(
        @Body request: AttendanceCheckReQuest
    ): String

    @POST(BeepUrl.Attendance.reset)
    suspend fun attendanceReset(
        @Body request: AttendanceResetRequest
    ): String
}