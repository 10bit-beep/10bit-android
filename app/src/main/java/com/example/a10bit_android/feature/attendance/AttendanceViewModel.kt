package com.example.a10bit_android.feature.attendance

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10bit_android.data.UserRepository // Repository import
import com.example.a10bit_android.network.atendance.AttendanceCheckReQuest
import com.example.a10bit_android.network.atendance.AttendanceResetRequest
import com.example.a10bit_android.network.atendance.AttendanceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val attendanceService: AttendanceService,
    private val userRepository: UserRepository // Repository 주입
) : ViewModel() {

    // LiveData/StateFlow 정의
    private val _tagId = MutableLiveData<String>()
//    val tagId: LiveData<String> get() = _tagId

    private val _attendanceStatus = MutableStateFlow("")
    val attendanceStatus: StateFlow<String> get() = _attendanceStatus

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
//        println("테스트용 성공 (요청 생략)")
//        _attendanceStatus.value = "출석 완료"
//
//        viewModelScope.launch {
//            userRepository.saveUserData(isChecked = true)
//        }
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

    fun startResetAttendance() {
        viewModelScope.launch {
            try {

                // 서버에 출석 초기화 요청
                val result: String = attendanceService.attendanceReset(request = AttendanceResetRequest())

                if (result == "출석 초기화 완료") {
                    // Repository도 초기화
                    userRepository.saveUserData(isChecked = false)

                } else {
                    _attendanceStatus.value = "출석 초기화 실패: $result"
                }
            } catch (e: Exception) {
                _attendanceStatus.value = "출석 초기화 실패: ${e.message}"
            }
        }
    }
}