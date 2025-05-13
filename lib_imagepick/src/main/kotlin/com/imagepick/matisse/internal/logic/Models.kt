package com.imagepick.matisse.internal.logic

import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.ImageEngine
import com.imagepick.matisse.MediaResource

data class MatisseTakePictureContractParams(
    val uri: Uri,
    val extra: Bundle
)

@Stable
internal data class MatisseMediaBucketInfo(
    val bucketId: String,
    val bucketName: String,
    val size: Int,
    val firstMedia: MediaResource?
)

@Stable
internal data class MatissePageViewState(
    val maxSelectable: Int,
    val fastSelect: Boolean,
    val gridColumns: Int,
    val imageEngine: ImageEngine,
    val captureStrategy: CaptureStrategy?,
    val mediaBucketsInfo: List<MatisseMediaBucketInfo>,
    val selectedBucket: MatisseMediaBucket,
    val lazyGridState: LazyGridState,
    val onClickBucket: suspend (String) -> Unit,
    val onClickMedia: (MatisseMediaExtend) -> Unit,
    val onMediaCheckChanged: (MatisseMediaExtend) -> Unit
)

@Stable
internal data class MatisseMediaBucket(
    val bucketId: String,
    val bucketName: String,
    val supportCapture: Boolean,
    val resources: List<MatisseMediaExtend>
)

@Stable
internal data class MatisseMediaExtend(
    val mediaId: Long,
    val bucketId: String,
    val bucketName: String,
    val media: MediaResource,
    val selectState: State<MatisseMediaSelectState>
)

@Stable
internal data class MatisseMediaSelectState(
    val isSelected: Boolean,
    val isEnabled: Boolean,
    val positionIndex: Int
) {

    val positionFormatted = run {
        if (positionIndex >= 0) {
            (positionIndex + 1).toString()
        } else {
            null
        }
    }

}