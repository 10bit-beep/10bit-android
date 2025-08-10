package com.example.a10bit.ui.server

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val message: String) : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.instance

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> = _uiState

    var studentId = mutableStateOf("")
    var publicId = mutableStateOf("")
    var password = mutableStateOf("")
    var email = mutableStateOf("")


    fun signup() {
        if (_uiState.value is SignupUiState.Loading) return

        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading
            try {
                val request = SignupRequest(
                    studentNumber = studentId.value.toIntOrNull() ?: 0,
                    publicId = publicId.value,
                    password = password.value,
                    email = email.value
                )
                val response = apiService.signup(request)

                if (response.isSuccessful && response.body()?.success == true) {
                    _uiState.value = SignupUiState.Success("회원가입 성공!")
                } else {
                    _uiState.value = SignupUiState.Error(response.body()?.message ?: "회원가입에 실패했습니다.")
                }
            } catch (e: Exception) {
                _uiState.value = SignupUiState.Error("회원가입 중 오류가 발생했습니다.")
            }
        }
    }

    fun resetStateToIdle() {
        _uiState.value = SignupUiState.Idle
    }
}