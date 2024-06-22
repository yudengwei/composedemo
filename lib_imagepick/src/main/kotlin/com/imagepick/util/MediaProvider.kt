package com.imagepick.util

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.imagepick.model.MediaBucket
import com.imagepick.model.MediaResource
import com.imagepick.model.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

internal object MediaProvider {

    suspend fun createImage(
        context: Context,
        imageName: String,
        mimeType: String,
        relativePath: String
    ): Uri? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val contentValues = ContentValues()
                contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, imageName)
                contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, mimeType)
                contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, relativePath)
                val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }
                context.contentResolver.insert(imageCollection, contentValues)
            } catch (e : Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun deleteUri(context: Context, uri : Uri) {
        withContext(Dispatchers.Default) {
            context.contentResolver.delete(uri, null, null)
        }
    }

    suspend fun loadResources(
        context: Context,
        mediaType: MediaType,
    ): List<MediaResource> {
        return withContext(context = Dispatchers.Default) {
            loadResources(context, generateSqlSelection(mediaType = mediaType), null) ?: emptyList()
        }
    }

    suspend fun loadResources(context: Context, uri: Uri): MediaResource? {
        return withContext(Dispatchers.Default) {
            val id = ContentUris.parseId(uri)
            val selection = MediaStore.MediaColumns._ID + " = " + id
            val resource = loadResources(
                context, selection, null
            )
            if (resource.isNullOrEmpty() || resource.size != 1) {
                return@withContext null
            }
            return@withContext resource[0]
        }
    }

    private fun generateSqlSelection(mediaType : MediaType) : String {
        val mimeTypeColumn = MediaStore.Images.Media.MIME_TYPE
        val imageSelection = "$mimeTypeColumn like 'image/%'"
        val videoSelection = "$mimeTypeColumn like 'video/%'"
        val selection = StringBuilder()
        when (mediaType) {
            MediaType.ImageAndVideo -> {
                selection.append("(")
                selection.append(imageSelection)
                selection.append(" or ")
                selection.append(videoSelection)
                selection.append(")")
            }
            MediaType.ImageOnly -> {
                selection.append(imageSelection)
            }
            is MediaType.MultipleMimeType -> {
                return mediaType.mimeTypes.joinToString(
                    separator = ",",
                    prefix = "$mimeTypeColumn in (",
                    postfix = ")",
                    transform = {
                        "'${it}'"
                    }
                )
            }
            MediaType.VideoOnly -> {
                selection.append(videoSelection)
            }
        }
        return selection.toString()
    }

    private suspend fun loadResources(
        context: Context,
        selection : String?,
        selectionArg : Array<String>?
    ) : List<MediaResource>? {
        return withContext(Dispatchers.IO) {
            val idColumn = MediaStore.MediaColumns._ID
            val dataColumn = MediaStore.MediaColumns.DATA
            val displayNameColumn = MediaStore.MediaColumns.DISPLAY_NAME
            val mimeTypeColumn = MediaStore.MediaColumns.MIME_TYPE
            val bucketIdColumn = MediaStore.MediaColumns.BUCKET_ID
            val bucketNameColumn = MediaStore.MediaColumns.BUCKET_DISPLAY_NAME
            val dataModifiedColumn = MediaStore.MediaColumns.DATE_MODIFIED
            val projection = arrayOf(
                idColumn,
                dataColumn,
                displayNameColumn,
                mimeTypeColumn,
                bucketIdColumn,
                bucketNameColumn
            )
            val contentUri = MediaStore.Files.getContentUri("external")
            val sortOrder = "$dataModifiedColumn DESC"
            val mediaSourceList = mutableListOf<MediaResource>()
            val mediaCursor = context.contentResolver.query(
                contentUri,
                projection,
                selection,
                selectionArg,
                sortOrder
            ) ?: return@withContext null
            mediaCursor.use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn, Long.MAX_VALUE)
                    val data = cursor.getString(dataColumn, "")
                    if (id == Long.MAX_VALUE || data.isBlank() || !File(data).exists()) {
                        continue
                    }
                    val name = cursor.getString(displayNameColumn, "")
                    val mimeType = cursor.getString(mimeTypeColumn, "")
                    val bucketId = cursor.getString(bucketIdColumn, "")
                    val bucketName = cursor.getString(bucketNameColumn, "")
                    val uri = ContentUris.withAppendedId(contentUri, id)
                    val mediaResource = MediaResource(
                        id = id,
                        path = data,
                        uri = uri,
                        name = name,
                        mimeType = mimeType,
                        bucketId = bucketId,
                        bucketName = bucketName
                    )
                    mediaSourceList.add(mediaResource)
                }
            }
            return@withContext mediaSourceList
        }
    }
}

private fun Cursor.getLong(columnName: String, default: Long): Long {
    return try {
        val columnIndex = getColumnIndexOrThrow(columnName)
        getLong(columnIndex)
    } catch (e: Throwable) {
        e.printStackTrace()
        default
    }
}

private fun Cursor.getString(columnName: String, default: String): String {
    return try {
        val columnIndex = getColumnIndexOrThrow(columnName)
        getString(columnIndex) ?: default
    } catch (e: Throwable) {
        e.printStackTrace()
        default
    }
}