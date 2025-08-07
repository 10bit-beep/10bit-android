package com.example.a10bit.ui.server

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiEvent {
    data class ShowToast(val message: String) : HomeUiEvent()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.instance
    private val authTokenManager = AuthTokenManager(application)

    // 출석 상태를 저장
    private val _isCheckedIn = MutableStateFlow(false)
    val isCheckedIn = _isCheckedIn.asStateFlow()

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchInitialStatus()
    }

    private fun fetchInitialStatus() {
        viewModelScope.launch {
            val token = authTokenManager.getToken()
            if (token == null) {
                _eventFlow.emit(HomeUiEvent.ShowToast("인증 정보가 없습니다. 다시 로그인해주세요."))
                return@launch
            }

            try {
                val response = apiService.getAttendanceStatus("Bearer $token")
                if (response.isSuccessful) {
                    _isCheckedIn.value = response.body()?.isCheckedIn ?: false
                }
            } catch (e: Exception) {
            }
        }
    }

    fun processNfcTag(nfcTagValue: String) {
        viewModelScope.launch {
            val token = authTokenManager.getToken()
            if (token == null) {
                _eventFlow.emit(HomeUiEvent.ShowToast("인증 정보가 없습니다. 다시 로그인해주세요."))
                return@launch
            }

            try {
                val request = AttendanceRequest(nfcTagId = nfcTagValue)
                val response = apiService.checkAttendance("Bearer $token", request)

                if (response.isSuccessful && response.body() != null) {
                    val attendanceResponse = response.body()!!
                    _eventFlow.emit(HomeUiEvent.ShowToast(attendanceResponse.message))

                    if(attendanceResponse.success) {
                        _isCheckedIn.value = attendanceResponse.isCheckedIn
                    }
                } else {
                    _eventFlow.emit(HomeUiEvent.ShowToast("인증에 실패했습니다."))
                }

            } catch (e: IOException) {
                _eventFlow.emit(HomeUiEvent.ShowToast("네트워크 연결을 확인해주세요."))
            } catch (e: Exception) {
                _eventFlow.emit(HomeUiEvent.ShowToast("알 수 없는 오류가 발생했습니다."))
            }
        }
    }
}