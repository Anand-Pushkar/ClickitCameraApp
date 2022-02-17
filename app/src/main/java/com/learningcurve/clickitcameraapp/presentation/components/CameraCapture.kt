package com.learningcurve.clickitcameraapp.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.R
import com.learningcurve.clickitcameraapp.presentation.theme.transparent
import com.learningcurve.clickitcameraapp.presentation.theme.transparent75
import com.learningcurve.clickitcameraapp.presentation.ui.navigation.Screen
import com.learningcurve.clickitcameraapp.presentation.util.executor
import com.learningcurve.clickitcameraapp.presentation.util.getCameraProvider
import com.learningcurve.clickitcameraapp.presentation.util.takePicture
import com.learningcurve.clickitcameraapp.util.uriToEncodedString
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("UnrememberedMutableState")
@ExperimentalPermissionsApi
@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    onNavigateToEditScreen: (String) -> Unit,
) {
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
    val imageCaptureUseCase by remember {
        mutableStateOf(
            ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
        )
    }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    Box(
        modifier = modifier
    ) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onUseCase = {
                previewUseCase = it
            }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomCenter),
        ) {

            Button(
                onClick = {
                    val encodeUri = uriToEncodedString()
                    val route = Screen.EDIT_SCREEN_ROUTE.withArgs(encodeUri)
                    onNavigateToEditScreen(route)
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colors.onPrimary, CircleShape)
                    .clip(CircleShape)
                    .border(4.dp, transparent75, CircleShape),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
            ) {
            }

            CapturePictureButton(
                modifier = Modifier
                    .size(110.dp)
                    .padding(16.dp),
                onClick = {

                    coroutineScope.launch {
                        imageCaptureUseCase.takePicture(
                            context.executor,
                        ).let { file ->
                            val uri = file.toUri()
                            val encodedUri = uriToEncodedString(uri)
                            val route = Screen.EDIT_SCREEN_ROUTE.withArgs(encodedUri)
                            onNavigateToEditScreen(route)
                        }
                    }
                }
            )

            IconButton(
                onClick = {
                    lensFacing =
                        if (lensFacing == CameraSelector.LENS_FACING_FRONT) CameraSelector.LENS_FACING_BACK
                        else CameraSelector.LENS_FACING_FRONT
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_rotate),
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(2.dp)
                        .background(transparent, CircleShape)
                        .clip(CircleShape),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }

        LaunchedEffect(lensFacing) {
            val cameraProvider = context.getCameraProvider()
            try {
                // Must unbind the use-cases before rebinding them.
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                )
            } catch (ex: Exception) {
                Log.e("CameraCapture", "Failed to bind camera use cases", ex)
            }
        }
    }
}


