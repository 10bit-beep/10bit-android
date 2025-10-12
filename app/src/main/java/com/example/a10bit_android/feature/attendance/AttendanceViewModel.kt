package com.example.a10bit_android.feature.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttendanceViewModel @Inject constructor
    (private val AttendanceService: AttendanceService) : ViewModel() {
    private val _startNfcEvent = MutableSharedFlow<Unit>()
    val startNfcEvent = _startNfcEvent.asSharedFlow()

    fun onAttendanceButtonClicked() {
        viewModelScope.launch {
            _startNfcEvent.emit(Unit)  // Activity에서 NFC 켤 수 있게 신호 전달용도
        }
    }

    fun onNfcTagScanned(tagId: String) {
        // 태그 ID 처리
    }
}