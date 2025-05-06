package com.abiao.lib_layout.customlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 与baseLine的top距离
 */
fun Modifier.firstBaseLineToTop(
    firstTop: Dp
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseLine = placeable[FirstBaseline]
    val placeableY = firstTop.roundToPx() - firstBaseLine
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        placeable.placeRelative(0, placeableY)
    }
}

@Composable
fun FirstBaseLineTopDemo(
    firstTop: Dp
) {
    Row(
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(30.dp).background(Color.Red)
    ) {
        Text(
            "阿彪好帅",
            modifier = Modifier.paddingFromBaseline(firstTop)
        )
        Text(
            "阿彪好帅",
            modifier = Modifier.firstBaseLineToTop(firstTop)
        )
    }
}

@Composable
private fun MyCustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) {measurables, constraints ->
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyCustomColumnDemo(
    modifier: Modifier = Modifier
) {
    MyCustomColumn {
        Text("阿彪好帅")
        Text("阿彪好帅1")
        Text("阿彪好帅2")
        Text("阿彪好帅3")
        Text("阿彪好帅4")
    }
}