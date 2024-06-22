package com.abiao.crane.compose.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abiao.crane.compose.common.CraneTabBar
import com.abiao.crane.compose.common.CraneTabs
import com.abiao.crane.data.ExploreModel
import com.abiao.crane.viewmodel.MainViewModel

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
    Fly, Sleep, Eat
}

@Composable
fun CraneHomeContent(
    onExploreItemClicked: OnExploreItemClicked,
    onDateSelectionClicked: () -> Unit,
    openDrawer: () -> Unit,
) {

}

@Composable
fun HomeTabBar(
    modifier: Modifier,
    tabSelected : CraneScreen,
    onTabSelected: (CraneScreen) -> Unit,
) {

}