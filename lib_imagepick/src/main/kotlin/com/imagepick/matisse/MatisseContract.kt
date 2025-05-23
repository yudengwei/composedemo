package com.imagepick.matisse

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.IntentCompat
import com.imagepick.matisse.internal.MatisseActivity

class MatisseContract: ActivityResultContract<Matisse, List<MediaResource>?>() {

    override fun createIntent(context: Context, input: Matisse): Intent {
        val intent = Intent(context, MatisseActivity::class.java)
        intent.putExtra(Matisse::class.java.name, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<MediaResource>? {
        val result = if (resultCode == Activity.RESULT_OK && intent != null) {
            IntentCompat.getParcelableArrayListExtra(
                intent,
                MediaResource::class.java.name,
                MediaResource::class.java
            )
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