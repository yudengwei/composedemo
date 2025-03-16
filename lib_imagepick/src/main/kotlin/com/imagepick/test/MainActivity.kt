package com.imagepick.test

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
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.imagepick.R
import com.imagepick.matisse.contract.MatisseCaptureContract
import com.imagepick.test.ui.MatisseTheme
import com.imagepick.test.viemodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setSystemBarUi()
        super.onCreate(savedInstanceState)
        setContent {
            val takePictureLauncher =
                rememberLauncherForActivityResult(MatisseCaptureContract()) {result ->
                    mainViewModel.takePictureResult(result)
                }
            val mediaPickerLauncher =

            MatisseTheme {
                MainPage(
                    mainPageViewState = mainViewModel.mainPageViewState,
                    takePicture = {
                        val matisseCapture = mainViewModel.buildMatisseCapture()
                        if (matisseCapture != null) {
                            takePictureLauncher.launch(matisseCapture)
                        }
                    },
                    selectPick = {

                    }
                )
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
private fun MainPage(
    mainPageViewState: MainPageViewState,
    takePicture: () -> Unit,
    selectPick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(60.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(20.dp),
                    fontSize = 22.sp,
                    color = Color.White
                )
            }
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp, 10.dp, 16.dp, 60.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Title(text = "CaptureStrategy")
            FlowRow(
                maxItemsInEachRow = 2,
                verticalArrangement = Arrangement.Center
            ) {
                for (strategy in MediaCaptureStrategy.entries) {
                    RadioButton(
                        tips = strategy.name,
                        selected = mainPageViewState.captureStrategy == strategy,
                        onClick = {
                            mainPageViewState.onCaptureStrategyChanged(strategy)
                        }
                    )
                }
            }
            Button(
                text = "直接拍照",
                enabled = mainPageViewState.captureStrategy != MediaCaptureStrategy.Close,
                onClick = takePicture
            )
            Button(
                text = "选择图片",
                enabled = true,
                onClick = selectPick
            )
        }
    }
}

@Composable
private fun Button(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        fontSize = 17.sp
    )
}

@Composable
private fun RadioButton(
    tips: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tips,
            fontSize = 16.sp
        )
        androidx.compose.material3.RadioButton(
            selected, onClick
        )
    }
}
