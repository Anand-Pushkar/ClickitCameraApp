package com.learningcurve.clickitcameraapp.presentation.ui.edit

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.presentation.components.EditImageBottomBar
import com.learningcurve.clickitcameraapp.presentation.components.EditImageContainer
import com.learningcurve.clickitcameraapp.presentation.components.EditImageTopBar
import com.learningcurve.clickitcameraapp.presentation.components.PickImage
import com.learningcurve.clickitcameraapp.presentation.theme.ClickitTheme
import com.learningcurve.clickitcameraapp.presentation.ui.navigation.EMPTY_IMAGE_URI
import com.learningcurve.clickitcameraapp.util.toBitmap


@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalPermissionsApi
@Composable
fun EditScreen(
    passedUri: Uri,
    viewModel: EditImageViewModel,
    onBackPressed: () -> Unit,
) {
    ClickitTheme {
        val context = LocalContext.current

        when(passedUri) {
            EMPTY_IMAGE_URI -> {
                PickImage(
                    onReceivedImage = { galleryImageUri ->
                        viewModel.setImageUri(galleryImageUri)
                        viewModel.setOriginalUri(galleryImageUri)
                        galleryImageUri.toBitmap(context)?.let {
                            viewModel.setImageBitmap(it)
                            viewModel.setOriginalBitmap(it)
                        }
                    }
                )
            }
            else -> {
                if(viewModel.imageBitmap.value == null) {
                    viewModel.setImageUri(passedUri)
                    viewModel.setOriginalUri(passedUri)
                    passedUri.toBitmap(context)?.let {
                        viewModel.setImageBitmap(it)
                        viewModel.setOriginalBitmap(it)
                    }
                }
            }
        }

        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            },
        ) {
            EditScreenMainContent(
                viewModel = viewModel,
                onBackPressed = onBackPressed,
            )
        }
    }


}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun EditScreenMainContent(
    viewModel: EditImageViewModel,
    onBackPressed: () -> Unit,
) {

    val context = LocalContext.current
    val imageBitmap = viewModel.imageBitmap
    val imageAltered = viewModel.imageAltered

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, imageContainer, bottomBar) = createRefs()

        EditImageTopBar(
            modifier = Modifier.constrainAs(topBar) {
                width = Dimension.fillToConstraints
                height = Dimension.value(56.dp)
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            imageAltered = imageAltered.value,
            onSave = {
                viewModel.saveImage(
                    context,
                )
            },
            onBackPressed = onBackPressed,
            setImageAlteredFalse = { viewModel.imageAltered.value = false }
        )

        imageBitmap.value?.let {
            EditImageContainer(
                imageBitmap = it,
                modifier = Modifier.constrainAs(imageContainer) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBar.top)
                }
            )
        }

        EditImageBottomBar(
            modifier = Modifier.constrainAs(bottomBar) {
                width = Dimension.fillToConstraints
                height = Dimension.value(100.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            viewModel = viewModel
        )
    }
}









