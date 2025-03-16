package com.imagepick.matisse.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.imagepick.matisse.Matisse
import com.imagepick.matisse.MediaResource

class MatisseContract: ActivityResultContract<Matisse, List<MediaResource>?>() {

    override fun createIntent(context: Context, input: Matisse): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): List<MediaResource>? {
        TODO("Not yet implemented")
    }
}