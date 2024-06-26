package com.abiao.animator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MyVisibility() {
    // initialState = false , targetState = true, 这样就能一进入界面就执行动画
    //val state = remember { MutableTransitionState(initialState = false) }
    var visible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { visible = true }) {
            Text(text = "show Animator")
        }
        Button(onClick = { visible = false }) {
            Text(text = "hide Animator")
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            val background by transition.animateColor(label = "") { state ->
                when (state) {
                    EnterExitState.Visible -> {
                        Color.Blue
                    }

                    EnterExitState.PostExit -> {
                        Color.Red
                    }

                    else -> {
                        Color.Green
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = background)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_crane_drawer),
                        contentDescription = null
                    )
                }
            }

        }
    }
}