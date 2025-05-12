package com.imagepick.matisse.internal

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.IntentCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imagepick.R
import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.Matisse
import com.imagepick.matisse.MediaResource
import com.imagepick.matisse.internal.logic.MatisseViewModel
import com.imagepick.matisse.internal.ui.MatissePage
import com.imagepick.matisse.internal.ui.MatisseTheme

@Suppress("UNCHECKED_CAST")
class MatisseActivity: BaseCaptureActivity() {

    private val matisseViewModel by viewModels<MatisseViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MatisseViewModel(
                        application = application,
                        matisse = IntentCompat.getParcelableExtra(
                            intent,
                            Matisse::class.java.name,
                            Matisse::class.java
                        )!!
                    ) as T
                }
            }
        }
    )

    override val captureStrategy: CaptureStrategy
        get() = matisseViewModel.captureStrategy!!

    override fun dispatchTakePictureResult(mediaResource: MediaResource) {
    }

    override fun takePictureCanceled() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setSystemBarUi(false)
        super.onCreate(savedInstanceState)
        setContent {
            MatisseTheme {
                MatissePage()
            }
        }
    }

    private fun setSystemBarUi(previewPageVisible: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            if (previewPageVisible) {
                hide(WindowInsetsCompat.Type.statusBars())
            } else {
                show(WindowInsetsCompat.Type.statusBars())
            }
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
            val statusBarDarkIcons = if (previewPageVisible) {
                false
            } else {
                resources.getBoolean(R.bool.matisse_status_bar_dark_icons)
            }
            val navigationBarDarkIcons = if (previewPageVisible) {
                false
            } else {
                resources.getBoolean(R.bool.matisse_navigation_bar_dark_icons)
            }
            isAppearanceLightStatusBars = statusBarDarkIcons
            isAppearanceLightNavigationBars = navigationBarDarkIcons
        }
    }
}