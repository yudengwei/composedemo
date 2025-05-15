package com.imagepick.matisse

import android.net.Uri
import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.imagepick.matisse.internal.MediaFilter
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Matisse(
    val maxSelectable: Int = 9,
    val fastSelect: Boolean = false,
    val gridColumns: Int = 4,
    val imageEngine: ImageEngine,
    val captureStrategy: CaptureStrategy? = null,
    val mediaType: MediaType,
    val mediaFilter: MediaFilter? = null
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

@Parcelize
sealed interface MediaType: Parcelable {

    @Parcelize
    data object ImageOnly: MediaType

    @Parcelize
    data object VideoOnly: MediaType

    @Parcelize
    data object ImageAndVideo: MediaType

    @Parcelize
    data class MultipleMimeType(val mimeTypes: Set<String>): MediaType {

        init {
            if (mimeTypes.isEmpty()) {
                throw IllegalArgumentException("mimeTypes cannot be empty")
            }
        }
    }

    val includeImage: Boolean
        get() = when(this) {
            ImageOnly, ImageAndVideo -> {
                true
            }
            VideoOnly -> {
                false
            }
            is MultipleMimeType -> {
                mimeTypes.any {
                    it.startsWith(prefix = ImageMimeTypePrefix)
                }
            }
        }

    val includeVideo: Boolean
        get() = when(this) {
            ImageOnly -> {
                false
            }
            VideoOnly, ImageAndVideo-> {
                true
            }
            is MultipleMimeType -> {
                mimeTypes.any {
                    it.startsWith(prefix = VideoMimeTypePrefix)
                }
            }
        }
}
