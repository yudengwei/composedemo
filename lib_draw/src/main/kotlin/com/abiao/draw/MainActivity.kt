package com.abiao.touch


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

import com.abiao.common.theme.ComposeDemoTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    /**
     * awaitPointEventScope
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
               Box(
                   modifier = Modifier
                       .pointerInput(Unit) {
                           awaitEachGesture {
                               this.awaitPointerEvent()
                           }
                       }
               ) {

               }
            }
        }
    }
}

@Composable
fun TouchBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 100.dp, top = 100.dp)
            .size(200.dp)
            .background(color = Color.Blue)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        Log.d("ABiao", "onDoubleTap")
                    },
                    onLongPress = {
                        Log.d("ABiao", "onLongPress")
                    },
                    onPress = {
                        Log.d("ABiao", "onPress")
                    },
                    onTap = {
                        Log.d("ABiao", "onTap")
                    }
                )
            }
    )
}

@Composable
fun DragBox(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        var topPadding by remember { mutableStateOf(0f) }
        Box(
            modifier = Modifier
                .padding(start = 100.dp, 100.dp)
                .offset { IntOffset(0, topPadding.roundToInt()) }
                .size(100.dp)
                .background(color = Color.Red)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        topPadding += delta
                    }
                )
        )
    }
}

@Composable
fun dragGuest(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        var topPadding by remember { mutableStateOf(0f) }
        var startPadding by remember { mutableStateOf(0f) }
        Box(
            modifier = Modifier
                .padding(start = 100.dp, 100.dp)
                .offset { IntOffset(startPadding.roundToInt(), topPadding.roundToInt()) }
                .size(100.dp)
                .background(color = Color.Red)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        startPadding += dragAmount.x
                        topPadding += dragAmount.y
                    }
                }
        )
    }
}

@Composable

fun SwipeableSample() {
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(96.dp)
            .height(squareSize)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { from, to -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.Red)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

