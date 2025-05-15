package com.abiao.lib_test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imagepick.matisse.CoilImageEngine
import com.imagepick.matisse.Matisse
import com.imagepick.matisse.MediaResource
import com.imagepick.matisse.MediaStoreCaptureStrategy
import com.imagepick.matisse.MediaType
import com.imagepick.matisse.internal.DefaultMediaFilter

class MainViewModel: ViewModel() {

    var pageViewState by mutableStateOf(
        MainPageViewState(
            mediaList = emptyList()
        )
    )

    fun mediaPickerResult(result: List<MediaResource>?) {
        if (!result.isNullOrEmpty()) {
            pageViewState = pageViewState.copy(mediaList = result)
        }
    }

    fun buildMatisse(mediaType: MediaType): Matisse {
        return Matisse(
            maxSelectable = 3,
            captureStrategy = MediaStoreCaptureStrategy(),
            imageEngine = CoilImageEngine(),
            mediaType = mediaType,
            mediaFilter = DefaultMediaFilter()
        )
    }
}