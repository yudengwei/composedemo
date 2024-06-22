package com.abiao.demo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abiao.common.base.BaseActivity
import com.abiao.common.theme.ComposeDemoTheme
import com.abiao.common.theme.Pink80
import com.abiao.common.theme.Purple40
import com.abiao.common.theme.Purple80
import com.abiao.demo.first.AlignYourBodyElement
import com.abiao.demo.first.AlignYourBodyRow
import com.abiao.demo.first.FavoriteCollectionCard
import com.abiao.demo.first.FavoriteCollectionsGrid
import com.abiao.demo.first.HomeScreen
import com.abiao.demo.first.HomeSection
import com.abiao.demo.first.MySootheApp
import com.abiao.demo.first.MySootheAppLandscape
import com.abiao.demo.first.MySootheAppPortrait
import com.abiao.demo.first.SearchBar
import com.abiao.demo.first.SootheBottomNavigation
import com.abiao.demo.first.SootheNavigationRail
import com.abiao.demo.second.CircleParameters
import com.abiao.demo.second.CircleParametersDefaults
import com.abiao.demo.second.LineParameters
import com.abiao.demo.second.LineParametersDefaults
import com.abiao.demo.second.TimelineNodePosition

class ManiActivity : BaseActivity() {

    @Composable
    override fun setComposeContent() {
        MySootheApp(calculateWindowSizeClass(activity = this))
    }
}

@Composable
fun TimelineNode(
    position: TimelineNodePosition,
    contentStartOffset: Dp = 50.dp,
    spaceBetweenNodes: Dp = 32.dp,
    circleParameters: CircleParameters,
    lineParameters: LineParameters? = null,
    content: @Composable BoxScope.(modifier : Modifier) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                val circleRadiusInPx = circleParameters.radius.toPx()
                drawCircle(
                    color = circleParameters.backgroundColor,
                    radius = circleRadiusInPx,
                    center = Offset(
                        circleRadiusInPx, circleRadiusInPx
                    )
                )
                lineParameters?.let {
                    drawLine(
                        brush = it.brush,
                        start = Offset(
                            x = circleRadiusInPx, y = circleRadiusInPx * 2,
                        ),
                        end = Offset(
                            x = circleRadiusInPx, y = this.size.height
                        ),
                        strokeWidth = it.strokeWidth.toPx()
                    )
                }
            }
    ) {
        content(
            Modifier
                .padding(
                    start = contentStartOffset,
                    bottom = if (position != TimelineNodePosition.LAST) {
                        spaceBetweenNodes
                    } else {
                        0.dp
                    }
                )
        )
    }
}

@Composable
fun MessageBubble(
    modifier: Modifier,
    containerColor: Color
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun TimelinePreview() {
    ComposeDemoTheme {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TimelineNode(
                position = TimelineNodePosition.FIRST,
                circleParameters = CircleParametersDefaults.circleParameters(
                    backgroundColor = Purple80
                ),
                lineParameters = LineParametersDefaults.linearGradient(
                    startColor = Purple80,
                    endColor = Pink80
                )
            ) {modifier ->
                MessageBubble(
                    modifier = modifier,
                    containerColor = Purple80
                )
            }
            TimelineNode(
                position = TimelineNodePosition.MIDDLE,
                circleParameters = CircleParametersDefaults.circleParameters(
                    backgroundColor = Pink80
                ),
                lineParameters = LineParametersDefaults.linearGradient(
                    startColor = Pink80,
                    endColor = Purple40
                )
            ) {modifier ->
                MessageBubble(
                    modifier = modifier,
                    containerColor = Pink80
                )
            }
            TimelineNode(
                position = TimelineNodePosition.LAST,
                circleParameters = CircleParametersDefaults.circleParameters(
                    backgroundColor = Purple40
                )
            ) {modifier ->
                MessageBubble(
                    modifier = modifier,
                    containerColor = Purple40
                )
            }
        }
    }
}
