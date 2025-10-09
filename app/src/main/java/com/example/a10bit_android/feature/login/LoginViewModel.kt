package com.example.a10bit_android.feature.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a10bit_android.network.data.login.LoginRequest
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

    private val _loginResult = mutableStateOf<String?>(null)
    val loginResult: State<String?> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                // 로그인 요청
                val request = LoginRequest(username, password)

                // 서버에 로그인 요청
                val response: LoginResponse = loginService.login(request)

                // 200 응답
                println("로그인 성공: 토큰 -> ${response.token}")

                // HTTP 오류 400
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> {
//                        _loginResult.value = "로그인 실패: 잘못된 사용자 이름 또는 비밀번호"
                        println("로그인 실패: 잘못된 사용자 이름 또는 비밀번호")
                    }
//                    // 그외 HTTP 오류 코드
                    else -> {
                        println("로그인 실패: HTTP 오류 코드 ${e.code()}")
                    }
                }

                // 네트워크 오류
            } catch (e: IOException) {
//                _loginResult.value = "네트워크 오류: ${e.message}"
                println("네트워크 오류: ${e.message}")

                // 기타 예외 처리
            } catch (e: Exception) {
//                _loginResult.value = "알 수 없는 오류: ${e.message}"
                println("알 수 없는 오류: ${e.message}")
            }
        }
    }
}