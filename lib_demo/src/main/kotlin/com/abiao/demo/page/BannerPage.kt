package com.abiao.demo.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abiao.demo.R
import com.abiao.demo.SoftShadowImage
import com.abiao.demo.shadowByDrawSoft
import kotlinx.coroutines.launch

private val IMAGE_LIST = listOf(
    R.drawable.ic_robot1,
    R.drawable.ic_robot2,
    R.drawable.ic_robot3
)

@Composable
fun BannerPage(
    imageList: List<Int> = IMAGE_LIST
) {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFF7F8FA))
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Skip",
                color = Color(0xFFD7D7D7),
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.End).padding(end = 45.dp)
            )
            val pagerState = rememberPagerState(
                pageCount = {
                    imageList.size
                }
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SoftShadowImage(
                        painter = painterResource(imageList[page]),
                    )
                }
            }
            DotIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                selectIndex = pagerState.currentPage,
                count = imageList.size
            )

            Text(
                text = "Unlock the Power Of Future AI",
                color= Color(0xFF23262F),
                fontSize = 33.sp,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "Chat with the smartest AI Future Experience power of AI with us",
                color= Color(0xFF8E9295),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 40.dp)
            )

            val scope = rememberCoroutineScope()
            ArrowSwitcher(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                canGoNext = pagerState.canScrollForward,
                canGoPrevious =  pagerState.canScrollBackward,
                onPreviousClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onNextClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                })
        }
    }
}

@Composable
private fun DotIndicator(
    modifier: Modifier = Modifier,
    count: Int = 3,
    selectIndex: Int = 0,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(count) {index ->
            if (index == selectIndex) {
                SelectedCircle()
            } else {
                UnSelectedCircle()
            }
        }
    }
}

@Composable
private fun SelectedCircle() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .border(
                width = 1.dp,
                color = Color(0XFF141718),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = Color(0XFF141718),
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun UnSelectedCircle() {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(
                color = Color(0xFF23262F).copy(alpha = 0.5f),
                shape = CircleShape
            )
    )
}

@Composable
fun ArrowSwitcher(
    modifier: Modifier = Modifier,
    canGoPrevious: Boolean,
    canGoNext: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFF4F4F6),
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .width(180.dp)
                .height(72.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArrowButton(
                modifier = Modifier.weight(1f),
                enabled = canGoPrevious,
                onClick = onPreviousClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Previous",
                        tint = if (canGoPrevious) Color(0xFF222222) else Color(0xFFB8BBC4)
                    )
                }
            )

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(48.dp)
                    .background(Color(0xFFE1E2E6))
            )

            ArrowButton(
                modifier = Modifier.weight(1f),
                enabled = canGoNext,
                onClick = onNextClick,
                content = {
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = "Next",
                        tint = if (canGoNext) Color(0xFF222222) else Color(0xFFB8BBC4)
                    )
                }
            )
        }
    }
}

@Composable
private fun ArrowButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(24.dp))
            .clickable(
                enabled = enabled,
                indication = null, // 不要水波纹的话保留这句
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center,
        content = content
    )
}