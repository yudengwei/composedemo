package com.imagepick.matisse.internal

import android.content.Intent
import android.os.Bundle
import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.MatisseCapture
import com.imagepick.matisse.MediaResource

class MatisseCaptureActivity: BaseCaptureActivity() {

    private val matisseCapture by lazy(mode = LazyThreadSafetyMode.NONE) {
        intent.getParcelableExtra<MatisseCapture>(MatisseCapture::class.java.name)!!
    }

    override val captureStrategy: CaptureStrategy
        get() = matisseCapture.captureStrategy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestTakePicture()
    }

    override fun dispatchTakePictureResult(mediaResource: MediaResource) {
        val intent = Intent()
        intent.putExtra(MediaResource::class.java.name, mediaResource)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun takePictureCanceled() {
        setResult(RESULT_CANCELED)
        finish()
    }
}