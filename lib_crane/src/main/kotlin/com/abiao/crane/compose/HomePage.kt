package com.abiao.crane.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abiao.crane.R
import com.abiao.crane.activity.HomeDraw
import com.abiao.crane.data.ExploreModel
import com.abiao.crane.ui.BottomSheetShape
import com.abiao.crane.viewmodel.MainViewModel
import kotlinx.coroutines.launch

private const val TAB_SWITCH_ANIM_DURATION = 300

typealias OnExploreItemClicked = (ExploreModel) -> Unit

@Composable
fun CraneHome(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    widthSize: WindowWidthSizeClass,
    onDateSelectionClicked: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        drawerContent = {
            HomeDraw()
        }
    ) {contentPadding ->
        val scope = rememberCoroutineScope()
        CraneHomeContent(
            modifier = modifier.padding(contentPadding),
            viewModel = viewModel,
            openDrawer = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            },
            widthSize = widthSize,
            onDateSelectionClicked = onDateSelectionClicked
        )
    }
}

@Composable
fun CraneHomeContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    openDrawer: () -> Unit,
    widthSize: WindowWidthSizeClass,
    onDateSelectionClicked: () -> Unit
) {
    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()
    val craneScreenValues = CraneScreen.entries.toTypedArray()
    val pagerState =
        rememberPagerState(initialPage = CraneScreen.Fly.ordinal) { craneScreenValues.size }
    val coroutineScope = rememberCoroutineScope()
    var fromCity by remember {
        mutableStateOf(
            if (suggestedDestinations.isNullOrEmpty()) ""
            else suggestedDestinations!!.get(0).city.nameToDisplay
        )
    }
    BackdropScaffold(
        modifier = modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        frontLayerShape = BottomSheetShape,
        frontLayerScrimColor = Color.Unspecified,
        appBar = {
            HomeTabBar(
                openDrawer = openDrawer,
                tabSelected = craneScreenValues[pagerState.currentPage],
                onTabSelected = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = it.ordinal,
                            animationSpec = tween(durationMillis = TAB_SWITCH_ANIM_DURATION)
                        )
                    }
                }
            )
        },
        backLayerContent = {
            SearchContent(
                modifier = Modifier.padding(top = 10.dp),
                tabSelected = craneScreenValues[pagerState.currentPage],
                widthSize = widthSize,
                onPeopleChanged = {
                    viewModel.updatePeople(it)
                },
                fromCity = fromCity,
                onDateSelectionClicked = onDateSelectionClicked
            )
        },
        frontLayerContent = {
            HorizontalPager(state = pagerState) { page ->
                when (craneScreenValues[page]) {
                    CraneScreen.Fly -> {
                        suggestedDestinations?.let { destinations ->
                            ExploreSection(
                                widthSize = widthSize,
                                title = stringResource(R.string.explore_flights_by_destination),
                                exploreList = destinations,
                                itemCLick = {
                                    fromCity = it.city.nameToDisplay
                                }
                            )
                        }
                    }
                    CraneScreen.Sleep -> {

                    }
                    CraneScreen.Eat -> {

                    }
                }
            }
        }
    )
}


@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    tabSelected: CraneScreen,
    widthSize: WindowWidthSizeClass,
    onPeopleChanged: (Int) -> Unit,
    fromCity: String,
    onDateSelectionClicked: () -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = tabSelected,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(durationMillis = TAB_SWITCH_ANIM_DURATION, easing = EaseIn)
            ).togetherWith(
                fadeOut(
                    animationSpec = tween(durationMillis = TAB_SWITCH_ANIM_DURATION, easing = EaseOut)
                )
            ).using(
                SizeTransform(
                    sizeAnimationSpec = {_, _ ->
                        tween(durationMillis = TAB_SWITCH_ANIM_DURATION, easing = EaseOut)
                    }
                )
            )
        },
        label = "SearchContent"
    ) { targetState ->
        when (targetState) {
            CraneScreen.Fly -> {
                FlySearchContent(
                    widthSize = widthSize,
                    searchUpdates = FlySearchContentUpdates(
                        onPeopleChanged = onPeopleChanged,
                        onToDestinationChanged = {},
                        onDateSelectionClicked = onDateSelectionClicked,
                        onExploreItemClicked = {

                        }
                    ),
                    fromCity = fromCity
                )
            }
            CraneScreen.Sleep -> {

            }
            CraneScreen.Eat -> {

            }
        }
    }
}

@Composable
fun HomeTabBar(
    openDrawer: () -> Unit,
    tabSelected: CraneScreen,
    onTabSelected: (CraneScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    CraneTabBar(
        modifier = modifier
            .wrapContentWidth()
            .sizeIn(maxWidth = 500.dp),
        onMenuClicked = openDrawer
    ) { tabBarModifier ->
        CraneTabs(
            titles = CraneScreen.entries.map { it.name },
            tabSelected = tabSelected,
            onTabSelected = { newTab ->
                onTabSelected(CraneScreen.entries[newTab.ordinal])
            }
        )
    }
}

@Composable
fun CraneTabBar(
    modifier: Modifier = Modifier,
    onMenuClicked: () -> Unit,
    children: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp)
                    .clickable(onClick = onMenuClicked),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(id = R.string.cd_menu)
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        children(
            Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun CraneTabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: CraneScreen,
    onTabSelected: (CraneScreen) -> Unit
) {
    TabRow(
        modifier = modifier,
        contentColor = MaterialTheme.colors.onSurface,
        divider = {}, // 去掉分割线
        indicator = { tabPositions: List<TabPosition> ->
            Box(modifier = Modifier
                .tabIndicatorOffset(tabPositions[tabSelected.ordinal])
                .fillMaxSize()
                .padding(horizontal = 4.dp)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = androidx.compose.ui.graphics.Color.White
                    ),
                    shape = RoundedCornerShape(size = 16.dp)
                )
            )
        },
        selectedTabIndex = tabSelected.ordinal
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == tabSelected.ordinal
            val textModifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
            Tab(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(16.dp)),
                selected = selected,
                onClick = { onTabSelected(CraneScreen.entries[index]) }
            ) {
                Text(
                    modifier = textModifier,
                    text = title.uppercase()
                )
            }
        }
    }
}

enum class CraneScreen {
    Fly, Sleep, Eat
}

data class FlySearchContentUpdates(
    val onPeopleChanged: (Int) -> Unit,
    val onToDestinationChanged: (String) -> Unit,
    val onDateSelectionClicked: () -> Unit,
    val onExploreItemClicked: OnExploreItemClicked
)