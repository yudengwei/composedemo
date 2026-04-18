package com.abiao.lib_layout.constraintLayout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun ConstraintLayDemo(

) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val (button, text) = createRefs()
        Button(
            onClick = {

            },
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        ) {
            Text(text = "Android")
        }
        Text(
            text = "I am Text",
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(button.bottom, margin = 16.dp)
                    start.linkTo(button.start)
                }
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DecoupledConstraintLayout(

) {
    var change by remember { mutableStateOf(false) }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val constraints = if (change) {
            decoupledConstraints(margin = 16.dp)
        } else {
            decoupledConstraints(margin = 32.dp)
        }
        ConstraintLayout(constraints) {
            Button(
                onClick = {
                    change = !change
                },
                modifier = Modifier
                    .layoutId("button")
            ) {
                Text("Button")
            }
            Text(text = "I am Text", modifier = Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")
        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}