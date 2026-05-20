package com.project.academy_hunt.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.*
import com.project.academy_hunt.viewmodel.LoginViewModel
import com.project.academy_hunt.ui.theme.AppError
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray700
import com.project.academy_hunt.ui.theme.Gray900

@Composable
fun LoginScreen(
    viewModel           : LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess      : (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess.isNotEmpty()) {
            onLoginSuccess(uiState.loginSuccess)
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier         = Modifier
                    .size(36.dp)
                    .background(Blue600, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("A", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text       = "Academy Hunt",
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = Blue600
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text       = "로그인",
            fontSize   = 26.sp,
            fontWeight = FontWeight.Bold,
            color      = Gray900,
            modifier   = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

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
            placeholder          = { Text("비밀번호", color = Gray400) },
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

        if (uiState.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text     = uiState.errorMessage,
                color    = AppError,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick  = viewModel::login,
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
                    text       = "로그인",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Text("계정이 없으신가요? ", fontSize = 14.sp, color = Gray500)
            TextButton(
                onClick        = onNavigateToRegister,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text       = "회원가입",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = Blue600
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier          = Modifier.fillMaxWidth()
        ) {
            Divider(modifier = Modifier.weight(1f), color = Gray200)
            Text("  또는  ", fontSize = 13.sp, color = Gray400)
            Divider(modifier = Modifier.weight(1f), color = Gray200)
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick  = { /* TODO: Google OAuth */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape  = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = White,
                contentColor   = Gray700
            )
        ) {
            Text("G", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4285F4))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text       = "Google로 계속하기",
                fontSize   = 15.sp,
                fontWeight = FontWeight.Medium,
                color      = Gray700
            )
        }
    }
}