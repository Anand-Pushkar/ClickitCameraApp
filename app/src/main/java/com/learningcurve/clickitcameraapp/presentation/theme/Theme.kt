package com.learningcurve.clickitcameraapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.presentation.components.DefaultSnackbar

private val lightColors = lightColors(
    primary = pink200,
    onPrimary = white,

    background = black,

    secondary = pink500,

    primaryVariant = pinkDarkPrimary,
)

private val darkColors = darkColors(
    primary = pinkDarkPrimary,
    onPrimary = white,

    background = black,

    secondary = pink500,

    primaryVariant = pink200
)

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun ClickitTheme(
    darkTheme: MutableState<Boolean> = mutableStateOf(isSystemInDarkTheme()),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    content: @Composable() () -> Unit,
) {
    val colors = if (darkTheme.value) {
        darkColors
    } else {
        lightColors
    }
    
    MaterialTheme(
        colors = colors,
        typography = QuickSandTypography,
        shapes = Shapes,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
            DefaultSnackbar(
                darkTheme = darkTheme,
                snackbarHostState = scaffoldState.snackbarHostState,
                onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}