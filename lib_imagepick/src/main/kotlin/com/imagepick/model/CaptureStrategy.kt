package com.imagepick.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.imagepick.util.MediaProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Stable
interface CaptureStrategy : Parcelable {

    fun shouldRequestWriteExternalStoragePermission(context : Context) : Boolean

    suspend fun createImageUri(context : Context) : Uri?

    suspend fun loadResource(context: Context, imageUri : Uri) : MediaResource?

    suspend fun onTakePictureCanceled(context: Context, imageUri : Uri)

    suspend fun createImageName(context: Context) : String {
        return withContext(Dispatchers.IO) {
            val time = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US).format(Date())
            "IMG_${time}.jpg"
        }
    }

    fun getCaptureExtra() : Bundle {
        return Bundle.EMPTY
    }
}

private const val JPG_MIME_TYPE = "image/jpeg"

/**
 *  通过 FileProvider 生成 ImageUri
 *  外部必须配置 FileProvider，通过 authority 来实例化 FileProviderCaptureStrategy
 *  此策略无需申请任何权限，所拍的照片不会保存在系统相册里
 */

@Parcelize
data class FileProviderCaptureStrategy(
    private val authority: String,
    private val extra: Bundle = Bundle.EMPTY
) : CaptureStrategy {

    @IgnoredOnParcel
    private val uriFileMap = mutableMapOf<Uri, File>()

    override fun shouldRequestWriteExternalStoragePermission(context: Context): Boolean {
        return false
    }

    override suspend fun createImageUri(context: Context): Uri? {
        return withContext(Dispatchers.IO) {
            try {
                createTempFile(context)?.let {tempFile->
                    Log.d("yudengwei", "authority: $authority")
                    val uri = FileProvider.getUriForFile(context, authority, tempFile)
                    uriFileMap[uri] = tempFile
                    return@withContext uri
                }
            } catch (e : Exception) {
                Log.d("yudengwei", "error: ${e.message}")
                e.printStackTrace()
            }
            return@withContext null
        }

    }

    override suspend fun loadResource(context: Context, imageUri: Uri) : MediaResource? {
        return withContext(Dispatchers.IO) {
            val tempFile = uriFileMap[imageUri]
            if (tempFile != null) {
                uriFileMap.remove(imageUri)
                if (!tempFile.exists()) {
                    return@withContext null
                }
                val imagePath = tempFile.absolutePath
                val name = tempFile.name
                return@withContext MediaResource(
                    id = 0,
                    bucketId = "",
                    bucketName = "",
                    uri = imageUri,
                    path = imagePath,
                    name = name,
                    mimeType = JPG_MIME_TYPE
                )
            }
            return@withContext null
        }
    }

    override suspend fun onTakePictureCanceled(context: Context, imageUri: Uri) {
        withContext(Dispatchers.IO) {
            val tempFile = uriFileMap[imageUri]
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete()
            }
            uriFileMap.remove(imageUri)
        }
    }

    private suspend fun createTempFile(context : Context) : File? {
        return withContext(Dispatchers.IO) {
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File(storageDir, createImageName(context))
            if (file.createNewFile()) {
                file
            } else {
                null
            }
        }
    }

    override fun getCaptureExtra(): Bundle {
        return extra
    }
}

/**
 *  通过 MediaStore 生成 ImageUri
 *  根据系统版本决定是否需要申请 WRITE_EXTERNAL_STORAGE 权限
 *  所拍的照片会保存在系统相册中
 */
@Parcelize
data class MediaStoreCaptureStrategy(private val extra: Bundle = Bundle.EMPTY) : CaptureStrategy {

    /**
     * android 10开始不需要写权限
     */
    override fun shouldRequestWriteExternalStoragePermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return false
        }
        return ActivityCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
    }

    override suspend fun createImageUri(context: Context): Uri? {
        return MediaProvider.createImage(
            context,
            createImageName(context),
            JPG_MIME_TYPE,
            "DCIM/与等为"
        )
    }

    override suspend fun loadResource(context: Context, imageUri: Uri): MediaResource? {
        return MediaProvider.loadResources(context, imageUri)
    }

    override suspend fun onTakePictureCanceled(context: Context, imageUri: Uri) {
        MediaProvider.deleteUri(context, imageUri)
    }

    override fun getCaptureExtra(): Bundle {
        return extra
    }

}