package com.imagepick.matisse.internal.activity

import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.MediaResource

class MatisseActivity: BaseCaptureActivity() {

    override val captureStrategy: CaptureStrategy
        get() = TODO("Not yet implemented")

    override fun dispatchTakePictureResult(mediaResource: MediaResource) {
    }

    override fun takePictureCanceled() {
    }
}