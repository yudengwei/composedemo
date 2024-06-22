package com.imagepick.model

import android.os.Parcelable
import com.imagepick.imagengine.ImageEngine
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageParameter(
    val maxSelect : Int,
    val imageEngine : ImageEngine,
    val mediaType: MediaType,
    val captureStrategy: CaptureStrategy? = null
) : Parcelable
