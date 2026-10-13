package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MedixinBlue,
    onPrimary = MedixinSurface,
    primaryContainer = MedixinBlueDark,
    onPrimaryContainer = MedixinBlueLight,
    secondary = MedixinCyan,
    onSecondary = MedixinDark,
    background = MedixinDark,
    surface = Color(0xFF1E2638),
    onBackground = MedixinSurface,
    onSurface = MedixinSurface,
    outline = MedixinMutedText
)

private val LightColorScheme = lightColorScheme(
    primary = MedixinBlue,
    onPrimary = MedixinSurface,
    primaryContainer = MedixinBlueLight,
    onPrimaryContainer = MedixinBlueDeep,
    secondary = MedixinCyan,
    onSecondary = MedixinDark,
    background = MedixinBackground,
    surface = MedixinSurface,
    onBackground = MedixinDarkText,
    onSurface = MedixinDarkText,
    surfaceVariant = MedixinSurfaceVariant,
    onSurfaceVariant = MedixinMutedText,
    outline = MedixinBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = colorScheme.background.toArgb()
                it.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(it, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
