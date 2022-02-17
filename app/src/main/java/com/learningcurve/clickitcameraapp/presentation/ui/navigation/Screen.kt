package com.learningcurve.clickitcameraapp.presentation.ui.navigation

sealed class Screen(
    val route: String
) {
    object HOME_ROUTE: Screen("home")
    object EDIT_SCREEN_ROUTE: Screen("edit_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }


}