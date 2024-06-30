package com.abiao.animator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.AnimationVector3D
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val DpSize.Companion.VectorConverter: TwoWayConverter<DpSize, AnimationVector2D>
    get() = DpSizeToVector

private val DpSizeToVector: TwoWayConverter<DpSize, AnimationVector2D> = TwoWayConverter(
    convertFromVector = { DpSize(Dp(it.v1), Dp(it.v2)) },
    convertToVector = { AnimationVector2D(it.width.value, it.height.value) }
)

@Composable
fun animateDpSizeAsState(
    targetValue: DpSize,
    finishListener: ((DpSize) -> Unit)? = null
): State<DpSize> {
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = DpSize.VectorConverter,
        finishedListener = finishListener
    )
}

@Composable
fun MyAnimateSizeBox(
    modifier: Modifier = Modifier
) {
    var isBig by remember { mutableStateOf(false) }
    val myAnimateSize by animateDpSizeAsState(
        targetValue = if (isBig) DpSize(
            100.dp,
            100.dp
        ) else DpSize(50.dp, 50.dp)
    )
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(myAnimateSize)
                .background(color = Color.Blue)
                .clickable {
                    isBig = !isBig
                }
        )
    }
}

data class UploadValue(
    val textAlpha: Float,
    val boxWidth: Dp,
    val progress: Int,
    val progressAlpha: Float
) {
    companion object
}

val UploadValue.Companion.VectorConverter: TwoWayConverter<UploadValue, AnimationVector4D>
    get() = UploadToVector

private val UploadToVector: TwoWayConverter<UploadValue, AnimationVector4D> = TwoWayConverter(
    convertToVector = {
        AnimationVector4D(
            it.textAlpha,
            it.boxWidth.value,
            it.progress.toFloat(),
            it.progressAlpha
        )
    },
    convertFromVector = { UploadValue(it.v1, Dp(it.v2), it.v3.toInt(), it.v4) }
)

@Composable
fun animateUploadAsState(
    targetValue: UploadValue,
    finishListener: ((UploadValue) -> Unit)? = null
): State<UploadValue> {
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = UploadValue.VectorConverter,
        finishedListener = finishListener
    )
}

@Composable
fun MyAnimateUploadBox(
    modifier: Modifier = Modifier
) {
    val originWidth = 180.dp
    val circleValue = 48.dp
    var uploadState by remember { mutableStateOf(UploadState.Normal) }
    var text by remember { mutableStateOf("Upload") }

    val upload = when (uploadState) {
        UploadState.Normal -> {
            UploadValue(1f, originWidth, 0, 0f)
        }

        UploadState.Start -> {
            UploadValue(0f, circleValue, 0, 1f)
        }

        UploadState.Uploading -> {
            UploadValue(0f, circleValue, 100, 1f)
        }

        UploadState.Success -> {
            UploadValue(1f, originWidth, 0, 0f)
        }
    }

    val backgroundColor = when (uploadState) {
        UploadState.Normal -> {
            Color.Blue
        }

        UploadState.Start -> {
            Color.Gray
        }

        UploadState.Uploading -> {
            Color.Gray
        }

        UploadState.Success -> {
            Color.Red
        }
    }

    val animateUpload by animateUploadAsState(upload) {
        if (uploadState == UploadState.Start) {
            uploadState = UploadState.Uploading
        } else if (uploadState == UploadState.Uploading) {
            uploadState = UploadState.Success
            text = "Success"
        }
    }

    val animatorBackgroundColor by animateColorAsState(targetValue = backgroundColor)

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(circleValue))
                .size(animateUpload.boxWidth, circleValue)
                .background(color = animatorBackgroundColor)
                .clickable {
                    uploadState = UploadState.Start
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(circleValue)
                    .clip(ArcShape(animateUpload.progress))
                    .alpha(animateUpload.progressAlpha)
                    .background(color = Color.Blue)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .alpha(animateUpload.progressAlpha)
                    .background(color = Color.White)
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(animateUpload.textAlpha),
                color = Color.White
            )
        }
    }

}
