package com.abiao.lib_widget.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyLazyColumn(

) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        item {
            Text("abiao1")
        }
        items(5) {
            Text("abiao$it")
        }
        item {
            Text("abiao7")
        }
    }
}

@Composable
fun MyLazyGrid(

) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(5) {
            Text("abiao$it")
        }
    }
}