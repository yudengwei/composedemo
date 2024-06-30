package com.abiao.animator

import android.animation.AnimatorSet
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

@Composable
fun UploadButton(
    modifier: Modifier = Modifier,
    duration: Long = 500
) {
    val originWidth = 180.dp
    val height = 48.dp
    var text by remember { mutableStateOf("Upload") }
    val textAlpha = remember {
        mutableStateOf(1.0f)
    }
    val centerAlpha = remember {
        mutableStateOf(0.0f)
    }
    val backgroundColor = remember {
        mutableStateOf(Color.Blue)
    }
    val boxWidth = remember { mutableStateOf(originWidth) }
    val progress = remember { mutableStateOf(0) }

    val endAnimatorSet = remember {
        val animatorSet = AnimatorSet()
        val widthAnimator = animatorOfDb(boxWidth, arrayOf(height, originWidth))
        val centerAnimator = animatorOfFloat(centerAlpha, 1f, 0f)
        val textAnimator = animatorOfFloat(textAlpha, 0f, 1f)
        val colorAnimator = animatorOfColor(backgroundColor, arrayOf(Color.Blue, Color.Red))
        animatorSet.playTogether(widthAnimator, centerAnimator, textAnimator, colorAnimator)
        animatorSet.doOnStart {
            text = "Success"
        }
        animatorSet
    }

    val progressAnimator = remember {
        val animator = animatorOfInt(progress, 0, 100)
        animator.duration = 1000
        animator.doOnEnd {
            endAnimatorSet.start()
        }
        animator
    }

    val uploadStartAnimator = remember {
        val animatorSet = AnimatorSet()
        val widthAnimator = animatorOfDb(boxWidth, arrayOf(originWidth, height))
        val textAlphaAnimator = animatorOfFloat(textAlpha, 1f, 0f)
        val colorAnimator = animatorOfColor(backgroundColor, arrayOf(Color.Blue, Color.Gray))
        val centerCircleAnimator = animatorOfFloat(centerAlpha, 0f, 1.0f)
        animatorSet.playTogether(
            widthAnimator,
            textAlphaAnimator,
            colorAnimator,
            centerCircleAnimator
        )
        animatorSet.duration = duration
        animatorSet.doOnEnd {
            progressAnimator.start()
        }
        animatorSet
    }

    Box(
        modifier = modifier
            .width(originWidth)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(height / 2))
                .background(backgroundColor.value)
                .size(boxWidth.value, height)
                .clickable {
                    uploadStartAnimator.start()
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(height)
                    .clip(ArcShape(progress.value))
                    .alpha(centerAlpha.value)
                    .background(Color.Blue)
            )
            Box(
                modifier = modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .alpha(centerAlpha.value)
                    .background(color = Color.White)
            )
            Text(text = text, color = Color.White, modifier = Modifier.alpha(textAlpha.value))
        }
    }
}

@Composable
fun AnimatorBox() {
    val topPadding = remember {
        mutableStateOf(10)
    }
    val animator = rememberAnimatorOfInt(state = topPadding, 10, 100)
    animator.duration = 500
    Box(modifier = Modifier
        .padding(start = 30.dp, top = topPadding.value.dp)
        .size(100.dp)
        .background(color = Color.Blue)
        .statusBarsPadding()
        .clickable {
            animator.start()
        }
    )
}

@Composable
fun SizeAnimateBox(
    modifier: Modifier = Modifier
) {
    var big by remember {
        mutableStateOf(false)
    }
    val size by animateSizeAsState(targetValue = if (big) Size(200f, 100f) else Size(50f, 50f))

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(size.width.dp, size.height.dp)
                .background(color = Color.Blue)
                .clickable {
                    big = !big
                }
        )
    }
}