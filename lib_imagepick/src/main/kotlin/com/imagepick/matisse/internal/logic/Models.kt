package com.imagepick.matisse.internal.logic

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Stable
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