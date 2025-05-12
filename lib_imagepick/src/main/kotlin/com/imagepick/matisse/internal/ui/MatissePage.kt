package com.imagepick.matisse.internal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.imagepick.R

@Composable
internal fun MatissePage() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.matisse_main_page_background_color),
        topBar = {
            MatisseTopBar(
                title = "所有"
            )
        }
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}