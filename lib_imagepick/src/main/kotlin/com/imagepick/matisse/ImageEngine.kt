package com.imagepick.matisse

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Stable
interface ImageEngine: Parcelable {

    @Composable
    fun Thumbnail(mediaResource: MediaResource)

    @Composable
    fun Image(mediaResource: MediaResource)
}

@Parcelize
class CoilImageEngine: ImageEngine{
    @Composable
    override fun Thumbnail(mediaResource: MediaResource) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Image(mediaResource: MediaResource) {
        TODO("Not yet implemented")
    }

}