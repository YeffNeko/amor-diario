package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = LovePrimary,
    onPrimary = LoveOnPrimary,
    primaryContainer = LovePrimaryContainer,
    onPrimaryContainer = LoveOnPrimaryContainer,
    secondary = LoveSecondary,
    onSecondary = LoveOnSecondary,
    secondaryContainer = LoveSecondaryContainer,
    onSecondaryContainer = LoveOnSecondaryContainer,
    tertiary = LoveTertiary,
    onTertiary = LoveOnTertiary,
    tertiaryContainer = LoveTertiaryContainer,
    onTertiaryContainer = LoveOnTertiaryContainer,
    background = LoveBackground,
    onBackground = LoveOnBackground,
    surface = LoveSurface,
    onSurface = LoveOnSurface,
    surfaceVariant = LoveSurfaceVariant,
    onSurfaceVariant = LoveOnSurfaceVariant,
    outline = LoveOutline
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB2BC),
    onPrimary = Color(0xFF65001C),
    primaryContainer = Color(0xFF8F0D2E),
    onPrimaryContainer = Color(0xFFFFD9DF),
    secondary = Color(0xFFFFB2C2),
    onSecondary = Color(0xFF5E1129),
    secondaryContainer = Color(0xFF7B253D),
    onSecondaryContainer = Color(0xFFFFD9E2),
    tertiary = Color(0xFFFFD286),
    onTertiary = Color(0xFF432C00),
    background = Color(0xFF1E1015),
    onBackground = Color(0xFFF9EAEF),
    surface = Color(0xFF26151B),
    onSurface = Color(0xFFF9EAEF),
    surfaceVariant = Color(0xFF40262E),
    onSurfaceVariant = Color(0xFFDCC2C9)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional romantic branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
