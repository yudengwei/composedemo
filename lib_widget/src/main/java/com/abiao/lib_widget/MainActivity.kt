package com.abiao.lib_widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abiao.common.theme.ComposeDemoTheme
import com.abiao.lib_widget.topbar.CenterAlignedTopAppBarExample
import com.abiao.lib_widget.topbar.LargeTitleExample
import com.abiao.lib_widget.topbar.MenuTitleExample
import com.abiao.lib_widget.topbar.TopAppBarEg

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                TopAppBarEg()
            }
        }
    }
}