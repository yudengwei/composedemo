package com.imagepick.matisse.internal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.imagepick.R
import com.imagepick.matisse.internal.logic.MatissePageViewState

@Composable
internal fun MatissePage(
    pageViewState: MatissePageViewState,
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
        Box(modifier = Modifier.padding(innerPadding))
    }
}