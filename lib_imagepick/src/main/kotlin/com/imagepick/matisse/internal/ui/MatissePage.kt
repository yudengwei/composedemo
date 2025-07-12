package com.imagepick.matisse.internal.ui

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.imagepick.R
import com.imagepick.matisse.ImageEngine
import com.imagepick.matisse.internal.logic.MatisseMediaExtend
import com.imagepick.matisse.internal.logic.MatissePageViewState

@Composable
internal fun MatissePage(
    pageViewState: MatissePageViewState,
    onRequestTakePicture: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.matisse_main_page_background_color),
        topBar = {
            MatisseTopBar(
                modifier = Modifier,
                title = "所有",
                mediaBucketsInfo = pageViewState.mediaBucketsInfo,
                onClickBucket = pageViewState.onClickBucket,
                imageEngine = pageViewState.imageEngine
            )
        }
    ) {innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pageViewState.lazyGridState,
            columns = GridCells.Fixed(count = pageViewState.gridColumns),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = PaddingValues(
                top = 1.dp,
                bottom = if (pageViewState.fastSelect) {
                    16.dp
                } else {
                    1.dp
                }
            )
        ) {
            if (pageViewState.selectedBucket.supportCapture) {
                item(
                    key = "CaptureItem",
                    contentType = "CaptureItem"
                ) {
                    CaptureItem(
                        onClick = onRequestTakePicture
                    )
                }
            }
            items(
                items = pageViewState.selectedBucket.resources,
                key = {
                    it.mediaId
                },
                contentType = {
                    "MediaItem"
                }
            ) {
                MediaItem(
                    mediaResource = it,
                    imageEngine = pageViewState.imageEngine,
                    onClickMedia = pageViewState.onClickMedia,
                    onClickCheckBox = pageViewState.onMediaCheckChanged
                )
            }
        }
    }
}

@Composable
private fun CaptureItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(ratio = 1f)
            .clip(RoundedCornerShape(size = 4.dp))
            .background(colorResource(R.color.matisse_capture_item_background_color))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize(fraction = 1f),
            imageVector = Icons.Default.Settings,
            tint = colorResource(R.color.matisse_capture_item_icon_color),
            contentDescription = null
        )
    }
}

@Composable
private fun MediaItem(
    modifier: Modifier = Modifier,
    imageEngine: ImageEngine,
    mediaResource: MatisseMediaExtend,
    onClickMedia: (MatisseMediaExtend) -> Unit,
    onClickCheckBox: (MatisseMediaExtend) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(color = colorResource(R.color.matisse_media_item_background_color))
            .clickable {
                onClickMedia(mediaResource)
            },
        contentAlignment = Alignment.Center
    ) {
        imageEngine.Thumbnail(mediaResource.media)
        val scrimColor = if (mediaResource.selectState.value.isSelected) {
            colorResource(R.color.matisse_media_item_scrim_color_when_selected)
        } else {
            colorResource(R.color.matisse_media_item_scrim_color_when_unselected)
        }
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(color = scrimColor)
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .fillMaxSize(fraction = 0.33f)
                .clickableNoRipple {
                    onClickCheckBox(mediaResource)
                },
            contentAlignment = Alignment.Center
        ) {
            MatisseCheckbox(
                modifier = Modifier
                    .fillMaxSize(fraction = 0.68f),
                selectState = mediaResource.selectState.value,
                onClick = {
                    onClickCheckBox(mediaResource)
                }
            )
        }
    }
}