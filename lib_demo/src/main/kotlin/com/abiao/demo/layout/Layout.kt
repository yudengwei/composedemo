package com.abiao.demo.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.sp

@Composable
fun LayoutText(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .layout {measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                },
            fontSize = 30.sp,
            color = Color.Red
        )
    }
}

@Composable
fun LayoutGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) {measurables, contraints ->
        val placeables = measurables.map {
            it.measure(contraints)
        }
        var yPosition = 0
        layout(contraints.maxWidth, contraints.maxHeight) {
            placeables.forEach {
                it.placeRelative(0, yPosition)
                yPosition += it.height
            }
        }
    }
}