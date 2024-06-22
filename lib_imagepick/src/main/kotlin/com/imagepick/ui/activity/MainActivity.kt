package com.imagepick.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import com.imagepick.contract.ImagePickContract
import com.imagepick.ui.compose.MyLazyColumn
import com.abiao.common.theme.ComposeDemoTheme
import com.imagepick.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "yudengwei"
    }
    
   private val mainViewModel by viewModels<MainViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        setSystemBarUi()
        super.onCreate(savedInstanceState)
        setContent {
            val mediaPickerLauncher =
                rememberLauncherForActivityResult(contract = ImagePickContract()) { result ->
                    Log.d(TAG, "result: $result")
                }
            ComposeDemoTheme {
                MyLazyColumn()
            }
        }
    }

    private fun setSystemBarUi() {
        val statusBarColor = android.graphics.Color.TRANSPARENT
        val navigationBarColor = android.graphics.Color.TRANSPARENT
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = statusBarColor
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = navigationBarColor
            )
        )
    }
}

@Composable
fun Foo() {
    var text by remember { mutableStateOf("") }
    Log.d("yudengwei", "Foo")
    Button(onClick = {
        text = "$text $text"
    }.also { Log.d("yudengwei", "Button") }) {
        Log.d("yudengwei", "Button content lambda")
        Text(text).also { Log.d("yudengwei", "Text") }
    }
}
