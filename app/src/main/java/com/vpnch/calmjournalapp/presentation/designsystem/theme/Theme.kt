package com.vpnch.calmjournalapp.presentation.designsystem.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Colors.Primary,
    onPrimary = Color.White,

    surface = Colors.SurfaceVariant,
    onSurface = Colors.OnSurface,

    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Colors.OnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = Colors.Primary,
    onPrimary = Colors.OnPrimary,

    surface = Colors.Surface,
    onSurface = Colors.OnSurface,

    surfaceVariant = Colors.SurfaceVariant,
    onSurfaceVariant = Colors.OnSurfaceVariant,

    primaryContainer = Colors.Primary.copy(alpha = 0.1f),
    onPrimaryContainer = Colors.Primary.copy(alpha = 0.8f),

    background = Color.White,
    onBackground = Colors.OnPrimary,

    onSecondary = Colors.DotsGrey,
)

@Composable
fun CalmJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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