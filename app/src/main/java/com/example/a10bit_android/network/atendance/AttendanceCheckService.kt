package com.example.a10bit_android.network.atendance

import com.example.a10bit_android.remote.BeepUrl
import retrofit2.http.Body
import retrofit2.http.POST

interface AttendanceCheckService {
    @POST(BeepUrl.Attendance.check)
    suspend fun attendanceCheck(
        @Body request: AttendanceReQuest
    ): AttendanceResponse
}