package com.imagepick.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import androidx.activity.result.contract.ActivityResultContract

internal class MatisseTakePictureContract : ActivityResultContract<TakePictureContractParams, Boolean>() {

    override fun createIntent(context: Context, input: TakePictureContractParams): Intent {
        val intent = Intent(ACTION_IMAGE_CAPTURE)
        val extra = input.extra
        if (!extra.isEmpty) {
            intent.putExtras(extra)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, input.uri)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }

}

internal data class TakePictureContractParams(
    val uri: Uri,
    val extra: Bundle
)