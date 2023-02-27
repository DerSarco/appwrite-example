package com.dersarco.appwriteexample.appwrite

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.File
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.FileOutputStream

class AppWriteStorage(private val context: Context, appWriteInstance: AppWriteInstance) {

    private val storage = Storage(appWriteInstance.appWriteClient)

    @SuppressLint("Recycle")
    suspend fun uploadToStorage(uri: Uri?): AppWriteResponse<File> {
        return try {
            if (uri != null) {
                val descriptor = context.contentResolver.openFileDescriptor(uri, "r", null)
                val file = java.io.File(context.cacheDir, context.contentResolver.getFileName(uri))
                val inputStream = FileInputStream(descriptor!!.fileDescriptor)
                val outputStream =
                    withContext(Dispatchers.IO) {
                        FileOutputStream(file)
                    }
                inputStream.copyTo(outputStream)
                val response = storage.createFile(
                    bucketId = BUCKET_ID,
                    fileId = ID.unique(),
                    file = InputFile.fromFile(file)
                )
                AppWriteResponse.Success(response)
            } else {
                throw AppwriteException("Something went wrong", 9999)
            }
        } catch (e: AppwriteException) {
            e.printStackTrace()
            AppWriteResponse.Error(e.code ?: 0, e.message ?: "")
        } catch (e: Exception) {
            e.printStackTrace()
            AppWriteResponse.Error(0, e.message ?: "")
        }
    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }
}

private const val BUCKET_ID = "63fccb7b6230779b11b4"
