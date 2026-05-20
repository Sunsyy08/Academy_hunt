package com.project.academy_hunt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.academy_hunt.data.auth.AuthRepository
import com.project.academy_hunt.data.core.TokenDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val email          : String  = "",
    val password       : String  = "",
    val name           : String  = "",
    val role           : String  = "student",
    val isLoading      : Boolean = false,
    val errorMessage   : String  = "",
    val registerSuccess: String  = ""
)

class RegisterViewModel(
    private val repository    : AuthRepository,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(v: String)    { _uiState.value = _uiState.value.copy(email    = v, errorMessage = "") }
    fun onPasswordChange(v: String) { _uiState.value = _uiState.value.copy(password = v, errorMessage = "") }
    fun onNameChange(v: String)     { _uiState.value = _uiState.value.copy(name     = v, errorMessage = "") }
    fun onRoleChange(v: String)     { _uiState.value = _uiState.value.copy(role     = v) }

    fun register() {
        val s = _uiState.value
        if (s.email.isBlank() || s.password.isBlank() || s.name.isBlank()) {
            _uiState.value = s.copy(errorMessage = "모든 항목을 입력해주세요.")
            return
        }
        if (s.password.length < 6) {
            _uiState.value = s.copy(errorMessage = "비밀번호는 최소 6자 이상이어야 합니다.")
            return
        }
        viewModelScope.launch {
            _uiState.value = s.copy(isLoading = true)
            repository.register(s.email, s.password, s.name, s.role)
                .onSuccess { data ->
                    tokenDataStore.saveAuthData(
                        token = data.token,
                        role  = data.user.role,
                        name  = data.user.name,
                        id    = data.user.id.toString()
                    )
                    _uiState.value = _uiState.value.copy(
                        isLoading       = false,
                        registerSuccess = data.user.role
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading    = false,
                        errorMessage = e.message ?: "회원가입에 실패했습니다."
                    )
                }
        }
    }
}