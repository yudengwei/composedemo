package com.abiao.crane.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abiao.crane.R
import com.abiao.crane.compose.CraneHome
import com.abiao.crane.viewmodel.MainViewModel
import com.abiao.crane.ui.CraneTheme
import com.abiao.crane.viewmodel.SplashState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            CraneTheme {
                val widthSize = calculateWindowSizeClass(activity = this).widthSizeClass
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Home.route) {
                    composable(route = Routes.Home.route) {
                        val viewModel = hiltViewModel<MainViewModel>()
                        MainScreen(
                            mainViewModel = viewModel,
                            widthSize = widthSize,
                            onDateSelectionClicked = {

                            }
                        )
                    }
                    composable(route = Routes.Calendar.route) {

                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Calendar : Routes("calendar")
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    widthSize: WindowWidthSizeClass,
    onDateSelectionClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .windowInsetsPadding(
                insets = WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)),
        color = MaterialTheme.colors.primary
    ) {
        val transitionState = remember { MutableTransitionState(mainViewModel.shownSplash.value) }
        val transient = updateTransition(transitionState = transitionState, label = "splashTransition")
        val splashAlpha by transient.animateFloat(
            transitionSpec = { tween(durationMillis = 100) },
            label = "splashAlpha"
        ) {splashState ->
            if (splashState == SplashState.Shown) 1f else 0f
        }
        val contentAlpha by transient.animateFloat(
            transitionSpec = { tween(durationMillis = 300) },
            label = "contentAlpha"
        ) {splashState ->
            if (splashState == SplashState.Shown) 0f else 1f
        }
        val contentTopPadding by transient.animateDp(
            transitionSpec = { spring(stiffness = StiffnessLow) },
            label = "contentTopPadding"
        ) {splashState ->
            if (splashState == SplashState.Shown) 100.dp else 0.dp
        }

        Box {
            LandingScreen(
                modifier = Modifier
                    .alpha(splashAlpha),
                onTimeout = {
                    transitionState.targetState = SplashState.Completed
                    mainViewModel.shownSplash.value = SplashState.Completed
                }
            )
            MainContent(
                modifier = Modifier
                    .alpha(contentAlpha),
                topPadding = contentTopPadding,
                viewModel = mainViewModel,
                widthSize = widthSize,
                onDateSelectionClicked = onDateSelectionClicked
            )
        }
    }
}

@Composable
fun LandingScreen(
    modifier: Modifier = Modifier,
    onTimeout : () -> Unit
) {
    val currentOnTimeout by rememberUpdatedState(newValue = onTimeout)
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        currentOnTimeout()
    }

    Image(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(),
        painter = painterResource(id = R.drawable.ic_crane_drawer),
        contentDescription = null)
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    viewModel: MainViewModel,
    widthSize: WindowWidthSizeClass,
    onDateSelectionClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(topPadding))
        CraneHome(
            viewModel = viewModel,
            widthSize = widthSize,
            onDateSelectionClicked = onDateSelectionClicked
        )
    }
}

@Composable
fun HomeDraw(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp, start = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_crane_drawer),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        screens.forEach {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

private val screens = listOf(
    R.string.screen_title_find_trips,
    R.string.screen_title_my_trips,
    R.string.screen_title_saved_trips,
    R.string.screen_title_price_alerts,
    R.string.screen_title_my_account
)