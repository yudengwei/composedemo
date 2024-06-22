package com.abiao.animator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.unit.sp
import com.abiao.common.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                MyVisibility()
            }
        }
    }
}

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
            enter = fadeIn(animationSpec = tween(5000)),
            exit = fadeOut(animationSpec = tween(5000))
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
                        .animateEnterExit(
                            enter = slideInVertically(animationSpec = tween(5000)),
                            exit = slideOutVertically()
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_crane_drawer),
                        contentDescription = null)
                }
            }

        }
    }
}