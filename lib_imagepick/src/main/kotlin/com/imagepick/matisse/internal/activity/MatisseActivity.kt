package com.imagepick.matisse.internal.activity

import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.MediaResource

class MatisseActivity: BaseCaptureActivity() {

    private val matisseViewModel by viewModels<

    override val captureStrategy: CaptureStrategy
        get() = TODO("Not yet implemented")

    override fun dispatchTakePictureResult(mediaResource: MediaResource) {
        TODO("Not yet implemented")
    }

    override fun takePictureCanceled() {
        TODO("Not yet implemented")
    }
}