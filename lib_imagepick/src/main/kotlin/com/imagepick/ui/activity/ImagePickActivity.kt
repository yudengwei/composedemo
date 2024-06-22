package com.imagepick.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imagepick.model.CaptureStrategy
import com.imagepick.model.ImageParameter
import com.imagepick.model.MediaResource
import com.imagepick.ui.compose.ImagePickPage
import com.imagepick.ui.compose.ImagePickTheme
import com.imagepick.ui.compose.ImagePreviewPage
import com.imagepick.ui.compose.LoadingDialog
import com.imagepick.viewmodel.ImagePickViewModel

@Suppress("DEPRECATION", "UNCHECKED_CAST")
class ImagePickActivity() : BaseCaptureActivity() {

    override val captureStrategy: CaptureStrategy
        get() = requireCaptureStrategy()

    override fun dispatchTakePictureResult(mediaResource: MediaResource) {
        Log.d("yudengwei", mediaResource.toString())
    }

    override fun takePictureCancelled() {
        Log.d("yudengwei", "takePictureCancelled")
    }

    private val viewModel by viewModels<ImagePickViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ImagePickViewModel(
                    application = this@ImagePickActivity.application,
                    imageParameter = intent.getParcelableExtra<ImageParameter>(ImageParameter::class.java.name) as ImageParameter
                    ) as T
            }
        }
    })

    private val requestPermissionLaunch =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            viewModel.requestReadMediaPermissionResult(result.all { it.value })
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ImagePickTheme {
                ImagePickPage(
                    imagePickViewModel = viewModel,
                    imageEngine = viewModel.imageEngine,
                    onRequestTakePicture = ::requestTakePicture,
                )
                ImagePreviewPage(
                    imageViewState = viewModel.imagePreviewPageViewState,
                )
                LoadingDialog(visible = viewModel.loadingDialogVisible)
            }
        }
        requestReadMediaPermission()
    }

    private fun requestReadMediaPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            && applicationInfo.targetSdkVersion >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionGranted(permission)) {
            viewModel.requestReadMediaPermissionResult(true)
        } else {
            requestPermissionLaunch.launch(permission)
        }
    }

    private fun permissionGranted(array: Array<String>) : Boolean {
        return array.all {
            permissionGranted(it)
        }
    }

    private fun permissionGranted(permission : String) : Boolean {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requireCaptureStrategy(): CaptureStrategy {
        val captureStrategy = viewModel.captureStrategy
        checkNotNull(captureStrategy)
        return captureStrategy
    }
}