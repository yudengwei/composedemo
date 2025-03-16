package com.imagepick.matisse.internal.logic

import android.net.Uri
import android.os.Bundle

data class MatisseTakePictureContractParams(
    val uri: Uri,
    val extra: Bundle
)