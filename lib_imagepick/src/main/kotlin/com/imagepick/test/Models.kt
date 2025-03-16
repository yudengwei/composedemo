package com.imagepick.test

import com.imagepick.matisse.MediaResource

enum class MediaCaptureStrategy {
    Smart,
    FileProvider,
    MediaStore,
    Close
}

data class MainPageViewState(
    val captureStrategy: MediaCaptureStrategy,
    val mediaList: List<MediaResource>,
    val onCaptureStrategyChanged: (MediaCaptureStrategy) -> Unit,
)