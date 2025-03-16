package com.imagepick.matisse

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Stable
import androidx.core.app.ActivityCompat
import com.imagepick.matisse.internal.logic.MediaProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val JPG_MIME_TYPE = "image/jpeg"

@Stable
interface CaptureStrategy: Parcelable {

    fun shouldRequestWriteExternalStoragePermission(context: Context): Boolean

    suspend fun createImageUri(context: Context): Uri?

    suspend fun loadResource(context: Context, imageUri: Uri): MediaResource?

    suspend fun onTakePictureCanceled(context: Context, imageUri: Uri)

    /**
     * 用于为相机设置启动参数
     * 返回值会传递给启动相机的 Intent
     */
    fun getCaptureExtra(): Bundle {
        return Bundle.EMPTY
    }

    /**
     * 生成图片名
     */
    suspend fun createImageName(context: Context): String {
        return withContext(Dispatchers.Default) {
            val time = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US).format(Date())
            return@withContext "IMG_$time.jpg"
        }
    }
}

/**
 *  通过 MediaStore 生成 ImageUri
 *  根据系统版本决定是否需要申请 WRITE_EXTERNAL_STORAGE 权限
 *  所拍的照片会保存在系统相册中
 */
@Parcelize
data class MediaStoreCaptureStrategy(private val extra: Bundle = Bundle.EMPTY): CaptureStrategy{

    override fun shouldRequestWriteExternalStoragePermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return false
        }
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    }

    override suspend fun createImageUri(context: Context): Uri? {
        return MediaProvider.createImage(
            context,
            imageName = createImageName(context),
            mimeType = JPG_MIME_TYPE,
            relativePath = "DCIM/Camera"
        )
    }

    override suspend fun loadResource(context: Context, imageUri: Uri): MediaResource? {
        val resource = MediaProvider.loadResources(context, imageUri)
        if (resource != null) {
            return resource
        }
        delay(250)
        return MediaProvider.loadResources(context = context, uri = imageUri)
    }

    override suspend fun onTakePictureCanceled(context: Context, imageUri: Uri) {
        MediaProvider.deleteMedia(context, imageUri)
    }

}