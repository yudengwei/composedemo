package com.abiao.lib_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abiao.test.R
import com.imagepick.matisse.MatisseContract


class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            MatisseTheme {
                val mediaPickerLauncher = rememberLauncherForActivityResult(
                    contract = MatisseContract()
                ) { result ->
                    mainViewModel.mediaPickerResult(result)
                }
                MainPage(
                    onClick = {
                        mediaPickerLauncher.launch(
                            mainViewModel.buildMatisse()
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MainPage(
    onClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
                    .height(55.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .padding(horizontal = 10.dp),
                    text = stringResource(R.string.matisse),
                    fontSize = 22.sp,
                    color = Color.White
                )
            }
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(
                onClick = onClick
            ) {
                Text("跳转")
            }
        }
    }
}