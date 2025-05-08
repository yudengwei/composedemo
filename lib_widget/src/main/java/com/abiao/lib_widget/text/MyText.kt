package com.abiao.lib_widget.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextColumn() {
    Column(
        modifier = Modifier.fillMaxSize().wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("abiao")
        Text("abiao", fontStyle = FontStyle.Italic)
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("阿彪")
                }
                append("好帅")
                withStyle(style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    fontSize = 34.sp)) {
                    append("你好啊")
                }
            }
        )

    }
}