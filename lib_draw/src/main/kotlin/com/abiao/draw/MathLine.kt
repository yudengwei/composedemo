package com.abiao.draw

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas


val X_MARGIN = 40f
val Y_MARGIN = 80f
val MARGIN_30 = 30
val MARGIN_15 = 15
val MARGIN_50 = 50

@Composable
fun Axis(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawXLine(canvasWidth, canvasHeight)
        drawYLine(canvasWidth, canvasHeight)
        drawText(canvasWidth, canvasHeight)
    }
}

// x轴
private fun DrawScope.drawXLine(
    canvasWidth : Float,
    canvasHeight : Float
) {
    drawLine(
        start = Offset(
            x = X_MARGIN,
            y = canvasHeight / 2
        ),
        end = Offset(
            x = canvasWidth - X_MARGIN,
            y = canvasHeight / 2
        ),
        color = Color.Black,
        strokeWidth = 6F
    )

    // x轴箭头上
    drawLine(
        start = Offset(
            x = canvasWidth - X_MARGIN - MARGIN_30,
            y = canvasHeight / 2 - MARGIN_15
        ),
        end = Offset(
            x = canvasWidth - X_MARGIN,
            y = canvasHeight / 2
        ),
        color = Color.Black,
        strokeWidth = 6F
    )

    // x轴箭头下
    drawLine(
        start = Offset(
            x = canvasWidth - X_MARGIN - MARGIN_30,
            y = canvasHeight / 2 + MARGIN_15
        ),
        end = Offset(
            x = canvasWidth - X_MARGIN,
            y = canvasHeight / 2
        ),
        color = Color.Black,
        strokeWidth = 6F
    )
}

// y轴
private fun DrawScope.drawYLine(
    canvasWidth : Float,
    canvasHeight : Float
) {
    drawLine(
        start = Offset(
            x = canvasWidth / 2,
            y = Y_MARGIN
        ),
        end = Offset(
            x = canvasWidth / 2,
            y = canvasHeight - Y_MARGIN
        ),
        color = Color.Black,
        strokeWidth = 6F
    )

    // y轴箭头左
    drawLine(
        start = Offset(
            x = canvasWidth / 2 - MARGIN_15,
            y = Y_MARGIN + MARGIN_30,
        ),
        end = Offset(
            x = canvasWidth / 2 ,
            y = Y_MARGIN
        ),
        color = Color.Black,
        strokeWidth = 6F
    )

    // y轴箭头右
    drawLine(
        start = Offset(
            x = canvasWidth / 2 + MARGIN_15 ,
            y = Y_MARGIN + MARGIN_30
        ),
        end = Offset(
            x = canvasWidth / 2 ,
            y = Y_MARGIN
        ),
        color = Color.Black,
        strokeWidth = 6F
    )
}

// 绘制0，x, y
private fun DrawScope.drawText(
    canvasWidth : Float,
    canvasHeight : Float
) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            "0",
            canvasWidth / 2 - MARGIN_50,
            canvasHeight / 2 - MARGIN_50,
            Paint().apply {
                textSize = 60F
                color = 0XFF000000.toInt()
            }
        )
    }

    drawContext.canvas.nativeCanvas.apply {
        drawText(
            "x",
            canvasWidth - X_MARGIN - MARGIN_50,
            canvasHeight / 2 + MARGIN_50 + MARGIN_15,
            Paint().apply {
                textSize = 60F
                color = 0XFF000000.toInt()
            }
        )
    }

    drawContext.canvas.nativeCanvas.apply {
        drawText(
            "y",
            canvasWidth / 2 + MARGIN_50,
            Y_MARGIN + MARGIN_50,
            Paint().apply {
                textSize = 60F
                color = 0XFF000000.toInt()
            }
        )
    }
}
