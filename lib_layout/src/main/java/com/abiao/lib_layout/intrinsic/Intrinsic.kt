package com.abiao.lib_layout.intrinsic

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * IntrinsicSize.Min 还没开始绘制，此时VerticalDivider获取不到高度
 */
@Composable
@Preview(showBackground = true)
fun RowPrew(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.weight(1f).padding(5.dp).wrapContentWidth(Alignment.Start),
            text = "阿彪好帅"
        )
        VerticalDivider(
            color = Color.Red,
            modifier = Modifier.width(1.dp)
        )
        Text(
            modifier = Modifier.weight(1f).padding(5.dp).wrapContentWidth(Alignment.End),
            text = "阿彪好帅啊"
        )
    }
}