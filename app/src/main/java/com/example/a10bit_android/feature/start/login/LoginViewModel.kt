package com.example.a10bit_android.feature.start.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10bit_android.network.data.login.LoginReQuest
import com.example.a10bit_android.network.data.login.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.a10bit_android.network.data.login.LoginService


@HiltViewModel
class LoginViewModel @Inject constructor
    (private val loginService: LoginService) : ViewModel() {

    private val _loginSuccess = mutableStateOf<Boolean?>(null)
    val loginSuccess: State<Boolean?> = _loginSuccess

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                // 로그인 요청 생성
                val request = LoginReQuest(username, password)

                // 서버에 로그인 요청
                val response: LoginResponse = loginService.login(request)

                // 서버 내부 success 여부 확인
                if (response.success) {
                    // 로그인 성공
                    println("로그인 성공: 토큰 -> ${response.token}")
                    _loginSuccess.value = true
                    // token 저장 로직 추가
                } else {
                    // 로그인 실패
                    println("로그인 실패: success = ${response.success}")
                    _loginSuccess.value = false
                }

            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> {
                        println("로그인 실패: 잘못된 사용자 이름 또는 비밀번호")
                        _loginSuccess.value = false
                    }
//                    // 그외 HTTP 오류 코드
                    else -> {
                        println("로그인 실패: HTTP 오류 코드 ${e.code()}")
                        _loginSuccess.value = false
                    }
                }

                // 네트워크 오류
            } catch (e: IOException) {
                println("네트워크 오류: ${e.message}")
                _loginSuccess.value = false

                // 기타 예외 처리
            } catch (e: Exception) {
                println("알 수 없는 오류: ${e.message}")
                _loginSuccess.value = false
            }
        }
    }
}