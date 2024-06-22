package com.imagepick.model

import androidx.compose.runtime.Stable

@Stable
data class MediaBucket(
    val id : String,
    val name : String = "全部",
    val mediaSource : List<MediaResource>,
    val captureStrategy: CaptureStrategy?
)
