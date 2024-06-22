package com.imagepick.imagengine

import android.os.Parcelable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.GlideImage
import com.imagepick.model.MediaResource
import kotlinx.parcelize.Parcelize

@Stable
interface ImageEngine : Parcelable {

    @Composable
    fun Thumbnail(mediaResource: MediaResource)

    @Composable
    fun Image(mediaResource: MediaResource)
}

@Parcelize
class GlideImageEngine : ImageEngine {

    @Composable
    override fun Thumbnail(mediaResource: MediaResource) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize(),
            model = mediaResource.uri,
            contentScale = ContentScale.Crop,
            contentDescription = mediaResource.name)
    }

    @Composable
    override fun Image(mediaResource: MediaResource) {
        if (mediaResource.isVideo) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = mediaResource.uri,
                contentScale = ContentScale.Fit,
                contentDescription = mediaResource.name
            )
        } else {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState()),
                model = mediaResource.uri,
                contentDescription = mediaResource.name)
        }
    }
}

@Parcelize
class CoilImageEngine : ImageEngine {
    @Composable
    override fun Thumbnail(mediaResource: MediaResource) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = mediaResource.uri,
            contentDescription = mediaResource.name)
    }

    @Composable
    override fun Image(mediaResource: MediaResource) {
        if (mediaResource.isVideo) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                model = mediaResource.uri,
                contentDescription = mediaResource.name)
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                contentScale = ContentScale.Fit,
                model = mediaResource.uri,
                contentDescription = mediaResource.name)
        }
    }

}