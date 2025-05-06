package com.abiao.lib_layout.verticalpager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MyVerticalPager(
    modifier: Modifier = Modifier
) {
    val pager = rememberPagerState(
        pageCount = {
            10
        }
    )
    VerticalPager(
        state = pager,
        modifier = modifier.fillMaxSize()
    ) { pagerCount ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Text("阿彪好帅: $pagerCount", fontSize = 24.sp)
        }
    }
}