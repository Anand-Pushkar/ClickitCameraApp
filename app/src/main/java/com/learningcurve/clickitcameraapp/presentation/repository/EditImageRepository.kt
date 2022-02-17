package com.learningcurve.clickitcameraapp.presentation.repository

import android.graphics.Bitmap

interface EditImageRepository {
    suspend fun saveImage(filteredBitmap: Bitmap): String?
}