package com.imagepick.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.imagepick.imagengine.ImageEngine
import com.imagepick.model.CaptureStrategy
import com.imagepick.model.ImageParameter
import com.imagepick.model.MediaBucket
import com.imagepick.model.MediaResource
import com.imagepick.util.MediaProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImagePickViewModel(application: Application, val imageParameter: ImageParameter) :
    AndroidViewModel(application) {

    private val defaultBucketId = "&__matisseDefaultBucketId__&"

    private val context = application

    var uiModelStatus by mutableStateOf(
        UiModelStatus(
            mediaBuckets = emptyList(),
            selectedBucket = MediaBucket(id = defaultBucketId, mediaSource = emptyList(), captureStrategy = null),
            onMediaCheckChanged = ::onMediaCheckChanged,
            onClickMedia = ::onClickMedia
        )
    )
        private set

    val imageEngine : ImageEngine
        get() = imageParameter.imageEngine

    var loadingDialogVisible by mutableStateOf(false)
        private set

    val captureStrategy : CaptureStrategy?
        get() = imageParameter.captureStrategy

    var selectedResources by mutableStateOf(
        value = emptyList<MediaResource>()
    )
        private set

    val maxSelectable: Int
        get() = imageParameter.maxSelect

    var imagePreviewPageViewState by mutableStateOf(
        value = ImagePreviewPageViewState(
            visible = false,
            initialPage = 0,
            imageEngine = imageEngine,
            selectedResources = emptyList(),
            previewResources = emptyList(),
            onDismissRequest = ::dismissPreviewPage,
        )
    )
        private set

    fun requestReadMediaPermissionResult(granted: Boolean) {
        viewModelScope.launch {
            if (granted) {
                showLoading()
                val allSource = MediaProvider.loadResources(
                    context, imageParameter.mediaType
                )
                val allBucket = groupByBucket(allSource)
                uiModelStatus = uiModelStatus.copy(
                    mediaBuckets = allBucket,
                    selectedBucket = allBucket[0]
                )
                hideLoading()
            } else {
                Toast.makeText(context, "请先同意权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClickMedia(mediaResource: MediaResource) {
        previewResource(
            initialMedia = mediaResource,
            previewResources = uiModelStatus.selectedBucket.mediaSource
        )
    }

    private fun previewResource(
        initialMedia: MediaResource?,
        previewResources: List<MediaResource>
    ) {
        if (previewResources.isEmpty()) {
            return
        }
        val mSelectedResources = selectedResources
        val initialPage = if (initialMedia == null) {
            0
        } else {
            previewResources.indexOf(element = initialMedia).coerceAtLeast(minimumValue = 0)
        }
        imagePreviewPageViewState = imagePreviewPageViewState.copy(
            visible = true,
            initialPage = initialPage,
            selectedResources = mSelectedResources,
            previewResources = previewResources,
        )
    }

    private fun dismissPreviewPage() {
        if (imagePreviewPageViewState.visible) {
            imagePreviewPageViewState = imagePreviewPageViewState.copy(visible = false)
        }
    }

    fun showLoading() {
        loadingDialogVisible = true
    }

    fun hideLoading() {
        loadingDialogVisible = false
    }

    private fun onMediaCheckChanged(mediaResource: MediaResource) {
        val selectResourceMutable = selectedResources.toMutableList()
        val isContains = selectResourceMutable.contains(element = mediaResource)
        if (isContains) {
            selectResourceMutable.remove(mediaResource)
        } else if (selectResourceMutable.size == maxSelectable){
            showToast("无法再添加更多图片")
        } else {
            selectResourceMutable.add(mediaResource)
        }
        selectedResources = selectResourceMutable
    }

    private suspend fun groupByBucket(mediaResources: List<MediaResource>) : List<MediaBucket> {
        return withContext(Dispatchers.IO) {
            val resourcesMap = linkedMapOf<String, MutableList<MediaResource>>()
            mediaResources.forEach { res ->
                if (res.bucketName.isNotBlank()) {
                    val bucketId = res.bucketId
                    val list = resourcesMap[bucketId]
                    if (list == null) {
                        resourcesMap[bucketId] = mutableListOf(res)
                    } else {
                        list.add(res)
                    }
                }
            }
            buildList {
                add (
                    element = MediaBucket(
                        id = defaultBucketId,
                        name = "全部",
                        mediaSource = mediaResources,
                        captureStrategy = captureStrategy
                    )
                )
                resourcesMap.forEach { key, value ->
                    val bucketId = key
                    val mediaSource = value
                    val bucketName = value[0].bucketName
                    add(
                        element = MediaBucket(
                            id = bucketId,
                            name = bucketName,
                            mediaSource = mediaSource,
                            captureStrategy = null
                        )
                    )
                }
            }
        }
    }

    private fun showToast(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

data class UiModelStatus(
    val title : String = "全部",
    val mediaBuckets : List<MediaBucket>,
    val selectedBucket: MediaBucket,
    val onMediaCheckChanged: (MediaResource) -> Unit,
    val onClickMedia: (MediaResource) -> Unit
)

@Stable
data class ImagePreviewPageViewState(
    val visible: Boolean,
    val initialPage: Int,
    val imageEngine: ImageEngine,
    val previewResources: List<MediaResource>,
    val selectedResources: List<MediaResource>,
    val onDismissRequest: () -> Unit
)