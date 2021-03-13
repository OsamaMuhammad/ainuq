package com.app.ainuq.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.*

object FileHelper {
    fun getFileFromBitmap(bitmap: Bitmap, context: Context): File {
        val filesDir = context.applicationContext.filesDir
        val fileImage = File(filesDir, "sample" + ".jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(fileImage)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
        }
        return fileImage
    }

    fun getBitmapFromUri(uri: Uri?, context: Context): Bitmap? {
        var parcelFD: ParcelFileDescriptor? = null
        var bitmap: Bitmap? = null
        try {
            parcelFD = context.contentResolver.openFileDescriptor(uri!!, "r")
            val imageSource = parcelFD!!.fileDescriptor

            // Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeFileDescriptor(imageSource, null, o)

            // the new size we want to scale to
            val REQUIRED_SIZE = 400

            // Find the correct scale value. It should be the power of 2.
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
//            while (true) {
//                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
//                    break
//                }
//                width_tmp /= 2
//                height_tmp /= 2
//                scale *= 2
//            }

            // decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2)
            return bitmap
            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            uploadImage(byteArray);*/
        } catch (e: FileNotFoundException) {
            // handle errors
        } finally {
            if (parcelFD != null) try {
                parcelFD.close()
            } catch (e: IOException) {
                // ignored
            }
        }
        return bitmap
    }

    fun getFileFromUri(uri: Uri, context: Context): File? {
        val filePath = getUriRealPath(context, uri)
        if (filePath != null && !filePath.isEmpty()) {
            val file = File(filePath)
            if (file.exists()) {
                return file
            }
        }
        return null
    }

    private fun getUriRealPath(ctx: Context, uri: Uri): String? {
        var ret: String? = ""
        ret = if (isAboveKitKat) {
            // Android OS above sdk version 19.
            getUriRealPathAboveKitkat(ctx, uri)
        } else {
            // Android OS below sdk version 19
            getImageRealPath(ctx.contentResolver, uri, null)
        }
        return ret
    }

    private fun getUriRealPathAboveKitkat(ctx: Context?, uri: Uri?): String? {
        var ret: String? = ""
        if (ctx != null && uri != null) {
            if (isContentUri(uri)) {
                ret = if (isGooglePhotoDoc(uri.authority)) {
                    uri.lastPathSegment
                } else {
                    getImageRealPath(ctx.contentResolver, uri, null)
                }
            } else if (isFileUri(uri)) {
                ret = uri.path
            } else if (isDocumentUri(ctx, uri)) {

                // Get uri related document id.
                val documentId = DocumentsContract.getDocumentId(uri)

                // Get uri authority.
                val uriAuthority = uri.authority
                if (isMediaDoc(uriAuthority)) {
                    val idArr = documentId.split(":".toRegex()).toTypedArray()
                    if (idArr.size == 2) {
                        // First item is document type.
                        val docType = idArr[0]

                        // Second item is document real id.
                        val realDocId = idArr[1]

                        // Get content uri by document type.
                        var mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        if ("image" == docType) {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        } else if ("video" == docType) {
                            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        } else if ("audio" == docType) {
                            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }

                        // Get where clause with real document id.
                        val whereClause = MediaStore.Images.Media._ID + " = " + realDocId
                        ret = getImageRealPath(ctx.contentResolver, mediaContentUri, whereClause)
                    }
                } else if (isDownloadDoc(uriAuthority)) {
                    // Build download uri.
                    val downloadUri = Uri.parse("content://downloads/public_downloads")

                    // Append download document id at uri end.
                    val downloadUriAppendId =
                        ContentUris.withAppendedId(downloadUri, java.lang.Long.valueOf(documentId))
                    ret = getImageRealPath(ctx.contentResolver, downloadUriAppendId, null)
                } else if (isExternalStoreDoc(uriAuthority)) {
                    val idArr = documentId.split(":".toRegex()).toTypedArray()
                    if (idArr.size == 2) {
                        val type = idArr[0]
                        val realDocId = idArr[1]
                        if ("primary".equals(type, ignoreCase = true)) {
                            ret = Environment.getExternalStorageDirectory()
                                .toString() + "/" + realDocId
                        }
                    }
                }
            }
        }
        return ret
    }

    /* Check whether current android os version is bigger than kitkat or not. */
    private val isAboveKitKat: Boolean
        private get() {
            var ret = false
            ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            return ret
        }

    /* Check whether this uri represent a document or not. */
    private fun isDocumentUri(ctx: Context?, uri: Uri?): Boolean {
        var ret = false
        if (ctx != null && uri != null) {
            ret = DocumentsContract.isDocumentUri(ctx, uri)
        }
        return ret
    }

    /* Check whether this uri is a content uri or not.
     *  content uri like content://media/external/images/media/1302716
     *  */
    private fun isContentUri(uri: Uri?): Boolean {
        var ret = false
        if (uri != null) {
            val uriSchema = uri.scheme
            if ("content".equals(uriSchema, ignoreCase = true)) {
                ret = true
            }
        }
        return ret
    }

    /* Check whether this uri is a file uri or not.
     *  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
     * */
    private fun isFileUri(uri: Uri?): Boolean {
        var ret = false
        if (uri != null) {
            val uriSchema = uri.scheme
            if ("file".equals(uriSchema, ignoreCase = true)) {
                ret = true
            }
        }
        return ret
    }

    /* Check whether this document is provided by ExternalStorageProvider. */
    private fun isExternalStoreDoc(uriAuthority: String?): Boolean {
        var ret = false
        if ("com.android.externalstorage.documents" == uriAuthority) {
            ret = true
        }
        return ret
    }

    /* Check whether this document is provided by DownloadsProvider. */
    private fun isDownloadDoc(uriAuthority: String?): Boolean {
        var ret = false
        if ("com.android.providers.downloads.documents" == uriAuthority) {
            ret = true
        }
        return ret
    }

    /* Check whether this document is provided by MediaProvider. */
    private fun isMediaDoc(uriAuthority: String?): Boolean {
        var ret = false
        if ("com.android.providers.media.documents" == uriAuthority) {
            ret = true
        }
        return ret
    }

    /* Check whether this document is provided by google photos. */
    private fun isGooglePhotoDoc(uriAuthority: String?): Boolean {
        var ret = false
        if ("com.google.android.apps.photos.content" == uriAuthority) {
            ret = true
        }
        return ret
    }

    /* Return uri represented document file real local path.*/
    private fun getImageRealPath(
        contentResolver: ContentResolver,
        uri: Uri,
        whereClause: String?
    ): String {
        var ret = ""

        // Query the uri with condition.
        val cursor = contentResolver.query(uri, null, whereClause, null, null)
        if (cursor != null) {
            val moveToFirst = cursor.moveToFirst()
            if (moveToFirst) {

                // Get columns name by uri type.
                var columnName = MediaStore.Images.Media.DATA
                if (uri === MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA
                } else if (uri === MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA
                } else if (uri === MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA
                }

                // Get column index.
                val imageColumnIndex = cursor.getColumnIndex(columnName)

                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex)
            }
        }
        return ret
    } /*private static String getRealPathFromUri(final Uri uri , Context context) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private static String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }*/
}