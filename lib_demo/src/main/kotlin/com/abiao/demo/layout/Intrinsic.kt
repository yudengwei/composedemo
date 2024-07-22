package com.abiao.demo.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 本质上就是父布局允许先测量子布局大小来确定自己的大小
 * IntrinsicSize.Min 父布局会先测量子类的大小，将子类的最大值作为自己的大小
 * 自定义layout的话，必须重写对应的方法
 * minIntrinsicHeight -> IntrinsicSize.Min 以此类推
 */

@Composable
fun TwoText(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    // row重写了minIntrinsicHeight以及其他三个方法，所以能用height(intrinsicSize = IntrinsicSize.Min)的方式
    // 如果没重写，会报错导致crash
    Row(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
    ) {
        Text(
            text = text1,
            fontSize = 30.sp,
            color = Color.Red,
            modifier = Modifier
                .weight(1f)
                .size(width = 100.dp, height = 300.dp)
                .background(color = Color.Green)
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(color = Color.Blue)
        )
        Text(
            text = text2,
            fontSize = 30.sp,
            color = Color.Red,
            modifier = Modifier
                .weight(1f)
                .size(width = 100.dp, height = 200.dp)
                .background(color = Color.Black)
        )
    }
}

@Composable
fun IntrinsicRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = object : MeasurePolicy{
            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                val dividerConstraints = constraints.copy(minWidth = 0)
                val mainPlaceables = measurables.filter {
                    it.layoutId == "main"
                }.map {
                    it.measure(constraints)
                }
                val dividerPlaceable = measurables.first { it.layoutId == "divider" }.measure(dividerConstraints)
                val minPosition = constraints.maxWidth / 2
                return layout(constraints.maxWidth, constraints.maxHeight) {
                    mainPlaceables.forEach {
                        it.placeRelative(0, 0)
                    }
                    dividerPlaceable.placeRelative(minPosition, 0)
                }
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int {
                var maxHeight = 0
                measurables.forEach {
                    maxHeight = it.minIntrinsicHeight(width).coerceAtLeast(maxHeight)
                }
                return maxHeight
            }


        }
    )
}

@Composable
fun SubComposeRow(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    divider: @Composable  (Int) -> Unit
) {
    SubcomposeLayout(modifier = modifier) {constraints ->
        var maxHeight = 0
        var placeables = subcompose("text", text).map {
            val placeable = it.measure(constraints)
            maxHeight = placeable.height.coerceAtLeast(maxHeight)
            placeable
        }
        val dividerPlaceable = subcompose("divider") {
            divider(maxHeight)
        }.map {
            it.measure(constraints.copy(minWidth = 0))
        }
        val minPosition = constraints.maxWidth / 2
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach {
                it.placeRelative(0, 0)
            }
            dividerPlaceable.forEach {
                it.placeRelative(minPosition, 0)
            }
        }
    }
}