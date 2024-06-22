package com.imagepick.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface MediaType : Parcelable {

    @Parcelize
    data object ImageOnly : MediaType

    @Parcelize
    data object VideoOnly : MediaType

    @Parcelize
    data object ImageAndVideo : MediaType

    @Parcelize
    data class MultipleMimeType(val mimeTypes : Set<String>) : MediaType
}