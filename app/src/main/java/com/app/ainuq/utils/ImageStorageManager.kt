package com.app.ainuq.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import timber.log.Timber
import java.io.File
import java.io.File.separator
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream


class ImageStorageManager {
    companion object {
        fun saveToInternalStorage(
            context: Context,
            bitmapImage: Bitmap,
            imageFileName: String
        ): String {
            context.openFileOutput(imageFileName, Context.MODE_PRIVATE).use { fos ->
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 25, fos)
            }
            return context.filesDir.absolutePath
        }

        fun getImageFromInternalStorage(context: Context, imageFileName: String): Bitmap? {
            val directory = context.filesDir
            val file = File(directory, imageFileName)
            return BitmapFactory.decodeStream(FileInputStream(file))
        }

        fun deleteImageFromInternalStorage(context: Context, imageFileName: String): Boolean {
            val dir = context.filesDir
            val file = File(dir, imageFileName)
            return file.delete()
        }

        fun isStoragePermissionGranted(activity: Activity): Boolean {
            val TAG = "Storage Permission"
            return if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Timber.tag(TAG).v("Permission is granted")
                true
            } else {
                Timber.tag(TAG).v("Permission is revoked")
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        }


        fun saveImage(bitmap: Bitmap, activity: Activity, context: Context, folderName: String): Uri? {

            if (isStoragePermissionGranted(activity)) {

                var uri: Uri? = null
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    val values = contentValues()
                    values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
                    values.put(MediaStore.Images.Media.IS_PENDING, true)
                    // RELATIVE_PATH and IS_PENDING are introduced in API 29.

                    uri = context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
                    if (uri != null) {
                        saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                        values.put(MediaStore.Images.Media.IS_PENDING, false)
                        context.contentResolver.update(uri, values, null, null)
                    }
                } else {
                    val directory = File(
                        Environment.getExternalStorageDirectory()
                            .toString() + separator + folderName
                    )
                    // getExternalStorageDirectory is deprecated in API 29

                    if (!directory.exists()) {
                        directory.mkdirs()
                    }
                    val fileName = System.currentTimeMillis().toString() + ".jpeg"
                    val file = File(directory, fileName)
                    saveImageToStream(bitmap, FileOutputStream(file))
                    if (file.absolutePath != null) {
                        val values = contentValues()
                        values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                        // .DATA is deprecated in API 29
                        context.contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                        )
                    }
                    uri = Uri.parse(file.absolutePath)

                }
                Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
                return uri
            }
            return null
        }

        private fun contentValues(): ContentValues {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            return values
        }

        private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
            if (outputStream != null) {
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
            var width = image.width
            var height = image.height
            val bitmapRatio = width.toFloat() / height.toFloat()
            if (bitmapRatio > 1) {
                width = maxSize
                height = (width / bitmapRatio).toInt()
            } else {
                height = maxSize
                width = (height * bitmapRatio).toInt()
            }
            return Bitmap.createScaledBitmap(image, width, height, true)
        }

    }



}