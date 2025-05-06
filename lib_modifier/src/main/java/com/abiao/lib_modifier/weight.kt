package com.abiao.lib_modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyWeight(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize().statusBarsPadding().padding(20.dp)
    ) {
        Box(
            modifier = Modifier.weight(2f).background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Text("阿彪好帅")
        }
        Box(
            modifier = Modifier.weight(1f).background(Color.Red).wrapContentSize(),
            contentAlignment = Alignment.TopStart
        ) {
            Text("阿彪哥哥", modifier = Modifier.background(Color.Black).wrapContentSize(), color = Color.White)
        }

    }
}