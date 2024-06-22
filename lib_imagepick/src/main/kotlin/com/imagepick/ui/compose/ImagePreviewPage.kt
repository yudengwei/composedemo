package com.imagepick.ui.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.imagepick.model.MediaResource
import com.abiao.common.theme.black
import com.imagepick.viewmodel.ImagePreviewPageViewState
import kotlin.math.absoluteValue

@Composable
internal fun ImagePreviewPage(
    imageViewState: ImagePreviewPageViewState,
) {
    if (imageViewState.visible) {
        val previewResources = imageViewState.previewResources
        val pageState = rememberPagerState(
            initialPage = imageViewState.initialPage,
            initialPageOffsetFraction = 0f
        ) {
            previewResources.size
        }
        BackHandler(
            enabled = imageViewState.visible,
            onBack = imageViewState.onDismissRequest
        )
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            contentWindowInsets = WindowInsets(
                left = 0.dp,
                right = 0.dp,
                top = 0.dp,
                bottom = 0.dp
            ),
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .padding(bottom = 56.dp),
                    state = pageState,
                    pageSpacing = 0.dp,
                    verticalAlignment = Alignment.CenterVertically,
                    key = { index ->
                        previewResources[index].id
                    }
                ) { pageIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = com.abiao.common.theme.black),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    val pageOffset =
                                        ((pageState.currentPage - pageIndex) + pageState.currentPageOffsetFraction).absoluteValue
                                    val fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    lerp(
                                        start = 0.84f,
                                        stop = 1f,
                                        fraction = fraction
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = fraction
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            imageViewState.imageEngine.Image(mediaResource = previewResources[pageIndex])
                        }
                    }
                }
            }
        }
    }
}