package com.abiao.crane.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abiao.crane.R

@Composable
fun FlySearchContent(
    widthSize: WindowWidthSizeClass,
    searchUpdates: FlySearchContentUpdates,
    fromCity: String
) {
    val columns = when (widthSize) {
        // 横屏
        WindowWidthSizeClass.Medium -> 2
        // 竖屏
        WindowWidthSizeClass.Compact -> 1
        // 超大屏幕
        WindowWidthSizeClass.Expanded -> 4
        else -> 1
    }
    CraneSearch(
        columns = columns
    ) {
        item {
            PeopleUserInput(
                onPeopleChanged = searchUpdates.onPeopleChanged
            )
        }
        item {
            CraneUserInput(
                text = fromCity,
                vectorImageId = R.drawable.ic_location
            )
        }
        item {
            ToDestinationUserInput(
                onToDestinationChanged = {

                }
            )
        }
        item {
            DatesUserInput(
                datesSelected = "",
                onDateSelectionClicked = searchUpdates.onDateSelectionClicked
            )
        }
    }
}

@Composable
fun CraneSearch(
    columns: Int,
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 24.dp, top = 0.dp, end = 24.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(count = columns),
        content = content
    )
}