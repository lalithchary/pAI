package com.pai.personalai.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.pai.personalai.domain.model.ThemeMode

private val LightColorScheme = lightColorScheme(
    primary = PaiPrimary,
    onPrimary = LightBackground,
    primaryContainer = PaiPrimary.copy(alpha = 0.12f),
    onPrimaryContainer = PaiPrimary,
    secondary = PaiSecondary,
    onSecondary = LightBackground,
    secondaryContainer = PaiSecondary.copy(alpha = 0.12f),
    onSecondaryContainer = PaiSecondary,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    error = LightError,
    onError = LightBackground
)

private val DarkColorScheme = darkColorScheme(
    primary = PaiPrimaryLight,
    onPrimary = DarkBackground,
    primaryContainer = PaiPrimary.copy(alpha = 0.2f),
    onPrimaryContainer = PaiPrimaryLight,
    secondary = PaiSecondaryLight,
    onSecondary = DarkBackground,
    secondaryContainer = PaiSecondary.copy(alpha = 0.2f),
    onSecondaryContainer = PaiSecondaryLight,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    error = DarkError,
    onError = DarkBackground
)

@Composable
fun PAiTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
