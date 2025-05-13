package com.imagepick.matisse.internal.logic

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.imagepick.R
import com.imagepick.matisse.Matisse
import com.imagepick.matisse.MediaResource
import kotlinx.coroutines.delay

internal class MatisseViewModel(application: Application, private val matisse: Matisse) :
    AndroidViewModel(application) {

    private val context: Context
        get() = getApplication()

    private val defaultBucketId = "&__matisseDefaultBucketId__&"

    val captureStrategy = matisse.captureStrategy

    val maxSelectable = matisse.maxSelectable

    private val fastSelect = matisse.fastSelect

    private val gridColumns = matisse.gridColumns

    private val imageEngine = matisse.imageEngine

    private val allMediaResource = mutableListOf<MatisseMediaExtend>()

    private val defaultBucket = MatisseMediaBucket(
        bucketId = defaultBucketId,
        bucketName = getString(R.string.matisse_default_bucket_name),
        supportCapture = captureStrategy != null,
        resources = emptyList()
    )

    var pageViewState by mutableStateOf(
        value = MatissePageViewState(
            maxSelectable = maxSelectable,
            fastSelect = fastSelect,
            gridColumns = gridColumns,
            imageEngine = imageEngine,
            captureStrategy = captureStrategy,
            selectedBucket = defaultBucket,
            mediaBucketsInfo = emptyList(),
            onClickBucket = ::onClickBucket,
            lazyGridState = LazyGridState(),
            onClickMedia = ::onClickMedia,
            onMediaCheckChanged = ::onMediaCheckChange
        )
    )

    private suspend fun onClickBucket(bucketId: String) {
        val viewState = pageViewState
        val isDefaultBucketId = bucketId == defaultBucketId
        val bucketName = viewState.mediaBucketsInfo.first {
            it.bucketId == bucketId
        }.bucketName
        val supportCapture = isDefaultBucketId && defaultBucket.supportCapture
        val resources = if (isDefaultBucketId) {
            allMediaResource
        } else {
            allMediaResource.filter {
                it.bucketId == bucketId
            }
        }
        pageViewState = viewState.copy(
            selectedBucket = MatisseMediaBucket(
                bucketId = bucketId,
                bucketName = bucketName,
                supportCapture = supportCapture,
                resources = resources
            )
        )
        delay(80)
        pageViewState.lazyGridState.animateScrollToItem(index = 0)
    }

    private fun onClickMedia(mediaResource: MatisseMediaExtend) {

    }

    private fun onMediaCheckChange(mediaResource: MatisseMediaExtend) {

    }

    private fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }
}