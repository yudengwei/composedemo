package com.imagepick.matisse

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.imagepick.matisse.internal.MatisseCaptureActivity

class MatisseCaptureContract: ActivityResultContract<MatisseCapture, MediaResource?>() {

    override fun createIntent(context: Context, input: MatisseCapture): Intent {
        val intent = Intent(context, MatisseCaptureActivity::class.java)
        intent.putExtra(MatisseCapture::class.java.name, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): MediaResource? {
        return if (resultCode == Activity.RESULT_OK && intent != null) {
            intent.getParcelableExtra(MediaResource::class.java.name)
        } else {
            null
        }
    }
}