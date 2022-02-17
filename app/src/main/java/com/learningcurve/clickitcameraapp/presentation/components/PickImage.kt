package com.learningcurve.clickitcameraapp.presentation.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.util.CoroutineThread
import com.learningcurve.clickitcameraapp.util.PermissionRequester
import com.learningcurve.clickitcameraapp.util.TAG

@ExperimentalPermissionsApi
@Composable
fun PickImage(
    onReceivedImage: (Uri) -> Unit,
) {
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                Log.d(TAG, "PickImage: uri received = $it")
                onReceivedImage(it)
            }
        }
    )

    PermissionRequester(context = context) {
        LaunchedEffect(key1 = true) {
            CoroutineThread.io {
                imagePickerLauncher.launch("image/*")
            }
        }
    }
}