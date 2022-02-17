package com.learningcurve.clickitcameraapp.presentation.ui.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.learningcurve.clickitcameraapp.presentation.ui.edit.EditImageViewModel
import com.learningcurve.clickitcameraapp.presentation.ui.edit.EditScreen
import com.learningcurve.clickitcameraapp.presentation.ui.home.HomeScreen
import com.learningcurve.clickitcameraapp.util.uriFromEncodedString
import java.io.File

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun NavGraph(
    startDestination: String = Screen.HOME_ROUTE.route,
) {
    val navController = rememberAnimatedNavController()



    BoxWithConstraints {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            home(
                navController = navController,
            )

            editScreen(
                navController = navController,
            )
        }
    }

}

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
fun NavGraphBuilder.home(
    navController: NavHostController,
) {
    composable(
        route = Screen.HOME_ROUTE.route
    ) { backStackEntry: NavBackStackEntry ->
        HomeScreen(
            onNavigateToEditScreen = { route ->
               if(backStackEntry.lifecycleIsResumed()){
                   navController.navigate(route)
               }
            },
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
fun NavGraphBuilder.editScreen(
    navController: NavHostController,
) {
    composable(
        route = Screen.EDIT_SCREEN_ROUTE.route + "/{imageUri}",
        arguments = listOf(navArgument("imageUri"){
            type = NavType.StringType
        })
    ) { backStackEntry: NavBackStackEntry ->

        backStackEntry.arguments?.getString("imageUri")?.let { encodedImageUri ->

            val viewModel = hiltViewModel<EditImageViewModel>()

            val uri = uriFromEncodedString(encodedImageUri)
            EditScreen(
                passedUri = uri,
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

    }

}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

