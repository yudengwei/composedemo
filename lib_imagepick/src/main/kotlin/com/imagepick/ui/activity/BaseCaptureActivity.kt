package com.imagepick.ui.activity

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
import com.imagepick.contract.MatisseTakePictureContract
import com.imagepick.contract.TakePictureContractParams
import com.imagepick.model.CaptureStrategy
import com.imagepick.model.MediaResource
import com.imagepick.util.MediaProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseCaptureActivity : ComponentActivity() {

    private var tempImageUriForTakePicture: Uri? = null

    protected abstract val captureStrategy : CaptureStrategy

    protected abstract fun dispatchTakePictureResult(mediaResource: MediaResource)

    protected abstract fun takePictureCancelled()

    protected fun showToast(@StringRes id: Int) {
        showToast(message = getString(id))
    }

    protected fun showToast(message: String) {
        if (message.isNotBlank()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun permissionGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.all {
            permissionGranted(context = context, permission = it)
        }
    }

    private fun permissionGranted(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    protected fun requestTakePicture() {
        if (captureStrategy.shouldRequestWriteExternalStoragePermission(context = applicationContext)) {
            requestWriteExternalStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            requestCameraPermissionIfNeed()
        }
    }

    private fun takePicture() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            tempImageUriForTakePicture = null
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (captureIntent.resolveActivity(packageManager) != null) {
                val imageUri = captureStrategy.createImageUri(applicationContext)
                if (imageUri != null) {
                    tempImageUriForTakePicture = imageUri
                    takePictureLauncher.launch(TakePictureContractParams(
                        uri = imageUri,
                        extra = captureStrategy.getCaptureExtra()
                    ))
                }
            } else {
                showToast("没有可用于拍照的应用")
            }
            takePictureCancelled()
        }
    }

    private fun requestCameraPermissionIfNeed() {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val cameraPermission = Manifest.permission.CAMERA
            val requirePermission = containsPermission(
                context = applicationContext,
                permission = cameraPermission
            ) && !permissionGranted(
                context = applicationContext,
                permission = cameraPermission)
            if (requirePermission) {
                requestCameraPermissionLauncher.launch(cameraPermission)
            } else {
                takePicture()
            }
        }
    }

    private suspend fun containsPermission(context: Context, permission : String) : Boolean {
        return withContext(Dispatchers.Default) {
            try {
                val packageManager : PackageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_PERMISSIONS
                )
                val permissions = packageInfo.requestedPermissions
                if (!permissions.isNullOrEmpty()) {
                    return@withContext permissions.contains(permission)
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
            return@withContext false
        }
    }

    private fun takePictureResult(successful : Boolean) {
        lifecycleScope.launch(Dispatchers.Main.immediate) {
            val imageUri = tempImageUriForTakePicture
            tempImageUriForTakePicture = null
            if (imageUri != null) {
                if (successful) {
                    val resource = captureStrategy.loadResource(
                        context = applicationContext,
                        imageUri = imageUri
                    )
                    if (resource != null) {
                        dispatchTakePictureResult(resource)
                        return@launch
                    }
                } else {
                    captureStrategy.onTakePictureCanceled(
                        context = applicationContext,
                        imageUri = imageUri
                    )
                }
            }
            takePictureCancelled()
        }
    }

    private val requestWriteExternalStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {granted ->
            if (granted) {
                requestCameraPermissionIfNeed()
            } else {
                showToast("请授予存储写入权限后重试")
                takePictureCancelled()
            }
        }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {gradnted ->
            if (gradnted) {
                takePicture()
            } else {
                showToast("请授予拍照权限后重试")
                takePictureCancelled()
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(MatisseTakePictureContract()) {sucessful ->
            takePictureResult(successful = sucessful)
        }
}