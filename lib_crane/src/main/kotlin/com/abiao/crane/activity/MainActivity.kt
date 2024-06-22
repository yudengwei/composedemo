package com.abiao.crane.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abiao.crane.compose.common.CraneTabBar
import com.abiao.crane.compose.home.CraneDrawer
import com.abiao.crane.compose.home.CraneHomeContent
import com.abiao.crane.viewmodel.MainViewModel
import com.abiao.crane.compose.home.OnExploreItemClicked
import com.abiao.crane.ui.CraneTheme
import com.abiao.util.log.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            CraneTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.Home.route
                ) {
                    composable(
                        route = Routes.Home.route
                    ) {
                        val viewModel = hiltViewModel<MainViewModel>()
                        MainScreen(
                            onDateSelectionClicked = {

                            },
                            onExploreItemClicked = {

                            })
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
    onExploreItemClicked: OnExploreItemClicked,
    onDateSelectionClicked: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .statusBarsPadding(),
        drawerContent = {
            CraneDrawer()
        }
    ) {contentPadding ->
        val scope = rememberCoroutineScope()
        BackdropScaffold(
            modifier = Modifier
                .padding(contentPadding),
            appBar = {
                CraneTabBar(
                    openDrawer = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            backLayerContent = {  },
            frontLayerContent = {  }
        )
    }
}