package com.imagepick.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

internal const val ImageMimeTypePrefix = "image/"

internal const val VideoMimeTypePrefix = "video/"

@Parcelize
data class MediaResource(
    val id : Long,
    val uri : Uri,
    val path : String,
    val name : String,
    val mimeType : String,
    val bucketId : String,
    val bucketName : String,
) : Parcelable {

    val isImage : Boolean
        get() = mimeType.startsWith(prefix = ImageMimeTypePrefix)

    val isVideo : Boolean
        get() = mimeType.startsWith(prefix = VideoMimeTypePrefix)
}
