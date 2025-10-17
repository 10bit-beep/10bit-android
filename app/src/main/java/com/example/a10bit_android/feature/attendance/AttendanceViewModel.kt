package com.example.a10bit_android.feature.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10bit_android.data.UserRepository // Repository import
import com.example.a10bit_android.network.atendance.AttendanceCheckService
import com.example.a10bit_android.network.atendance.AttendanceCheckReQuest
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttendanceViewModel @Inject constructor(
    private val attendanceService: AttendanceCheckService,
    private val userRepository: UserRepository // Repository 주입
) : ViewModel() {

    // LiveData/StateFlow 정의
    private val _tagId = MutableLiveData<String>()
    val tagId: LiveData<String> get() = _tagId

    private val _attendanceStatus = MutableLiveData<String>()
    val attendanceStatus: LiveData<String> get() = _attendanceStatus

    // PUBLIC_ID를 Repository에서 Flow로 가져와 StateFlow로 변환
    // StateFlow는 UI가 관찰하기 쉬운 형태로, 데이터 변화를 실시간으로 반영ㄱㄴ
    val publicId: StateFlow<String?> = userRepository.publicIdFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // UI가 활성화된 동안 구독 유지
            initialValue = null // 초기값
        )

    private val _isNfcActive = MutableLiveData<Boolean>(false)
    val isNfcActive: LiveData<Boolean> get() = _isNfcActive

    fun startAttendance() {
        _attendanceStatus.value = "태그를 인식시켜 주세요."
        _isNfcActive.value = true
    }

    fun onNfcTagScanned(tagId: String) {
        _tagId.value = tagId
        _isNfcActive.value = false

        viewModelScope.launch {
            try {
                val currentPublicId = publicId.value

                if (currentPublicId.isNullOrEmpty()) {
                    _attendanceStatus.value = "PublicId가 존재하지 않습니다."
                    return@launch
                }

                val request = AttendanceCheckReQuest(
                    publicId = currentPublicId,
                    nfcTag = tagId
                )
                val result: String = attendanceService.attendanceCheck(request)

                if (result == "출석 완료") {
                    _attendanceStatus.value = "출석 완료"
                } else {
                    _attendanceStatus.value = "출석 실패"
                }
            } catch (e: Exception) {
                _attendanceStatus.value = "출석 실패: ${e.message}"
            }
        }
    }
}