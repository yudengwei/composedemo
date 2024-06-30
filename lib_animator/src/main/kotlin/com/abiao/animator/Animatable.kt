package com.abiao.animator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Animatable 最底层的api initialValue 初始值

 * animateTo(targetValue, animationSpec, initialVelocity, block) -> 开始动画,
 * targetValue：动画目标值
 * animationSpec：动画规格配置，这个前面几篇文件进行了详细介绍，共有 6 种动画规格可进行设置
 * initialVelocity：初始速度
 * block：函数类型参数，动画运行的每一帧都会回调这个 block 方法，可用于动画监听

 * snapTo(targetValue) -> 动画瞬间完成

 * animateDecay 衰减动画，结束时会有惯性，过一会才停止
 */

@Composable
fun AnimatableBox(
    modifier: Modifier = Modifier
) {
    val initPaddingStart = 0.dp
    var moveRight by remember { mutableStateOf(false) }
    val animatablePadding = remember {
        Animatable(initPaddingStart, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = moveRight) {
        animatablePadding.animateTo(if (moveRight) 100.dp else initPaddingStart)
    }
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(start = animatablePadding.value)
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Red)
                .clickable {
                    moveRight = !moveRight
                }
        )
    }
}

@Composable
fun DecayBox(
    modifier: Modifier = Modifier
) {
    val initPaddingStart = 0.dp
    val animatablePadding = remember {
        Animatable(initPaddingStart, Dp.VectorConverter)

    }
    val splineDecay = splineBasedDecay<Dp>(LocalDensity.current)
    val splineDecay1 = rememberSplineBasedDecay<Dp>()
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(start = animatablePadding.value)
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Red)
                .clickable {
                    scope.launch {
                        animatablePadding.animateDecay(2000.dp, splineDecay1)
                    }
                }
        )
    }
}