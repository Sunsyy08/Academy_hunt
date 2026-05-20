package com.project.academy_hunt.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OnboardingUiState(
    val selectedRole: String = ""
)

class OnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState = _uiState.asStateFlow()

    fun selectRole(role: String) {
        _uiState.value = _uiState.value.copy(selectedRole = role)
    }

    fun isRoleSelected(): Boolean = _uiState.value.selectedRole.isNotEmpty()
}