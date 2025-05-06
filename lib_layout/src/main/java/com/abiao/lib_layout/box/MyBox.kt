package com.abiao.lib_layout.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun MyBox(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(Color.Red).fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("阿彪好帅", modifier = Modifier.align(Alignment.TopStart), fontSize = 24.sp, color = Color.White)
        Text("第三个啊", fontSize = 24.sp, color = Color.White)
    }
}

@Composable
@Preview
private fun MyBoxDemo() {
    MyBox()
}