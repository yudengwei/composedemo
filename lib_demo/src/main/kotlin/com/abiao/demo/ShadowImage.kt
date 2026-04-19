package com.abiao.demo

import android.R.attr.shadowRadius
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SoftShadowImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 33.dp,
) {
    val shape = RoundedCornerShape(cornerRadius)

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .shadowByDrawSoft(
                cornerRadius = cornerRadius,
                shadowRadius = 25.dp,
                offsetY = 20.dp,
            )
            .clip(shape)
    )
}

fun Modifier.shadowByDrawSoft(
    cornerRadius: Dp = 25.dp,
    shadowRadius: Dp = 25.dp,
    offsetY: Dp,
    alpha: Float = 0.35f
) = this.drawBehind {
    drawIntoCanvas {canvas ->
        val offsetX = 20.dp.toPx()
        val paint = Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.TRANSPARENT
            setShadowLayer(
                shadowRadius.toPx(),
                -(offsetX),
                offsetY.toPx(),
                Color.Black.copy(alpha = alpha).toArgb()
            )
        }
        canvas.nativeCanvas.drawRoundRect(
            0f, 0f, size.width + offsetX * 2, size.height,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint
        )
    }
}
