package com.learningcurve.clickitcameraapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learningcurve.clickitcameraapp.presentation.theme.Light200
import com.learningcurve.clickitcameraapp.R

@Composable
fun EditImageTopBar(
    modifier: Modifier,
    imageAltered: Boolean,
    onSave: () -> Unit,
    onBackPressed: () -> Unit,
    setImageAlteredFalse: () -> Unit,
) {
    var isBack by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = "",
                color = Light200,
                fontSize = 18.sp
            )
        },
        modifier = modifier
            .background(color = MaterialTheme.colors.background),
        navigationIcon = {
            IconButton(
                onClick = {
                    isBack = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back icon",
                    tint = Light200,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onSave()
                    // after saving the image we don't want to show the alert dialogue
                    setImageAlteredFalse()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_save),
                    contentDescription = "edit / save",
                    tint = MaterialTheme.colors.onPrimary,
                )
            }
        },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.background,
    )

    if (isBack) {
        if (imageAltered) {
            AppAlertDialog(
                confirmButtonText = stringResource(id = R.string.dialog_confirm_text),
                dismissButtonText = stringResource(id = R.string.dialog_cancel_text),
                message = stringResource(id = R.string.dialog_discard_warning_text),
                onConfirm = {
                    onBackPressed()
                },
                onDismiss = {
                    isBack = false
                }
            )
        } else {
            onBackPressed()
            isBack = false
        }
    }

}