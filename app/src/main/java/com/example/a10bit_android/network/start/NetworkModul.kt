package com.example.a10bit_android.network.start

import com.example.a10bit_android.network.atendance.AttendanceCheckService
import com.example.a10bit_android.network.atendance.AttendanceResetService
import com.example.a10bit_android.network.start.login.LoginService
import com.example.a10bit_android.network.start.signup.SignUpService
import com.example.a10bit_android.remote.BeepUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BeepUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignUpService(retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }

    @Provides
    @Singleton
    fun provideAttendanceCheckService(retrofit: Retrofit): AttendanceCheckService {
        return retrofit.create(AttendanceCheckService::class.java)
    }

    @Provides
    @Singleton
    fun provideAttendanceRexsetService(retrofit: Retrofit): AttendanceResetService {
        return retrofit.create(AttendanceResetService::class.java)
    }
}