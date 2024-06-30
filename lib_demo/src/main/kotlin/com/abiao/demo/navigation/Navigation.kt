package com.abiao.demo.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

object RouteConfig {
    const val PAGE_ONE = "page_one"
    const val PAGE_TWO = "page_two"
    const val PAGE_THREE = "page_three"
}

object ParamsConfig {
    const val PARAMS_NAME = "name"
    const val PARAMS_AGE = "age"
}

@Composable
fun PageOne() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Blue)
        .wrapContentSize(align = Alignment.Center)
    ) {
        Text(text = "page one", fontSize = 30.sp, color = Color.White)

    }
}

@Composable
fun PageTwo(
    name: String,
    age: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green)
            .wrapContentSize(align = Alignment.Center),
    ) {
        Text(text = "page two", fontSize = 30.sp, color = Color.Black)
        Text(text = "name: $name")
        Text(text = "age: $age")
    }
}

@Composable
fun PageThree(
    onClick: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red)
        .wrapContentSize(align = Alignment.Center)
    ) {
        Text(text = "page three", fontSize = 30.sp, color = Color.Blue)
        Button(onClick = onClick) {
            Text(text = "点击跳转到page one")
        }
    }
}

@Composable
fun SwitchRegion(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouteConfig.PAGE_ONE,
    onThreeButtonCLick: () -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable(route = RouteConfig.PAGE_ONE) {
            PageOne()
        }
        composable(
            route = "${RouteConfig.PAGE_TWO}/{${ParamsConfig.PARAMS_NAME}}/{${ParamsConfig.PARAMS_AGE}}",
            arguments = listOf(
                navArgument(ParamsConfig.PARAMS_NAME) {}, // string可以不额外指定
                navArgument(ParamsConfig.PARAMS_AGE) {
                    type = NavType.IntType
                    defaultValue = 30
                    nullable = false
                }
            )
        ) {
            val argument = requireNotNull(it.arguments)
            val name = argument.getString(ParamsConfig.PARAMS_NAME) ?: ""
            val age = argument.getInt(ParamsConfig.PARAMS_AGE)
            PageTwo(
                name = name, age = age
            )
        }
        composable(route = RouteConfig.PAGE_THREE) {
            PageThree(
                onClick = onThreeButtonCLick
            )
        }
    }
}

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    onButtonOneClick: () -> Unit,
    onButtonTwoClick: () -> Unit,
    onButtonThreeClick: () -> Unit,
    onThreeButtonCLick: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Top
    ) {
        SwitchButton(
            onButtonOneClick = onButtonOneClick,
            onButtonTwoClick = onButtonTwoClick,
            onButtonThreeClick = onButtonThreeClick
        )
        SwitchRegion(
            navController = navController,
            onThreeButtonCLick = onThreeButtonCLick
        )
    }
}

@Composable
fun SwitchButton(
    onButtonOneClick: () -> Unit,
    onButtonTwoClick: () -> Unit,
    onButtonThreeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = onButtonOneClick
        ) {
            Text(
                text = "Button one",
                maxLines = 1,
                fontSize = 15.sp
            )
        }
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = onButtonTwoClick
        ) {
            Text(
                text = "Button two",
                maxLines = 1,
                fontSize = 15.sp
            )
        }
        Button(
            modifier = Modifier
                .weight(1f),
            onClick = onButtonThreeClick
        ) {
            Text(
                text = "Button three",
                maxLines = 1,
                fontSize = 15.sp,
            )
        }
    }
}