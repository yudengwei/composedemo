package com.imagepick.matisse.internal.logic

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.imagepick.matisse.MediaResource
import com.imagepick.matisse.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

internal object MediaProvider {

    data class MediaInfo(
        val mediaId: Long,
        val bucketId: String,
        val bucketName: String,
        val uri: Uri,
        val path: String,
        val name: String,
        val mimeType: String
    )

    suspend fun createImage(
        context: Context,
        imageName: String,
        mimeType: String,
        relativePath: String
    ): Uri? {
        return withContext(Dispatchers.Default) {
            return@withContext try {
                val contentValue = ContentValues()
                contentValue.put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
                contentValue.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                contentValue.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath)
                val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
                context.contentResolver.insert(imageCollection, contentValue)
            } catch (e: Throwable) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun loadResources(context: Context, uri: Uri): MediaInfo? {
        return withContext(Dispatchers.Default) {
            val id = ContentUris.parseId(uri)
            val selection = MediaStore.MediaColumns._ID + "=" + id
            val resource = loadResources(
                context,
                selection,
                null,
            )
            if (resource.isNullOrEmpty() || resource.size != 1) {
                return@withContext null
            }
            return@withContext resource[0]
        }
    }

    suspend fun deleteMedia(context: Context, uri: Uri) {
        withContext(Dispatchers.Default) {
            try {
                context.contentResolver.delete(uri, null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun loadResources(
        context: Context,
        selection: String?,
        selectionArgs: Array<String>?,
    ): List<MediaInfo>? {
        return withContext(Dispatchers.Default) {
            val idColumn = MediaStore.MediaColumns._ID
            val dataColumn = MediaStore.MediaColumns.DATA
            val sizeColumn = MediaStore.MediaColumns.SIZE
            val displayNameColumn = MediaStore.MediaColumns.DISPLAY_NAME
            val mimeTypeColumn = MediaStore.MediaColumns.MIME_TYPE
            val bucketIdColumn = MediaStore.MediaColumns.BUCKET_ID
            val bucketDisplayNameColumn = MediaStore.MediaColumns.BUCKET_DISPLAY_NAME
            val dataModifiedColumn = MediaStore.MediaColumns.DATE_MODIFIED
            val project = arrayOf(
                idColumn, dataColumn, sizeColumn, displayNameColumn,
                mimeTypeColumn, bucketIdColumn, bucketDisplayNameColumn,
            )
            val contentUri = MediaStore.Files.getContentUri("external")
            val sortOrder = "$dataModifiedColumn DESC"
            val mediaResourceList = mutableListOf<MediaInfo>()
            try {
                val mediaCursor = context.contentResolver.query(
                    contentUri,
                    project,
                    selection,
                    selectionArgs,
                    sortOrder
                ) ?: return@withContext null
                mediaCursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val defaultId = Long.MAX_VALUE
                        val id = cursor.getLong(idColumn, defaultId)
                        val data = cursor.getString(dataColumn, "")
                        val size = cursor.getLong(sizeColumn, 0)
                        if (id == defaultId || data.isBlank() || size <= 0) {
                            continue
                        }
                        val file = File(data)
                        if (!file.isFile || !file.exists()) {
                            continue
                        }
                        val name = cursor.getString(displayNameColumn, "")
                        val mimeType = cursor.getString(mimeTypeColumn, "")
                        val bucketId = cursor.getString(bucketIdColumn, "")
                        val bucketName = cursor.getString(bucketDisplayNameColumn, "")
                        val uri = ContentUris.withAppendedId(contentUri, id)
                        mediaResourceList.add(MediaInfo(
                            mediaId = id,
                            path = data,
                            uri = uri,
                            name = name,
                            mimeType = mimeType,
                            bucketId =  bucketId,
                            bucketName = bucketName
                        ))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext mediaResourceList
        }
    }

    suspend fun loadResources(
        context: Context,
        mediaType: MediaType
    ): List<MediaInfo>? {
        return withContext(Dispatchers.Default) {
            loadResources(
                context,
                selection = generateSqlSelection(mediaType = mediaType),
                selectionArgs = null
            )
        }
    }

    private fun generateSqlSelection(mediaType: MediaType): String {
        val mediaTypeColumn = MediaStore.Files.FileColumns.MEDIA_TYPE
        val mediaTypeImageColumn = MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
        val mediaTypeVideoColumn = MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
        val mimeTypeColumn = MediaStore.Files.FileColumns.MIME_TYPE
        val queryImageSelection =
            "$mediaTypeColumn = $mediaTypeImageColumn and $mimeTypeColumn like 'image/%'"
        val queryVideoSelection =
            "$mediaTypeColumn = $mediaTypeVideoColumn and $mimeTypeColumn like 'video/%'"
        return when (mediaType) {
            is MediaType.ImageOnly -> {
                queryImageSelection
            }

            MediaType.VideoOnly -> {
                queryVideoSelection
            }

            is MediaType.ImageAndVideo -> {
                buildString {
                    append(queryImageSelection)
                    append(" or ")
                    append(queryVideoSelection)
                }
            }

            is MediaType.MultipleMimeType -> {
                mediaType.mimeTypes.joinToString(
                    prefix = "$mediaTypeColumn in (",
                    postfix = ")",
                    separator = ",",
                    transform = {
                        "'${it}'"
                    }
                )
            }
        }
    }
}

private fun Cursor.getLong(columnName: String, default: Long): Long {
    return try {
        val columnIndex = getColumnIndexOrThrow(columnName)
        getLong(columnIndex)
    } catch (e: Exception) {
        e.printStackTrace()
        default
    }
}

private fun Cursor.getString(columnName: String, default: String): String {
    return try {
        val columnIndex = getColumnIndexOrThrow(columnName)
        getString(columnIndex)
    }catch (e: Exception) {
        e.printStackTrace()
        default
    }
}