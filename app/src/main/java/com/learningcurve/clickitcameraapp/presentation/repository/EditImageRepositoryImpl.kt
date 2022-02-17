package com.learningcurve.clickitcameraapp.presentation.repository

import android.graphics.Bitmap
import android.util.Log
import com.learningcurve.clickitcameraapp.presentation.util.toJpeg
import com.learningcurve.clickitcameraapp.util.FileHelper
import com.learningcurve.clickitcameraapp.util.TAG
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditImageRepositoryImpl
@Inject
constructor(
    private val fileHelper: FileHelper
) : EditImageRepository {

    override suspend fun saveImage(filteredBitmap: Bitmap): String? {
        Log.d(TAG, "saveImage: here-----------------")
        return try {
            val mediaStorageDir = fileHelper.getMediaStorage()
            if (!mediaStorageDir.exists()){
                mediaStorageDir.mkdirs()
            }
            val fileExt = ".jpg"
            val fileName = "JE_IMG_${System.currentTimeMillis()}".toJpeg()
            val file = File(mediaStorageDir, fileName)
            saveFile(file = file, bitmap = filteredBitmap)
            fileName.removeSuffix(fileExt)
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    private fun saveFile(file: File, bitmap: Bitmap){
        with(FileOutputStream(file)){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            flush()
            close()
        }
    }
}