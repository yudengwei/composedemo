package com.imagepick.ui.compose


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon

import androidx.compose.runtime.getValue

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import com.imagepick.imagengine.ImageEngine
import com.imagepick.model.MediaResource
import com.imagepick.viewmodel.ImagePickViewModel

@Composable
internal fun ImagePickPage(
    imagePickViewModel: ImagePickViewModel,
    imageEngine: ImageEngine,
    onRequestTakePicture: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            ImagePickTopBar(
                uiModelStatus = imagePickViewModel.uiModelStatus,
                imageEngine = imageEngine)
        },
        bottomBar = {
            ImagePickBottomBar(imagePickViewModel = imagePickViewModel)
        }
    ) {padding ->
        val captureStrategy by remember {
            derivedStateOf {
                imagePickViewModel.uiModelStatus.selectedBucket.captureStrategy
            }
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = remember { LazyGridState() },
            columns = GridCells.Fixed(count = 3)
        ) {
            if (captureStrategy != null) {
                item(
                    key = "Capture",
                    contentType = "Capture",
                    content = {
                        CaptureItem(onClick = onRequestTakePicture)
                    }
                )
            }
            items(
                items = imagePickViewModel.uiModelStatus.selectedBucket.mediaSource,
                key = {
                    it.id
                },
                contentType = {
                    "Media"
                },
                itemContent = {
                    val mediaPlacement by remember {
                        derivedStateOf {
                            val index = imagePickViewModel.selectedResources.indexOf(element = it)
                            val isSelected = index > -1
                            val enabled =
                                isSelected || imagePickViewModel.selectedResources.size < imagePickViewModel.maxSelectable
                            val position = if (isSelected) {
                                (index + 1).toString()
                            } else {
                                ""
                            }
                            MediaPlacement(
                                isSelected = isSelected,
                                enabled = enabled,
                                position = position
                            )
                        }
                    }
                    MediaItem(
                        mediaSource = it,
                        imageEngine = imageEngine,
                        onClickCheckBox = imagePickViewModel.uiModelStatus.onMediaCheckChanged,
                        mediaPlacement = mediaPlacement,
                        onClickMedia = imagePickViewModel.uiModelStatus.onClickMedia
                    )
                }
            )
        }
    }
}

@Stable
private data class MediaPlacement(
    val isSelected: Boolean,
    val enabled: Boolean,
    val position: String
)

@Composable
private fun LazyGridItemScope.CaptureItem(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .animateItemPlacement()
            .padding(1.dp)
            .aspectRatio(1.0f)
            .clip(shape = RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(fraction = 0.5f)
                .align(Alignment.Center),
            imageVector = Icons.Filled.Menu,
            contentDescription = "Capture")
    }
}

@Composable
private fun LazyGridItemScope.MediaItem(
    mediaSource : MediaResource,
    imageEngine: ImageEngine,
    onClickCheckBox: (MediaResource) -> Unit,
    mediaPlacement: MediaPlacement,
    onClickMedia: (MediaResource) -> Unit,
) {
    Box(
        modifier = Modifier
            .animateItemPlacement()
            .aspectRatio(ratio = 1f)
            .padding(1.dp)
            .clickable {
                onClickMedia(mediaSource)
            }
            .clip(shape = RoundedCornerShape(4.dp))
    ) {
        imageEngine.Thumbnail(mediaResource = mediaSource)
        CircleCheckBox(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(all = 4.dp),
            text = mediaPlacement.position,
            enabled = mediaPlacement.enabled,
            checked = mediaPlacement.isSelected,
            onClick = {
                onClickCheckBox(mediaSource)
            })
    }
}