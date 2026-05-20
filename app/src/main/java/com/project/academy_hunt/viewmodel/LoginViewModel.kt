package com.project.academy_hunt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.academy_hunt.data.auth.AuthRepository
import com.project.academy_hunt.data.core.TokenDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email        : String  = "",
    val password     : String  = "",
    val isLoading    : Boolean = false,
    val errorMessage : String  = "",
    val loginSuccess : String  = ""
)

class LoginViewModel(
    private val repository    : AuthRepository,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(v: String) {
        _uiState.value = _uiState.value.copy(email = v, errorMessage = "")
    }

    fun onPasswordChange(v: String) {
        _uiState.value = _uiState.value.copy(password = v, errorMessage = "")
    }

    fun login() {
        val s = _uiState.value
        if (s.email.isBlank() || s.password.isBlank()) {
            _uiState.value = s.copy(errorMessage = "이메일과 비밀번호를 입력해주세요.")
            return
        }
        viewModelScope.launch {
            _uiState.value = s.copy(isLoading = true)
            repository.login(s.email, s.password)
                .onSuccess { data ->
                    tokenDataStore.saveAuthData(
                        token = data.token,
                        role  = data.user.role,
                        name  = data.user.name,
                        id    = data.user.id.toString()
                    )
                    _uiState.value = _uiState.value.copy(
                        isLoading    = false,
                        loginSuccess = data.user.role
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading    = false,
                        errorMessage = e.message ?: "로그인에 실패했습니다."
                    )
                }
        }
    }
}