package com.learningcurve.clickitcameraapp.presentation.components

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.canhub.cropper.*
import com.learningcurve.clickitcameraapp.R
import com.learningcurve.clickitcameraapp.presentation.ui.edit.EditImageViewModel
import com.learningcurve.clickitcameraapp.util.TAG
import com.learningcurve.clickitcameraapp.util.toBitmap

@Composable
fun EditImageBottomBar(
    modifier: Modifier,
    viewModel: EditImageViewModel
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Undo(viewModel)
        Edit(viewModel)
    }
}

@Composable
fun Undo(
    viewModel: EditImageViewModel
){
    IconButton(
        onClick = {
            viewModel.imageAltered.value = true
            viewModel.undoEdit()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_undo),
            contentDescription = "undo",
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}


@Composable
fun Edit(
    viewModel: EditImageViewModel
){
    val context = LocalContext.current
    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {

            viewModel.imageAltered.value = true
            result.uriContent?.let { uri ->
                viewModel.setImageUri(uri)
                uri.toBitmap(context)?.let { bitmap ->
                    viewModel.setImageBitmap(bitmap)
                }
            }

        } else {
            // an error occurred cropping
            val exception = result.error
        }
    }

    IconButton(
        onClick = {
            imageCropLauncher.launch(
                options(uri = viewModel.imageUri.value) {
                    setGuidelines(CropImageView.Guidelines.ON)
                    setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                    setInitialCropWindowPaddingRatio(0f)
                    setAllowRotation(true)
                    setAllowFlipping(true)
                    setAllowCounterRotation(true)
                }
            )
        },
        enabled = true
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = "edit",
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}
