package com.learningcurve.clickitcameraapp.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.learningcurve.clickitcameraapp.presentation.theme.Dark700
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import com.learningcurve.clickitcameraapp.util.CoroutineThread
import kotlinx.coroutines.delay


@ExperimentalCoilApi
@Composable
fun EditImageContainer(
    imageBitmap: Bitmap,
    modifier: Modifier,
) {
    var isVisible by remember { mutableStateOf(true) }
    Box(modifier = modifier.background(Dark700)) {
        Image(
            bitmap = imageBitmap.asImageBitmap(),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Captured image"
        )
    }
    CoroutineThread.main {
        delay(500)
        isVisible = false
    }
}