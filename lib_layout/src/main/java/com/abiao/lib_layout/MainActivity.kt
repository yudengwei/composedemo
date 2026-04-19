package com.abiao.lib_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abiao.common.theme.ComposeDemoTheme
import com.abiao.lib_layout.constraintLayout.AspectRatioConstraintDemo
import com.abiao.lib_layout.constraintLayout.BarrierConstraintDemo
import com.abiao.lib_layout.constraintLayout.BasicConstraint
import com.abiao.lib_layout.constraintLayout.ChainConstraintDemo
import com.abiao.lib_layout.constraintLayout.ConstraintLayDemo
import com.abiao.lib_layout.constraintLayout.CutCornerShapeDemo
import com.abiao.lib_layout.constraintLayout.DecoupledConstraintLayout
import com.abiao.lib_layout.constraintLayout.FormLayout
import com.abiao.lib_layout.constraintLayout.GuidelineConstraintDemo
import com.abiao.lib_layout.customlayout.FirstBaseLineTopDemo
import com.abiao.lib_layout.customlayout.MyCustomColumnDemo
import com.abiao.lib_layout.flow.FlowRow1
import com.abiao.lib_layout.flow.MyFlowRow
import com.abiao.lib_layout.horizontalpager.AutoAdvancePager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDemoTheme {
                //CutCornerShapeDemo()
                Column(
                    modifier = Modifier.fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars)
                        .imePadding()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(20) {
                            Text(text = "我是第${it}个")
                        }
                    }
                    var value by remember { mutableStateOf("") }
                    BasicTextField(
                        value = value,
                        onValueChange = {
                            value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(color = Color.Red)
                    )
                }

            }
        }
    }
}