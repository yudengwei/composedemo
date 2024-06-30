package com.abiao.animator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class UploadState {
    Normal,
    Start,
    Uploading,
    Success
}

@Composable
fun AnimatorDpBox(
    modifier: Modifier = Modifier
) {
    var isRight by remember { mutableStateOf(false) }

    val startPadding = animateDpAsState(if (isRight) 200.dp else 100.dp, label = "") { endValue ->
        if (endValue == 200.dp) {
            isRight = false
        }
    }
    Box(modifier = modifier) {
        Box(modifier = Modifier
            .padding(start = startPadding.value)
            .size(100.dp)
            .background(Color.Blue)
            .clickable {
                isRight = !isRight
            }
        )
    }
}

@Composable
fun AnimatorUploadBtn(
    modifier: Modifier = Modifier
) {
    val originWidth = 180.dp
    val height = 48.dp
    var textAlpha = 1f
    var backgroundColor = Color.Blue
    var boxWidth = originWidth
    var progressAlpha = 0f
    var progress = 0

    var uploadState by remember { mutableStateOf(UploadState.Normal) }
    var rememberText by remember { mutableStateOf("Upload") }

    when (uploadState) {
        UploadState.Start -> {
            textAlpha = 0f
            backgroundColor = Color.Gray
            boxWidth = height
            progressAlpha = 1f
        }
        UploadState.Uploading -> {
            textAlpha = 0f
            backgroundColor = Color.Gray
            boxWidth = height
            progressAlpha = 1f
            progress = 100
        }
        UploadState.Success -> {
            textAlpha = 1f
            backgroundColor = Color.Red
            boxWidth = originWidth
            progressAlpha = 0f
            progress = 0
        }
        else -> {}
    }

    val animateTextAlpha by animateFloatAsState(targetValue = textAlpha)
    val AnimateBackgroundColor by animateColorAsState(targetValue = backgroundColor)
    val AnimateBoxWidth by animateDpAsState(targetValue = boxWidth) {
        if (uploadState == UploadState.Start) {
            uploadState = UploadState.Uploading
        }
    }
    val AnimateProgressAlpha by animateFloatAsState(targetValue = progressAlpha)
    val AnimateProgress by animateIntAsState(targetValue = progress) {
        if (uploadState == UploadState.Uploading) {
            uploadState = UploadState.Success
            rememberText = "success"
        }
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(height / 2))
                .background(color = AnimateBackgroundColor)
                .size(AnimateBoxWidth, height)
                .clickable {
                    uploadState = UploadState.Start
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(height)
                    .clip(ArcShape(AnimateProgress))
                    .alpha(AnimateProgressAlpha)
                    .background(color = Color.Blue)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .alpha(AnimateProgressAlpha)
                    .background(color = Color.White)
            )
            Text(
                text = rememberText,
                modifier = Modifier
                    .alpha(animateTextAlpha),
                color = Color.White
            )
        }
    }
}