package com.imagepick.test.viemodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.imagepick.R
import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.MatisseCapture
import com.imagepick.matisse.MediaResource
import com.imagepick.matisse.MediaStoreCaptureStrategy
import com.imagepick.test.MainPageViewState
import com.imagepick.test.MediaCaptureStrategy

class MainViewModel: ViewModel() {

    var mainPageViewState by mutableStateOf(
        value = MainPageViewState(
            captureStrategy = MediaCaptureStrategy.MediaStore,
            mediaList = emptyList(),
            onCaptureStrategyChanged = ::onCaptureStrategyChanged,
        )
    )

    fun takePictureResult(resource: MediaResource?) {
        if (resource != null) {
            mainPageViewState = mainPageViewState.copy(
                mediaList = listOf(resource)
            )
        }
    }

    fun buildMatisseCapture(): MatisseCapture? {
        val captureStrategy = getMediaCaptureStrategy() ?: return null
        return MatisseCapture(captureStrategy)
    }

    private fun getMediaCaptureStrategy(): CaptureStrategy? {
        return when (mainPageViewState.captureStrategy) {
            MediaCaptureStrategy.Smart -> null
            MediaCaptureStrategy.FileProvider -> null
            MediaCaptureStrategy.MediaStore -> MediaStoreCaptureStrategy()
            MediaCaptureStrategy.Close -> null
        }
    }

    private fun onCaptureStrategyChanged(captureStrategy: MediaCaptureStrategy) {
        mainPageViewState = mainPageViewState.copy(captureStrategy = captureStrategy)
    }
}