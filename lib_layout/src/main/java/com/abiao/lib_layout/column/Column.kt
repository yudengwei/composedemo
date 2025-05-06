package com.abiao.lib_layout.column

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abiao.layout.R

@Composable
fun MyColumn(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.size(width = 400.dp, height = 100.dp).background(Color.Blue)
    ) {
        Image(
            contentDescription = "",
            modifier = Modifier.size(150.dp),
            painter = painterResource(com.abiao.common.R.drawable.ic_crane_drawer)
        )
    }
}

@Composable
@Preview
private fun MyColumnDemo(

) {
    MyColumn()
}