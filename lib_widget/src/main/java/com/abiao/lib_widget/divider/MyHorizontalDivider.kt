package com.abiao.lib_widget.divider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyHorizontalDivider(

) {
    Column(
        modifier = Modifier.fillMaxSize().wrapContentSize()
    ) {
        Text("阿彪好帅")
        HorizontalDivider(thickness = 3.dp, color = Color.Red)
        Text("abiao我该你")
    }
}