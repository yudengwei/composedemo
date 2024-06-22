package com.imagepick.ui.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyAnnotatedString(
    
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SelectionContainer {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                val annotatedString = buildAnnotatedString {
                    append("通过")
                    withStyle(style = SpanStyle(color = Color.Red)){
                        append("AnnotatedString")
                    }
                    append("设置富文本效果")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Blue)){
                        append("点击跳转www.baidu.com")
                    }
                    addStringAnnotation(
                        tag = "URL",
                        annotation = "https://www.baidu.com",
                        start = 25,
                        end = 41
                    )
                }
                Text(
                    modifier = Modifier.clickable {
                        annotatedString.getStringAnnotations(
                            tag = "URL",
                            start = 0,
                            end = 25
                        ).firstOrNull()?.let { annotation ->
                            Log.d("yudengwei", "click annotation is $annotation")
                        } ?: run {
                            Log.d("yudengwei", "no annotation")
                        }
                    },
                    text = annotatedString,
                    fontSize = 30.sp,
                    color = Color.Blue
                )
                ClickableText(text = annotatedString,
                    style = TextStyle(fontSize = 30.sp, color = Color.Blue)) { offset ->
                    Log.d("yudengwei", "offset: $offset")
                    annotatedString.getStringAnnotations(
                        tag = "URL",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let { annotation ->
                        Log.d("yudengwei", "click annotation is $annotation")
                    } ?: run {
                        Log.d("yudengwei", "no annotation")
                    }
                }
                Text(text = "第二个")
                Text(text = "第三个")
                // 不可选中
                DisableSelection {
                    Text(text = "第4个")
                    Text(text = "第5个")

                }
                Text(text = "第6个")
                Text(text = "第7个")
            }
        }
    }
}