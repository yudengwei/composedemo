package com.imagepick.matisse.internal.logic

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.imagepick.matisse.Matisse

class MatisseViewModel(application: Application, private val matisse: Matisse) :
    AndroidViewModel(application) {

    private val context: Context
        get() = getApplication()

    private val defaultBucketId = "&__matisseDefaultBucketId__&"


}