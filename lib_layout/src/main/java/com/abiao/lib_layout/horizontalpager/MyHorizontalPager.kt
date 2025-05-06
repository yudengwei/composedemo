package com.abiao.lib_layout.horizontalpager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.abiao.layout.R
import com.abiao.util.log.Logger
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private val threePager = object: PageSize {

    override fun Density.calculateMainAxisPageSize(availableSpace: Int, pageSpacing: Int): Int {
        return (availableSpace - 2 * pageSpacing) / 1
    }

}

@Composable
fun MyHorizontalPager(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().background(Color.Black).statusBarsPadding().padding(30.dp)
    ) {
        val pager = rememberPagerState(
            pageCount = {
                10
            }
        )
        val fling = PagerDefaults.flingBehavior(
            state = pager,
            // 快速滑动时，最多出现几个
            pagerSnapDistance = PagerSnapDistance.atMost(5)
        )
        HorizontalPager(
            state = pager,
            pageSize = threePager,
            beyondViewportPageCount = 5,
            flingBehavior = fling
        ) {pageIndex ->
            Box(
                modifier = Modifier.background(Color.Red).fillMaxWidth()
                    .graphicsLayer {
                        val pageOffsets = ((pager.currentPage - pageIndex)
                                + pager.currentPageOffsetFraction).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffsets.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Text(
                    "我是第${pageIndex}个",
                    fontSize = 36.sp,
                    color = Color.White
                )
            }

        }

        LaunchedEffect(pager) {
            snapshotFlow { pager.currentPage }.collect {
                Logger.d("pager.currentPage: $it")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        val coroutineScope = rememberCoroutineScope()
        Box(
            modifier = Modifier.fillMaxWidth().wrapContentWidth()
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pager.animateScrollToPage(5)
                    }
                }
            ) {
                Text(
                    "点击滚到第五页",
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pager.pageCount) {index ->
                val color = if (pager.currentPage == index) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(10.dp)
                )
            }
        }
    }
}