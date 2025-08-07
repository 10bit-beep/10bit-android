package com.example.a10bit.ui.server

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profileData: ProfileResponse) : ProfileUiState()
    object Error : ProfileUiState()
}

sealed class ProfileEvent {
    object LogoutCompleted : ProfileEvent()
}

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = RetrofitClient.instance
    private val authTokenManager = AuthTokenManager(application)

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ProfileEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            val token = authTokenManager.getToken()
            if (token == null) {
                _uiState.value = ProfileUiState.Error
                return@launch
            }

            try {
                val response = apiService.getUserProfile("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = ProfileUiState.Success(response.body()!!)
                } else {
                    _uiState.value = ProfileUiState.Error
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error
            }
        }
    }

    //로그아웃
    fun logout() {
        viewModelScope.launch {
            authTokenManager.deleteToken()
            _eventFlow.emit(ProfileEvent.LogoutCompleted)
        }
    }
}