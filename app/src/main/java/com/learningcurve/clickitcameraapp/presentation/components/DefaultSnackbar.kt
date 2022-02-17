package com.learningcurve.clickitcameraapp.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learningcurve.clickitcameraapp.presentation.theme.black
import com.learningcurve.clickitcameraapp.util.TAG

@ExperimentalMaterialApi
@Composable
fun DefaultSnackbar(
    darkTheme: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Log.d(TAG, "DefaultSnackbar: here-----------------")
    SnackbarHost(
        modifier = modifier
            .padding(bottom = 48.dp),
        hostState = snackbarHostState,
        snackbar = { snackbarData ->
            Snackbar(
                modifier = Modifier
                    .padding(16.dp),
                content = {
                    Text(
                        text = snackbarData.message,
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                        color = if(darkTheme.value){ black } else { Color.White },
                    )
                },
                backgroundColor = if(darkTheme.value){ Color.White } else { black },
                action = {
                    snackbarData.actionLabel?.let { actonLabel ->
                        TextButton(
                            onClick = { onDismiss() }
                        ) {
                            Text(
                                text = actonLabel,
                                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                                color = if(darkTheme.value){ black } else { Color.White },
                            )
                        }
                    }
                },
                shape = MaterialTheme.shapes.small.copy(
                    topStart = CornerSize(24.dp),
                    topEnd = CornerSize(24.dp),
                    bottomEnd = CornerSize(24.dp),
                    bottomStart = CornerSize(24.dp)
                )
            )
        }
    )
}