package com.learningcurve.clickitcameraapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.presentation.theme.ClickitTheme
import com.learningcurve.clickitcameraapp.presentation.ui.navigation.NavGraph
import com.learningcurve.clickitcameraapp.util.PermissionRequester
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PermissionRequester(context = LocalContext.current){
                ClickitTheme {
                    NavGraph()
                }
            }
        }
    }
}
