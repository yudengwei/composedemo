package com.abiao.demo.second

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CircleParameters(
    val radius: Dp,
    val backgroundColor: Color
)

object CircleParametersDefaults {

    private val defaultCircleRadius = 12.dp

    fun circleParameters(
        radius : Dp = defaultCircleRadius,
        backgroundColor: Color = Color.Cyan
    ) = CircleParameters(radius, backgroundColor)
}


