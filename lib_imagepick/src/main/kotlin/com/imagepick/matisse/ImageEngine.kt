package com.imagepick.matisse

import android.os.Parcelable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import kotlinx.parcelize.Parcelize

@Stable
interface ImageEngine: Parcelable {

    @Composable
    fun Thumbnail(mediaResource: MediaResource)

    @Composable
    fun Image(mediaResource: MediaResource)
}

@Parcelize
class CoilImageEngine: ImageEngine {

    @Composable
    override fun Thumbnail(mediaResource: MediaResource) {
        val context = LocalContext.current
        val request = remember(key1 = mediaResource.uri) {
            val uri = mediaResource.uri
            val memoryCacheKey = uri.toString()
            ImageRequest.Builder(context = context)
                .data(uri)
                .placeholderMemoryCacheKey(memoryCacheKey)
                .memoryCacheKey(memoryCacheKey)
                .build()
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = request,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

    @Composable
    override fun Image(mediaResource: MediaResource) {
        val context = LocalContext.current
        val request = remember(key1 = mediaResource.uri) {
            val uri = mediaResource.uri
            val memoryCacheKey = uri.toString()
            ImageRequest.Builder(context = context)
                .data(uri)
                .placeholderMemoryCacheKey(memoryCacheKey)
                .memoryCacheKey(memoryCacheKey)
                .build()
        }
        if (mediaResource.isVideo) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = request,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                model = request,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }

}