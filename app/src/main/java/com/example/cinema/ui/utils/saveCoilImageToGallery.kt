package com.example.cinema.ui.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.provider.MediaStore
import android.util.Log.e
import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import coil3.Bitmap

suspend fun saveCoilImageToGallery(context: Context, url: String, showShackBar: () -> Unit) {
    withContext(Dispatchers.IO) {
        try {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false)
                .build()
            val result = loader.execute(request)
            if (result is SuccessResult) {
                val bitmap = result.image.toBitmap()
                saveBitmapToGallery(context, bitmap)
                withContext(Dispatchers.Main) {
                    showShackBar()
                }
            } else {
                val throwable = (result as? coil3.request.ErrorResult)?.throwable
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Ошибка: ${throwable?.message ?: "Неизвестная ошибка"}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
    }
    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    uri?.let {
        resolver.openOutputStream(it)?.use { stream ->
            bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, stream)
        }
    }
}