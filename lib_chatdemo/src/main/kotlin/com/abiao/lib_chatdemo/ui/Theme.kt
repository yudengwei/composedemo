package com.abiao.lib_chatdemo.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

internal val LightDefaultColorScheme = lightColorScheme(
    primary = PrimaryColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceVariant = SurfaceVariant,
    onPrimary = OnPrimaryColor,
)
internal val DarkDefaultColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    onPrimary = OnPrimaryColor,
    surface = SurfaceColorDark,
    onSurface = OnSurfaceColorDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceVariant = SurfaceVariantDark
)


@Composable
fun ChatDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}