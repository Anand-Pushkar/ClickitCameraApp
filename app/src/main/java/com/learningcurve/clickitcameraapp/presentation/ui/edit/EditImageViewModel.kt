package com.learningcurve.clickitcameraapp.presentation.ui.edit

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningcurve.clickitcameraapp.presentation.repository.EditImageRepository
import com.learningcurve.clickitcameraapp.presentation.util.saveImageToInternalStorage
import com.learningcurve.clickitcameraapp.util.SnackbarController
import com.learningcurve.clickitcameraapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditImageViewModel
@Inject
constructor(
    private val editImageRepository: EditImageRepository
):ViewModel() {

    private val _imageBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val imageBitmap: State<Bitmap?> = _imageBitmap

    private val _originalImageBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val originalImageBitmap: State<Bitmap?> = _originalImageBitmap

    private val _imageUri: MutableState<Uri?> = mutableStateOf(null)
    val imageUri: State<Uri?> = _imageUri

    private val _originalImageUri: MutableState<Uri?> = mutableStateOf(null)
    val originalImageUri: State<Uri?> = _originalImageUri

    val imageAltered: MutableState<Boolean> = mutableStateOf(false)
    val snackbarController = SnackbarController(viewModelScope)

    fun setImageBitmap(bitmap: Bitmap) {
        _imageBitmap.value = bitmap
    }

    fun setOriginalBitmap(bitmap: Bitmap) {
        _originalImageBitmap.value = bitmap
    }

    private fun undoBitmap() {
        _imageBitmap.value = _originalImageBitmap.value
    }

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun setOriginalUri(uri: Uri) {
        _originalImageUri.value = uri
    }

    private fun undoUri() {
        _imageUri.value = _originalImageUri.value
    }

    fun undoEdit() {
        undoUri()
        undoBitmap()
    }

    @ExperimentalMaterialApi
    fun saveImage(
        context: Context,
    ){
        _imageBitmap.value?.let { bitmap ->
            saveImageToInternalStorage(
                context,
                bitmap,
            )
        }
    }

}