package com.abiao.lib_layout.horizontalpager

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abiao.util.log.Logger
import kotlinx.coroutines.delay

@Composable
fun AutoAdvancePager(
    pageItems: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(pageCount = {
            pageItems.size
        })
        val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
        val pagerInteSource = remember {
            MutableInteractionSource()
        }
        val pagerIsPressed by pagerInteSource.collectIsPressedAsState()
        val autoAdvance = !pagerIsDragged && !pagerIsPressed
        if (autoAdvance) {
            LaunchedEffect(pagerState, pagerInteSource) {
                while (true) {
                    delay(2000)
                    val nextPage = (pagerState.currentPage + 1) % pageItems.size
                    if (nextPage == 0) {
                        pagerState.scrollToPage(0)
                    } else {
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            Text(
                text = "this is ${pageIndex}",
                modifier = Modifier.fillMaxSize()
                    .background(pageItems[pageIndex])
                    .clickable(
                        interactionSource = pagerInteSource,
                        indication = LocalIndication.current
                    ) {
                        Logger.d("点击了第${pageIndex}个")
                    }
                    .wrapContentSize()
            )
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageItems.size) {index ->
                val color = if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier.padding(20.dp).clip(CircleShape).background(color).size(10.dp)
                )
            }
        }
    }
}