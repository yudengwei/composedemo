package com.imagepick.matisse.internal.logic

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abiao.util.log.Logger
import com.imagepick.R
import com.imagepick.matisse.Matisse
import com.imagepick.matisse.MediaResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    val mediaType = matisse.mediaType

    private val mediaFilter = matisse.mediaFilter

    var loadingDialogVisible by mutableStateOf(false)
        private set

    private val defaultBucket = MatisseMediaBucket(
        bucketId = defaultBucketId,
        bucketName = getString(R.string.matisse_default_bucket_name),
        supportCapture = captureStrategy != null,
        resources = emptyList()
    )

    private val unselectedEnableMediaSelectState = MatisseMediaSelectState(
        isSelected = false,
        isEnabled = true,
        positionIndex = -1
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

    fun requestReadMediaPermissionResult(granted: Boolean) {
        viewModelScope.launch(context = Dispatchers.Main.immediate) {
            showLoadingDialog()
            dismissPreviewPage()
            allMediaResource.clear()
            if (granted) {
                val allResource = loadMediaResources()
                allMediaResource.addAll(allResource)
                val collectBucket = defaultBucket.copy(resources = allResource)
                val allMediaBuckets = run {
                    val temp = allResource.groupBy {
                        it.bucketId
                    }.mapNotNull {
                        val bucketId = it.key
                        val resources = it.value
                        val firstResource = resources.firstOrNull()
                        val bucketName = firstResource?.bucketName
                        if (bucketName.isNullOrEmpty()) {
                            null
                        } else {
                            MatisseMediaBucketInfo(
                                bucketId = bucketId,
                                bucketName = bucketName,
                                size = resources.size,
                                firstMedia = firstResource.media
                            )
                        }
                    }.toMutableList()
                    temp.add(
                        index = 0,
                        element = MatisseMediaBucketInfo(
                            bucketId = collectBucket.bucketId,
                            bucketName = collectBucket.bucketName,
                            size = collectBucket.resources.size,
                            firstMedia = collectBucket.resources.firstOrNull()?.media
                        )
                    )
                    temp
                }
                pageViewState = pageViewState.copy(
                    mediaBucketsInfo = allMediaBuckets,
                    selectedBucket = collectBucket
                )
            } else {
                resetViewState()
                showToast(R.string.matisse_read_media_permission_denied)
            }
            hideLoadingDialog()
        }
    }

    private fun showToast(@StringRes id: Int) {
        showToast(getString(id))
    }

    private fun showToast(text: String) {
        if (text.isNotBlank()) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetViewState() {
        pageViewState = pageViewState.copy(
            selectedBucket = defaultBucket,
            mediaBucketsInfo = listOf(
                MatisseMediaBucketInfo(
                    defaultBucket.bucketId,
                    defaultBucket.bucketName,
                    defaultBucket.resources.size,
                    defaultBucket.resources.firstOrNull()?.media
                )
            )
        )
    }

    private suspend fun loadMediaResources(): List<MatisseMediaExtend> {
        return withContext(Dispatchers.Default) {
            val resourcesInfo = MediaProvider.loadResources(
                context = context,
                mediaType = mediaType
            )
            Logger.d("resourcesInfo.szie: ${resourcesInfo?.size}")
            if (resourcesInfo.isNullOrEmpty()) {
                emptyList()
            } else {
                resourcesInfo.mapNotNull {
                    val media = MediaResource(
                        uri = it.uri,
                        path = it.path,
                        name = it.name,
                        mimeType = it.mimeType
                    )
                    if (mediaFilter?.ignoreMedia(media) == true) {
                        null
                    } else {
                        MatisseMediaExtend(
                            mediaId = it.mediaId,
                            bucketId = it.bucketId,
                            bucketName = it.bucketName,
                            media = media,
                            selectState = mutableStateOf(unselectedEnableMediaSelectState)
                        )
                    }
                }
            }
        }

    }

    private fun dismissPreviewPage(){}

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

    private fun showLoadingDialog() {
        loadingDialogVisible = true
    }

    private fun hideLoadingDialog() {
        loadingDialogVisible = false
    }
}