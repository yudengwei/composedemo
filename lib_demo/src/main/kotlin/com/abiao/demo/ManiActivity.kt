package com.abiao.demo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.abiao.common.base.BaseActivity
import com.abiao.demo.navigation.RouteConfig
import com.abiao.demo.navigation.Screen
import com.abiao.demo.navigation.SwitchRegion
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManiActivity : BaseActivity() {

    @Composable
    override fun setComposeContent() {
        //MySootheApp(calculateWindowSizeClass(activity = this))
        //SwitchRegion()
        val navController = rememberNavController()
        Screen(
            onButtonOneClick = {
                navController.navigate(RouteConfig.PAGE_ONE)
            },
            onButtonTwoClick = {
                // 添加必选参数
                navController.navigate("${RouteConfig.PAGE_TWO}/阿标/30")
            },
            onButtonThreeClick = {
                navController.navigate(RouteConfig.PAGE_THREE) {

                    // 将three到one中间的compose都给出栈
                    //popUpTo(RouteConfig.PAGE_ONE)

                    //inclusive 为true的话会把one自己也出栈
                    //popUpTo(RouteConfig.PAGE_ONE) {
                        //inclusive = true
                    //}

                    // launch栈顶复用，类似activity的singleTop, 如果目标已经在栈顶则不会重新创建
                    launchSingleTop = true
                }
            },
            onThreeButtonCLick = {
                // 返回上一级界面
                //navController.navigateUp()

                // 不带参数的话，和navigateUp是一样的效果，返回上一界面
                //navController.popBackStack()

                // 回退到指定界面，如果inclusive为true，会把指定界面也移除,同时中间的界面包括自己都会被移除出栈
                navController.popBackStack(route = RouteConfig.PAGE_ONE, inclusive = false)
            },
            navController = navController
        )
    }
}


