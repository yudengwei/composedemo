package com.imagepick.matisse

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Matisse(
    val maxSelectable: Int = 9,
    val captureStrategy: CaptureStrategy? = null
): Parcelable

internal const val ImageMimeTypePrefix = "image/"

internal const val VideoMimeTypePrefix = "video/"

@Stable
@Parcelize
data class MediaResource(
    val uri: Uri,
    val path: String,
    val name: String,
    val mimeType: String
) : Parcelable {

    val isImage: Boolean
        get() = mimeType.startsWith(prefix = ImageMimeTypePrefix)

    val isVideo: Boolean
        get() = mimeType.startsWith(prefix = VideoMimeTypePrefix)

}

@Parcelize
data class MatisseCapture(val captureStrategy: CaptureStrategy): Parcelable