package com.imagepick.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.imagepick.model.ImageParameter
import com.imagepick.model.MediaResource
import com.imagepick.ui.activity.ImagePickActivity

@Suppress("DEPRECATION")
class ImagePickContract : ActivityResultContract<ImageParameter, List<MediaResource>?>() {

    override fun createIntent(context: Context, input: ImageParameter): Intent {
        val intent = Intent(context, ImagePickActivity::class.java)
        intent.putExtra(ImageParameter::class.java.name, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<MediaResource>? {
        val result = if (resultCode == Activity.RESULT_OK && intent != null) {
            intent.getParcelableArrayListExtra<MediaResource>(MediaResource::class.java.name)
        } else {
            null
        }
        return if (result.isNullOrEmpty()) {
            null
        } else {
            result
        }
    }
}