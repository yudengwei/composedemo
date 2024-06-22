package com.abiao.crane.compose.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abiao.crane.R
import java.util.Locale

@Composable
fun CraneTabBar(
    openDrawer: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 20.dp)
                .clickable {
                    openDrawer()
                },
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null
        )
        CraneTabs(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            titles = listOf("阿标", "与等为", "月夜的王子")
        )
    }
}

@Composable
fun CraneTabs(
    modifier: Modifier = Modifier,
    titles: List<String>
) {
    TabRow(
        modifier = Modifier
            .fillMaxHeight(),
        selectedTabIndex = 1,
        indicator = { tabPositions: List<TabPosition> ->
            Box(
                modifier = modifier
                    .tabIndicatorOffset(currentTabPosition = tabPositions[1])
                    .fillMaxSize()
                    .padding(horizontal = 4.dp)
                    .border(border = BorderStroke(2.dp, Color.White), shape = RoundedCornerShape(16.dp))
            )
        },
        divider = {}
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(16.dp)),
                selected = false,
                onClick = {  }
            ) {
                Text(
                    modifier = Modifier
                        ,
                    text = title.uppercase(Locale.getDefault()),
                    maxLines = 1
                )
            }
        }
    }
}