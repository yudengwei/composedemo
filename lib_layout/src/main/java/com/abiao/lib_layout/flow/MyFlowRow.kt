package com.abiao.lib_layout.flow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyFlowRow(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.statusBarsPadding().padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items.forEach {
            FlagView(it)
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
private fun FlagView(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Red)
            .size(width = 100.dp, height = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

@Composable
fun FlowRow1(
    modifier: Modifier = Modifier
) {
    val row = 3
    val column = 3
    FlowRow(
        modifier = modifier.statusBarsPadding().padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        maxItemsInEachRow = row
    ) {
        val itemModifier = Modifier
            .padding(4.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Blue)
        repeat(row * column) {
            if ((it + 1) % 3 == 0) {
                Spacer(modifier = itemModifier.fillMaxWidth())
            } else {
                Spacer(modifier = itemModifier.weight(1f))
            }
        }
    }
}