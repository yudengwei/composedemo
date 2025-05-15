package com.imagepick.matisse.internal

import android.net.Uri
import android.os.Parcelable
import com.imagepick.matisse.MediaResource
import kotlinx.parcelize.Parcelize

interface MediaFilter: Parcelable {

    fun ignoreMedia(mediaResource: MediaResource): Boolean

    fun selectMedia(mediaResource: MediaResource): Boolean
}

@Parcelize
class DefaultMediaFilter(
    private val ignoreMimeType: Set<String> = emptySet(),
    private val ignoreResourceUri: Set<Uri> = emptySet(),
    private val selectedResourceUri: Set<Uri> = emptySet(),
): MediaFilter {

    override fun ignoreMedia(mediaResource: MediaResource): Boolean {
        return ignoreMimeType.contains(mediaResource.mimeType)
                || ignoreResourceUri.contains(mediaResource.uri)
    }

    override fun selectMedia(mediaResource: MediaResource): Boolean {
        return selectedResourceUri.contains(mediaResource.uri)
    }

}