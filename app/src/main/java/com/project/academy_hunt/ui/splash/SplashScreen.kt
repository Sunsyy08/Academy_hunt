package com.project.academy_hunt.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.academy_hunt.ui.theme.Blue600
import com.project.academy_hunt.ui.theme.Gray500
import com.project.academy_hunt.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToOnboarding: () -> Unit) {
    val scale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue   = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness    = Spring.StiffnessLow
            )
        )
        delay(1500L)
        onNavigateToOnboarding()
    }

    Box(
        modifier         = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier         = Modifier
                    .scale(scale.value)
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Blue600),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "A",
                    color      = White,
                    fontSize   = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text       = "Academy Hunt",
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                color      = Blue600
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text     = "학원이 먼저 제안합니다",
                fontSize = 14.sp,
                color    = Gray500
            )
        }

        CircularProgressIndicator(
            modifier    = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .size(24.dp),
            color       = Blue600,
            strokeWidth = 2.dp
        )
    }
}