package com.imagepick.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyColumn(

) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.Yellow)
            .fillMaxSize(),
        //verticalArrangement = Arrangement.Bottom
        //verticalArrangement = Arrangement.SpaceAround // 顶部距离x，底部距离x，中间同等间距
        //verticalArrangement = Arrangement.SpaceBetween  // 第一个在最顶部，最后一个在最底部，中间平等间距
        //verticalArrangement = Arrangement.SpaceEvenly, // 同等均分
        verticalArrangement = Arrangement.spacedBy(20.dp), // 设置每个控件的间距
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Red)
                .size(100.dp)
        )
        Box(
            modifier = Modifier
                .background(color = Color.Green)
                .size(200.dp)
        )
        Box(
            modifier = Modifier
                .background(color = Color.Black)
                .size(100.dp)
        )
    }
}