package com.project.academy_hunt.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.*
import com.project.academy_hunt.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel        : RegisterViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registerSuccess) {
        if (uiState.registerSuccess.isNotEmpty()) {
            onRegisterSuccess(uiState.registerSuccess)
        }
    }

    Column(
        modifier            = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text       = "회원가입",
            fontSize   = 26.sp,
            fontWeight = FontWeight.Bold,
            color      = Gray900,
            modifier   = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value         = uiState.name,
            onValueChange = viewModel::onNameChange,
            placeholder   = { Text("이름", color = Gray400) },
            modifier      = Modifier.fillMaxWidth(),
            shape         = RoundedCornerShape(12.dp),
            singleLine    = true,
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = Blue600,
                unfocusedBorderColor    = Gray200,
                focusedContainerColor   = White,
                unfocusedContainerColor = White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value           = uiState.email,
            onValueChange   = viewModel::onEmailChange,
            placeholder     = { Text("이메일", color = Gray400) },
            modifier        = Modifier.fillMaxWidth(),
            shape           = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine      = true,
            colors          = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = Blue600,
                unfocusedBorderColor    = Gray200,
                focusedContainerColor   = White,
                unfocusedContainerColor = White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value                = uiState.password,
            onValueChange        = viewModel::onPasswordChange,
            placeholder          = { Text("비밀번호 (최소 6자)", color = Gray400) },
            modifier             = Modifier.fillMaxWidth(),
            shape                = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine           = true,
            colors               = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = Blue600,
                unfocusedBorderColor    = Gray200,
                focusedContainerColor   = White,
                unfocusedContainerColor = White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text       = "역할 선택",
            fontSize   = 14.sp,
            fontWeight = FontWeight.Medium,
            color      = Gray700,
            modifier   = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            listOf("student" to "학생", "academy" to "학원").forEach { (value, label) ->
                val selected = uiState.role == value
                Box(
                    modifier         = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .background(
                            color = if (selected) Blue600 else White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (selected) Blue600 else Gray200,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { viewModel.onRoleChange(value) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = label,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = if (selected) White else Gray500
                    )
                }
            }
        }

        if (uiState.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text     = uiState.errorMessage,
                color    = AppError,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick  = viewModel::register,
            enabled  = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape  = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Blue600)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier    = Modifier.size(20.dp),
                    color       = White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text       = "회원가입",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Text("이미 계정이 있으신가요? ", fontSize = 14.sp, color = Gray500)
            TextButton(
                onClick        = onNavigateToLogin,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text       = "로그인",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = Blue600
                )
            }
        }
    }
}