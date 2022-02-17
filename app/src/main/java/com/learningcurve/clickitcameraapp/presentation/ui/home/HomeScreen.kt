package com.learningcurve.clickitcameraapp.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.presentation.components.CameraCapture
import java.io.File


@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    onNavigateToEditScreen: (String) -> Unit,
) {
    CameraCapture(
        modifier = Modifier.fillMaxSize(),
        onNavigateToEditScreen = onNavigateToEditScreen,
    )
}