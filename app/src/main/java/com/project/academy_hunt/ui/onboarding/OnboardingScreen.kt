package com.project.academy_hunt.ui.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.Blue50
import com.project.academy_hunt.ui.theme.Blue100
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray100
import com.project.academy_hunt.ui.theme.Gray200
import com.project.academy_hunt.ui.theme.Gray400
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.Gray900
import com.project.academy_hunt.ui.theme.White
import com.project.academy_hunt.viewmodel.OnboardingViewModel
import com.project.academy_hunt.ui.theme.*

@Composable
fun OnboardingScreen(
    viewModel        : OnboardingViewModel,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text       = "어떤 분이신가요?",
                fontSize   = 26.sp,
                fontWeight = FontWeight.Bold,
                color      = Gray900,
                textAlign  = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text      = "역할을 선택해주세요",
                fontSize  = 15.sp,
                color     = Gray500,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            RoleCard(
                title       = "학생",
                description = "수강 조건을 올리고 제안을 받아보세요",
                emoji       = "🎓",
                isSelected  = uiState.selectedRole == "student",
                onClick     = { viewModel.selectRole("student") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                title       = "학원",
                description = "학생에게 먼저 제안해보세요",
                emoji       = "🏫",
                isSelected  = uiState.selectedRole == "academy",
                onClick     = { viewModel.selectRole("academy") }
            )
        }

        Button(
            onClick  = { if (viewModel.isRoleSelected()) onNavigateToLogin() },
            enabled  = viewModel.isRoleSelected(),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor         = Blue600,
                disabledContainerColor = Gray200
            )
        ) {
            Text(
                text       = "시작하기",
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color      = if (viewModel.isRoleSelected()) White else Gray400
            )
        }
    }
}

@Composable
private fun RoleCard(
    title      : String,
    description: String,
    emoji      : String,
    isSelected : Boolean,
    onClick    : () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue   = if (isSelected) Blue600 else Gray200,
        animationSpec = tween(200),
        label         = "border"
    )
    val bgColor by animateColorAsState(
        targetValue   = if (isSelected) Blue50 else White,
        animationSpec = tween(200),
        label         = "bg"
    )

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape     = RoundedCornerShape(16.dp),
        border    = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors    = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier         = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Blue100 else Gray100),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text       = title,
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                color      = if (isSelected) Blue600 else Gray900
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text      = description,
                fontSize  = 13.sp,
                color     = Gray500,
                textAlign = TextAlign.Center
            )
        }
    }
}