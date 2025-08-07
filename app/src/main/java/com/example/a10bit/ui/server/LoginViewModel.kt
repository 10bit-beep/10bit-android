package com.example.a10bit.ui.server

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.instance
    private val authTokenManager = AuthTokenManager(application)

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(publicId: String, password: String) {
        if (_uiState.value is LoginUiState.Loading) return

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val response = apiService.login(LoginRequest(publicId, password))

                if (response.isSuccessful) {
                    val token = response.body()

                    if (!token.isNullOrBlank()) {
                        authTokenManager.saveToken(token)
                        _uiState.value = LoginUiState.Success
                    } else {
                        _uiState.value = LoginUiState.Error("서버로부터 유효한 토큰을 받지 못했습니다.")
                    }
                } else {
                    _uiState.value = LoginUiState.Error("아이디 또는 비밀번호가 잘못되었습니다.")
                }
            } catch (e: IOException) {
                _uiState.value = LoginUiState.Error("네트워크 연결을 확인해주세요.")
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error("알 수 없는 오류가 발생했습니다.")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}