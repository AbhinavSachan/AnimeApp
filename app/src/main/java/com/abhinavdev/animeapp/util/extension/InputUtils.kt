package com.abhinavdev.animeapp.util.extension

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import com.abhinavdev.animeapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Context?.captureImage(
    intent: ActivityResultLauncher<Intent>,
    isBackCam: Boolean
): Uri? {
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, createImageFileName())
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(
            MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/${this@captureImage?.getString(
                R.string.app_name)}")
    }
    val resolver = this?.contentResolver
    val mCapturedImageURI =
        resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI)
    val cam = if (isBackCam) {
        0
    } else {
        1
    }
    captureIntent.putExtra("android.intent.extras.CAMERA_FACING", cam)
    intent.launch(captureIntent)

    return mCapturedImageURI
}

fun Context?.createImageFileName(): String {
    // Create an image file name
    val timeStamp: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "${this?.getString(R.string.app_name)}_image_$timeStamp"
    return imageFileName
}

fun getFilePathFromContentUri(context: Context, uri: Uri?): String? {
    // DocumentProvider
    if (uri == null) return null
    if (DocumentsContract.isDocumentUri(
            context, uri
        )
    ) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), id.toLong()
            )
            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {

            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            when (type) {
                "image" -> {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

                "video" -> {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                }

                "audio" -> {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(
                split[1]
            )
            return getDataColumn(context, contentUri, selection, selectionArgs)
        }
    } else if (uri.scheme.equals("content", ignoreCase = true)) {

// Return the remote address
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            context, uri, null, null
        )
    } else if (uri.scheme.equals("file", ignoreCase = true)) {
        return uri.path
    }
    return null
}

private fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(
        column
    )
    try {
        cursor = context.contentResolver.query(
            uri!!, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

private fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}