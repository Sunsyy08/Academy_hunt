package com.academyhunt.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary             = Blue600,
    onPrimary           = White,
    primaryContainer    = Blue50,
    onPrimaryContainer  = Blue700,
    background          = White,
    onBackground        = Gray900,
    surface             = White,
    onSurface           = Gray900,
    surfaceVariant      = Gray100,
    onSurfaceVariant    = Gray500,
    outline             = Gray200,
    error               = AppError,
    onError             = White
)

@Composable
fun AcademyHuntTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = AcademyTypography,
        content     = content
    )
}