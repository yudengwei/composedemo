package com.abiao.touch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun Lines() {
    val start = Offset(100f, 100f)
    val end = Offset(500f, 500f)
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLine(
            color = Color.Blue,
            start = start,
            end = end,
            strokeWidth = 30f,
            cap = StrokeCap.Round
        )
    }
}