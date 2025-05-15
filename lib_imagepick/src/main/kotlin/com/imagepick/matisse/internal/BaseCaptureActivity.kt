package com.imagepick.matisse.internal

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.imagepick.R
import com.imagepick.matisse.CaptureStrategy
import com.imagepick.matisse.MediaResource
import com.imagepick.matisse.internal.logic.MatisseTakePictureContract
import com.imagepick.matisse.internal.logic.MatisseTakePictureContractParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseCaptureActivity: ComponentActivity() {

    protected abstract val captureStrategy: CaptureStrategy

    protected abstract fun dispatchTakePictureResult(mediaResource: MediaResource)

    protected abstract fun takePictureCanceled()

    private var tempImageUriForTakePicture: Uri? = null

    private val requestWriteExternalStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {granted ->
            if (granted) {
                requestCameraPermissionIfNeed()
            } else {
                showToast(R.string.matisse_write_external_storage_permission_denied)
                takePictureCanceled()
            }
        }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {granted ->
            if (granted) {
                takePicture()
            } else {
                showToast(R.string.matisse_camera_permission_denied)
                takePictureCanceled()
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(MatisseTakePictureContract()) { successful ->
            takePictureResult(successful)
        }

    protected fun requestTakePicture() {
        if(captureStrategy.shouldRequestWriteExternalStoragePermission(context = applicationContext)) {
            requestWriteExternalStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            requestCameraPermissionIfNeed()
        }
    }

    protected fun showToast(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun showToast(@StringRes strRes: Int) {
        showToast(getString(strRes))
    }

    private fun takePicture() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            tempImageUriForTakePicture = null
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (captureIntent.resolveActivity(packageManager) != null) {
                val imageUri = captureStrategy.createImageUri(applicationContext)
                if (imageUri != null) {
                    tempImageUriForTakePicture = imageUri
                    takePictureLauncher.launch(
                        MatisseTakePictureContractParams(
                            imageUri,
                            captureStrategy.getCaptureExtra()
                        )
                    )
                    return@launch
                }
            } else {
                showToast(R.string.matisse_no_apps_support_take_picture)
            }
            takePictureCanceled()
        }
    }

    private fun requestCameraPermissionIfNeed() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val cameraPermission = Manifest.permission.CAMERA
            val requirePermissionToTakePhotos = containsPermission(
                applicationContext,
                cameraPermission
            ) && !permissionGranted(
                context = applicationContext,
                permission = cameraPermission
            )
            if (requirePermissionToTakePhotos) {
                requestCameraPermissionLauncher.launch(cameraPermission)
            } else {
                takePicture()
            }
        }
    }

    private fun takePictureResult(successful: Boolean) {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val imageUri = tempImageUriForTakePicture
            tempImageUriForTakePicture = null
            if (imageUri != null) {
                if (successful) {
                    val resource = captureStrategy.loadResource(applicationContext, imageUri)
                    if (resource != null) {
                        dispatchTakePictureResult(resource)
                        return@launch
                    }
                } else {
                    captureStrategy.onTakePictureCanceled(applicationContext, imageUri)
                }
            }
            takePictureCanceled()
        }
    }

    private fun permissionGranted(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    protected fun permissionGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.all { permissionGranted(context, it) }
    }

    private suspend fun containsPermission(context: Context, permission: String): Boolean {
        return withContext(Dispatchers.Default) {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_PERMISSIONS
                )
                val permissions = packageInfo.requestedPermissions
                if (!permissions.isNullOrEmpty()) {
                    permissions.contains(permission)
                } else {
                    true
                }
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }
    }
}