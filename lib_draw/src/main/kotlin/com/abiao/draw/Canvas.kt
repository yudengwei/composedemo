package com.abiao.draw

import android.graphics.Bitmap
import androidx.annotation.FloatRange
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.scale
import androidx.core.graphics.transform
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

private const val defaultAmlitude = 0.2f
private const val defaultVelocity = 1.0f
private const val waveDuration = 2000

data class WaveConfig(
    @FloatRange(from = 0.0, to = 1.0) val progress: Float = 0f,
    @FloatRange(from = 0.0, to = 1.0) val amplitude: Float = defaultAmlitude,
    @FloatRange(from = 0.0, to = 1.0) val velocity: Float = defaultVelocity
)

@Composable
fun Circle() {
    var sweepAngle by remember {
        mutableStateOf(162f)
    }
    Box(
        modifier = Modifier
            .size(372.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Text(text = "45%",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            drawCircle(
                color = Color(0xFF1E7171),
                center = Offset(drawContext.size.width / 2f, drawContext.size.height / 2f),
                style = Stroke(width = 20.dp.toPx())
            )
            drawArc(
                color = Color(0xFF3B3DCCE),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
fun DrawBefore(

) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(100.dp)
                .drawWithContent {
                    drawCircle(
                        Color(0xffe7614e),
                        18.dp.toPx() / 2,
                        center = Offset(drawContext.size.width, 0f)
                    )
                    drawContent()

                }
        ) {
        }
    }
}

@Preview
@Composable
fun DrawAfter(

) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(100.dp)
                .drawBehind {
                    drawCircle(
                        Color(0xffe7614e),
                        18.dp.toPx() / 2,
                        center = Offset(drawContext.size.width, 0f)
                    )
                }
        ) {
        }
    }
}

@Composable
fun WaveLoading(
    modifier: Modifier = Modifier,
    waveConfig: WaveConfig,
    bitmap: Bitmap
) {
    val transition = rememberInfiniteTransition("aaa")
    val animates = listOf(
        1f
    ).map {
        transition.animateFloat(
            initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
                animation = tween((it * waveDuration).roundToInt()),
                repeatMode = RepeatMode.Restart
            ),
            label = "abiao"
        )
    }
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawWave(
            imageBitmap = bitmap.asImageBitmap(),
            waveConfig = waveConfig,
            animates = animates)
    }
}

private fun DrawScope.drawWave(
    imageBitmap: ImageBitmap,
    waveConfig: WaveConfig,
    animates: List<State<Float>>,
) {
    drawImage(image = imageBitmap, colorFilter = run {
        val cm = ColorMatrix().apply { setToSaturation(0f) }
        ColorFilter.colorMatrix(cm)
    })

    animates.forEachIndexed { index, anim ->

        val maxWidth = 2 * size.width / waveConfig.velocity.coerceAtLeast(0.1f)
        val offsetX = maxWidth / 2 * (1 - anim.value)

        translate(-offsetX) {
            drawPath(
                path = buildWavePath(
                    width = maxWidth,
                    height = size.height,
                    amplitude = size.height * waveConfig.amplitude,
                    progress = waveConfig.progress
                ), brush = ShaderBrush(ImageShader(imageBitmap).apply {
                    transform { postTranslate(offsetX, 0f) }
                }), alpha = if (index == 0) 1f else 0.5f
            )
        }

    }
}

private fun buildWavePath(
    dp: Float = 3f,
    width: Float,
    height: Float,
    amplitude: Float,
    progress: Float
): Path {

    //调整振幅，振幅不大于剩余空间
    var adjustHeight = min(height * Math.max(0f, 1 - progress), amplitude)

    return Path().apply {
        reset()
        moveTo(0f, height)
        lineTo(0f, height * (1 - progress))
        if (progress > 0f && progress < 1f) {
            if (adjustHeight > 0) {
                var x = dp
                while (x < width) {
                    lineTo(
                        x,
                        height * (1 - progress) - adjustHeight / 2f * sin(4.0 * Math.PI * x / width)
                            .toFloat()
                    )
                    x += dp
                }
            }
        }
        lineTo(width, height * (1 - progress))
        lineTo(width, height)
        close()
    }
}

@Composable
fun WaveDemo() {
}

@Composable
private fun LabelSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>
) {
    Row(Modifier.padding(start = 10.dp, end = 10.dp)) {
        Text(
            label, modifier = Modifier
                .width(100.dp)
                .align(Alignment.CenterVertically)
        )
        Slider(
            modifier = Modifier.align(Alignment.CenterVertically),
            value = value,
            onValueChange = onValueChange,
            valueRange = range
        )
    }
}
