package com.imagepick.ui.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorScheme = lightColorScheme()

@Composable
internal fun ImagePickTheme(content : @Composable () -> Unit) {
    MaterialTheme (
        colorScheme = lightColorScheme,
        content = content
    )
}