package com.imagepick.matisse

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Matisse(
    val maxSelectable: Int = 9
): Parcelable

@Stable
@Parcelize
data class MediaResource(
    internal val id: Long,
    internal val bucketId: String,
    internal val bucketName: String,
    val uri: Uri,
    val path: String,
    val name: String,
    val mimeType: String,
): Parcelable

@Parcelize
data class MatisseCapture(val captureStrategy: CaptureStrategy): Parcelable