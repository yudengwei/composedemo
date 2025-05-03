package com.abiao.lib_nowinandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import com.abiao.util.net.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.abiao.common.theme.ComposeDemoTheme
import com.abiao.lib_nowinandroid.databinding.ActivityABinding
import com.abiao.lib_nowinandroid.viewmodel.MainActivityUiState
import com.abiao.lib_nowinandroid.viewmodel.MainViewModel
import com.abiao.util.ext.isSystemInDarkTheme
import com.abiao.util.log.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityABinding>() {

    @Inject
    lateinit var network: NetworkMonitor

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme,
                androidTheme = MainActivityUiState.Loading.shouldUseAndroidTheme,
                disableDynamicTheming = MainActivityUiState.Loading.shouldDisableDynamicTheming
            ),
        )

//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                combine(
//                    isSystemInDarkTheme(),
//                    viewModel.uiState
//                ) {systemDark, uiState ->
//                    ThemeSettings(
//                        darkTheme = uiState.shouldUseDarkTheme(systemDark),
//                        androidTheme = uiState.shouldUseAndroidTheme,
//                        disableDynamicTheming = uiState.shouldDisableDynamicTheming,
//                    )
//                }
//                    .onEach {themeSettings = it}
//                    .map { it.darkTheme }
//                    .distinctUntilChanged()
//                    .collect {darkTheme ->
//                        Logger.d("darkTheme: $darkTheme")
//                        enableEdgeToEdge(
//                            statusBarStyle = SystemBarStyle.auto(
//                                lightScrim = android.graphics.Color.TRANSPARENT,
//                                darkScrim = android.graphics.Color.TRANSPARENT
//                            ) { darkTheme },
//                            navigationBarStyle = SystemBarStyle.auto(
//                                lightScrim = android.graphics.Color.TRANSPARENT,
//                                darkScrim = android.graphics.Color.TRANSPARENT
//                            ) { darkTheme }
//                        )
//                    }
//            }
//        }

        setContent {
            ComposeDemoTheme {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Red)
                ) {

                }
            }
        }
    }
}

data class ThemeSettings(
    val darkTheme: Boolean,
    val androidTheme: Boolean,
    val disableDynamicTheming: Boolean,
)