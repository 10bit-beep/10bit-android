package com.example.a10bit_android.feature.start.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10bit_android.network.data.signup.SignUpReQuest
import com.example.a10bit_android.network.data.signup.SignUpResponse
import com.example.a10bit_android.network.data.signup.SignUpService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class SignUpViewModel @Inject constructor
    (private val signupservice: SignUpService) : ViewModel() {

        private val _signupSuccess = mutableStateOf<Boolean?>(null)
        val signupSuccess: State<Boolean?> = _signupSuccess

        fun signup (
            studentNumber: Number,
            publicId: String,
            password: String,
            email: String,
            club: String
        ) {
            viewModelScope.launch {
                try {
                    //회원가입 요청
                    val request = SignUpReQuest(studentNumber, publicId, password, email, club)

                    //서버에 회원가입 요청
                    val response: SignUpResponse = signupservice.signup(request)

                    //서버 내부 success 여부 확인
                    if (response.success) {
                        //회원가입 성공
                        println("회원가입 성공: Id -> ${response.publicId}")
                        _signupSuccess.value = true
                    } else {
                        //회원가입 실패
                        println("회원가입 실패: success = ${response.success}")
                        _signupSuccess.value = false
                    }
                } catch (e: HttpException) {
                    when (e.code()) {
                        400 -> {
                            println("로그인 실패: 잘못된 사용자 이름 또는 비밀번호")
                            _signupSuccess.value = false
                        }
//                    // 그외 HTTP 오류 코드
                        else -> {
                            println("로그인 실패: HTTP 오류 코드 ${e.code()}")
                            _signupSuccess.value = false
                        }
                    }

                    // 네트워크 오류
                } catch (e: IOException) {
                    println("네트워크 오류: ${e.message}")
                    _signupSuccess.value = false

                    // 기타 예외 처리
                } catch (e: Exception) {
                    println("알 수 없는 오류: ${e.message}")
                    _signupSuccess.value = false
                }
            }
        }
    }