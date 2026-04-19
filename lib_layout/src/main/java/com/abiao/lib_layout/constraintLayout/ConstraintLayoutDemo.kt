package com.abiao.lib_layout.constraintLayout

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

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

@Composable
fun BasicConstraint(

) {
    ConstraintLayout(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.LightGray.copy(alpha = 0.1f))
    ) {
        val (left, middle, right) = createRefs()

        Button(
            onClick = {

            },
            modifier = Modifier
                .constrainAs(right) {
                    end.linkTo(parent.end, margin = 20.dp)
                }
        ) {
            Text(text = "click me")
        }

        Text(
            text = "I am title",
            modifier = Modifier
                .constrainAs(left) {
                    start.linkTo(parent.start, margin = 20.dp)
                }
        )

        Text(
            text = "I am middle",
            modifier = Modifier
                .constrainAs(middle) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

/**
 * ChainStyle.Spread：组件均匀分布（默认）；
 * ChainStyle.SpreadInside：首尾贴边，中间均匀；
 * ChainStyle.Packed：组件紧凑排列（可通过 margin 调整间距）。
 */
@Composable
fun ChainConstraintDemo(

) {
    ConstraintLayout(
        modifier = Modifier.statusBarsPadding().fillMaxWidth().height(80.dp)
    ) {
        val (btn1, btn2, btn3) = createRefs()

        createHorizontalChain(btn1, btn2, btn3,
             chainStyle = ChainStyle.SpreadInside
        )

        Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(btn1) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        ) {
            Text(text = "阿标")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(btn2) {
                    top.linkTo(btn1.top)
                }
        ) {
            Text(text = "阿标2")
        }

        Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(btn3) {
                    top.linkTo(btn1.top)
                }
        ) {
            Text(text = "阿标3")
        }
    }
}

@Composable
fun AspectRatioConstraintDemo(

) {
    ConstraintLayout(
        Modifier.statusBarsPadding().fillMaxWidth().padding(16.dp)
    ) {
        val (image) = createRefs()

        Box(
            modifier = Modifier
                .height(100.dp)
                .aspectRatio(16f / 9f, matchHeightConstraintsFirst = true) // 优先适配高度
                .constrainAs(image) {
                    top.linkTo(parent.top)
                }
                .background(Color.Gray)
        ) {
            Text(
                text = "16:9图片占位",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun BarrierConstraintDemo(

) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        val (text1, text2, btnRef) = createRefs()
        val actionStartLimit = createGuidelineFromEnd(112.dp)

        Text(
            text = "I am sort",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(
                    text1
                ) {
                    start.linkTo(parent.start)
                    end.linkTo(actionStartLimit)
                    top.linkTo(parent.top)
                    width = Dimension.preferredWrapContent
                }
        )
        Text(
            text = "I am very longLLLLLLL text, and I should ellipsize before the action",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .constrainAs(
                    text2
                ) {
                    start.linkTo(text1.start)
                    end.linkTo(actionStartLimit)
                    top.linkTo(text1.bottom, margin = 20.dp)
                    width = Dimension.preferredWrapContent
                }
        )

        val barrier = createEndBarrier(text1, text2)

        Button(
            onClick = {

            },
            modifier = Modifier
                .constrainAs(btnRef) {
                    start.linkTo(barrier, margin = 8.dp)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.preferredWrapContent
                    horizontalBias = 0f
                }
        ) {
            Text("按钮")
        }
    }
}

@Composable
fun GuidelineConstraintDemo(

) {
    ConstraintLayout(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        val (a) = createRefs()

        val verticalGuideline = createGuidelineFromStart(fraction = 1f / 3f)
        val horizontalGuideline = createGuidelineFromTop(offset = 50.dp)

        Button(
            onClick = {},
            modifier = Modifier
                .constrainAs(a) {
                    end.linkTo(verticalGuideline)
                    top.linkTo(horizontalGuideline)
                }
        ) {
            Text(text = "button")
        }
    }
}

@Composable
fun FormLayout(

) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (text1, input1) = createRefs()

        Text(
            text = "姓名",
            modifier = Modifier
                .constrainAs(text1) {
                    start.linkTo(parent.start, )
                    centerVerticallyTo(parent)
                }
        )
        Box(
            modifier = Modifier
                .constrainAs(input1) {
                    start.linkTo(text1.end,)
                    end.linkTo(parent.end, )
                    top.linkTo(text1.top)
                    // 只有加了这个才能铺满全部
                    width = Dimension.fillToConstraints
                }
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "请输入姓名")
        }
    }
}

@Composable
fun CutCornerShapeDemo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = Color.Red,
                    shape = CutCornerShape(12.dp)
                )
        )
    }
}
