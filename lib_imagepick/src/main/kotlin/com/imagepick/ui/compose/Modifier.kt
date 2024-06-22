package com.imagepick.ui.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyBox(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /**
         * 得出结论， 如果size在padding之前，那么实际的size会减小padding的值。而如果size在padding之后，那么控件会往对应位置偏移相应的padding值，类似margin效果
         * clickable 如果在padding之前，对整个空间生效，如果在padding之后，只对padding里的区域生效
         */
        Box(
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    Log.d("yudengwei", "click 2")
                }
                .padding(end = 30.dp)
                .background(color = Color.Green)
                .padding(20.dp) // 设置的内边距
                .background(color = Color.Red)
                .clickable {
                    Log.d("yudengwei", "click 1")
                }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color = Color.Green)
                .clickable {
                    Log.d("yudengwei", "click 2")
                }
                .padding(50.dp) // 设置的内边距
                .background(color = Color.Red)

        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .padding(end = 30.dp)
                .size(100.dp)
                .background(color = Color.Green)
                .padding(20.dp) // 设置的内边距
                .background(color = Color.Red)
                .clickable {
                    Log.d("yudengwei", "click 3")
                }
        )
    }
}