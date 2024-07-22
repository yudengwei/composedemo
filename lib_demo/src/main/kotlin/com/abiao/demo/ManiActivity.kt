package com.abiao.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.abiao.common.theme.ComposeDemoTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManiActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {

            }
        }
    }
}




