package com.abiao.touch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap

/**
 * drawPoints(points, pointMode, color, cap...)
        pointMode
            PointMode.Points 只绘制点
            PointMode.Lines  每两点绘制一条线段，如果是奇数，则忽略最后一个点，每段线段不会连接起来，就是说如果有n个点，则总共有n/2条线段
            PointMode.Polygon 将这些点连成一条线段

        cap
            StrokeCap.Butt： 连接点会有间隙
            StrokeCap.Round: 连接点有圆角的过渡，没有间隙
            StrokeCap.Square: Square表示线段末端将每个轮廓延伸笔触宽度的一半

        color 点的颜色

        brush 渐变色
 */

@Composable
fun Points(
    modifier: Modifier = Modifier
) {
    val points = arrayListOf(
        Offset(100f, 100f),
        Offset(100f, 200f),
        Offset(300f, 300f),
        Offset(400f, 400f),
    )
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPoints(
                points = points,
                pointMode = PointMode.Polygon,
                color = Color.Blue,
                strokeWidth = 50f,
                cap = StrokeCap.Square
            )
        }
    }
}

@Composable
fun PointsBrush(
    modifier: Modifier = Modifier
) {
    val points = arrayListOf(
        Offset(100f, 100f),
        Offset(300f, 300f),
        Offset(500f, 500f),
        Offset(700f, 700f),
    )
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPoints(
                points = points,
                pointMode = PointMode.Polygon,
//                brush = Brush.linearGradient(
//                    colors = arrayListOf(
//                        Color.Red,
//                        Color.Green,
//                        Color.Blue
//                    )
//                ),
                // 精准定位
                brush = Brush.linearGradient(
                    0f to Color.Red,
                    0.3f to Color.Green,
                    0.9f to Color.Yellow,
                    1.0f to Color.Black
                ),
                strokeWidth = 30f,
                cap = StrokeCap.Round
            )
        }
    }
}