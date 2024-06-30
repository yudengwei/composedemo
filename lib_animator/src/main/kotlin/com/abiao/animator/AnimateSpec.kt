package com.abiao.animator

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

/**
 * TweenSpec 设置时长，延迟时间，插值器

 * SnapSpec 瞬时动画

 * keyframes 定义在某个时间区间内的动画 150.dp at 3000 using FastOutSlowInEasing

 * SpringSpec 弹簧动画，回弹效果
        dampingRatio：弹簧阻尼比，数值越小震荡的次数越多
        dampingRatio = 0：无阻尼，弹簧处于永远震荡的状态
        dampingRatio < 1：欠阻尼，弹簧进行指数递减的震荡运动
        dampingRatio = 1：临界阻尼，弹簧以最短时间结束运动，无震荡运动
        dampingRatio > 1：过阻尼，弹簧进行无震荡的减速运动

 * stiffness：弹簧刚度，刚度越大弹簧回到原点的速度越快，即动画运行得越快

 * visibilityThreshold：可视阈值，即当弹簧弹到某一个值的时候就不弹了然后直接运动到目标值

 * RepeatableSpec 重复动画
        iterations 重复次数
        animation2 真正实现动画的效果，是DurationBasedAnimationSpec的子类，只能TweenSpec SnapSpec keyframes这三个
        repeatMode 重复模式 Restart 重新播放  反向播放

 * InfiniteRepeatableSpec 无限重复动画，用法和RepeatableSpec基本相同，就是少了一个iterations参数
 * */

@Composable
fun SnapBox(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        var move by remember {
            mutableStateOf(false)
        }
        val offset by animateOffsetAsState(
            targetValue = if (move) Offset(50f, 100f) else Offset(0f, 0f),
            // 加了这个以后动画瞬时完成
            animationSpec = snap()
        )
        Box(
            modifier = Modifier
                .offset(offset.x.dp, offset.y.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Red)
                .clickable {
                    move = !move
                }
        )
    }
}

@Composable
fun KeyFrameBox(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        var move by remember {
            mutableStateOf(false)
        }
        val offset by animateOffsetAsState(
            targetValue = if (move) Offset(50f, 100f) else Offset(0f, 0f),
            animationSpec = keyframes {
                durationMillis = 3000
                // 0-1000ms内的target是Offset(200f, 200f)
                Offset(200f, 200f).at(1000)
                // 1000-2000内是从Offset(200f, 200f) 到Offset(10f, 50f)
                Offset(-50f, -50f) at 2000 using FastOutSlowInEasing
                // 最后再从Offset(10f, 50f) 到 Offset(50f, 100f)
            }
        )
        Box(
            modifier = Modifier
                .offset(offset.x.dp, offset.y.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = Color.Red)
                .clickable {
                    move = !move
                }
        )
    }
}

@Composable
fun SpringBox(
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val points = remember { mutableListOf<DpOffset>() }
    val ballSize = 50.dp
    val targetPosition = 300.dp
    val targetLineHeight = 3.dp

    var moveToRight by remember { mutableStateOf(false) }
    val targetValue = if (moveToRight) targetPosition else 10.dp
    val topPadding by animateDpAsState(
        targetValue = targetValue,
        // 默认构造函数是没有效果的
        //animationSpec = spring()
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )
    var leftPadding by remember { mutableStateOf(10.dp) }
    val leftPaddingValue = remember(topPadding) {
        leftPadding += 1.dp
        points.add(DpOffset(leftPadding, topPadding))
        leftPadding
    }
    Box(modifier) {
        // target位置线条标记
        Box(
            modifier = Modifier
                .padding(top = targetPosition + ballSize / 2 - targetLineHeight / 2)
                .size(screenWidth, targetLineHeight)
                .background(color = Color.Green)
        )
        // 小球运动轨迹
        Box(
            modifier = Modifier
                .size(screenWidth, screenHeight)
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val path = Path()
                path.moveTo(10.dp.toPx(), 10.dp.toPx() + (ballSize / 2f).toPx())
                if (points.isNotEmpty()) {
                    points.forEach {
                        path.lineTo(it.x.toPx(), it.y.toPx() + (ballSize / 2).toPx())
                    }
                    drawPath(
                        path = path,
                        color = Color.Red,
                        style = Stroke(
                            width = targetLineHeight.toPx()
                        )
                    )
                }
            }
        }
        // 小球
        Box(
            modifier = Modifier
                .padding(start = leftPaddingValue, top = topPadding)
                .size(ballSize, ballSize)
                .clip(RoundedCornerShape(25.dp))
                .background(Color.Blue)
                .clickable {
                    leftPadding = 10.dp
                    points.clear()
                    moveToRight = true
                }
        )
    }
}

@Composable
fun RepeatableSpecBox(
    modifier: Modifier = Modifier
) {
    var moveToRight by remember { mutableStateOf(false) }
    val animatePaddingLeft by animateDpAsState(
        targetValue = if (moveToRight) 100.dp else 20.dp,
        animationSpec = repeatable(
            iterations = 5,
            animation = tween(3000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(start = animatePaddingLeft)
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.Red)
                .clickable {
                    moveToRight = !moveToRight
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "tween")
        }
    }
}