package com.abiao.lib_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abiao.common.theme.ComposeDemoTheme
import com.abiao.lib_layout.customlayout.FirstBaseLineTopDemo
import com.abiao.lib_layout.customlayout.MyCustomColumnDemo
import com.abiao.lib_layout.flow.FlowRow1
import com.abiao.lib_layout.flow.MyFlowRow
import com.abiao.lib_layout.horizontalpager.AutoAdvancePager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        val items = listOf(
            "是的撒",
            "sadasdasdasda",
            "公司大丰收的方法",
            "sdsdfsdfsdfsdfsdfsdfdsfsdgsdfg",
            "asdasdsa"
        )
        setContent {
            ComposeDemoTheme {
                MyCustomColumnDemo()
            }
        }
    }
}