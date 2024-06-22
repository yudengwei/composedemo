package com.imagepick.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imagepick.imagengine.GlideImageEngine
import com.imagepick.model.FileProviderCaptureStrategy
import com.imagepick.model.ImageParameter
import com.imagepick.model.MediaStoreCaptureStrategy
import com.imagepick.model.MediaType

class MainViewModel : ViewModel() {

    var mainUiModel by mutableStateOf(
        value = MainUiModel(
            maxSelect = 3,
            onMaxSelectClick = ::onMaxSelect
        )
    )

    private fun onMaxSelect(maxSelect: Int) {
        mainUiModel = mainUiModel.copy(maxSelect = maxSelect)
    }

    fun buildImageParameter() : ImageParameter {
        return ImageParameter(
            maxSelect = mainUiModel.maxSelect,
            imageEngine = GlideImageEngine(),
            mediaType = MediaType.ImageOnly,
            //captureStrategy = FileProviderCaptureStrategy("com.abiao.composedemo.FileProvider")
            captureStrategy = MediaStoreCaptureStrategy()
        )
    }
}

data class MainUiModel(
    val maxSelect: Int,
    val onMaxSelectClick: (Int) -> Unit
)