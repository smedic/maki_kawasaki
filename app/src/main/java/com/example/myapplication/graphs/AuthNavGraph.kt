package com.example.myapplication.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myapplication.screens.LoginPage
import com.example.myapplication.screens.OtpCodePage

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginPage(
                onCodeSent = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.OtpCode.route)
                },
            )
        }
        composable(AuthScreen.OtpCode.route) {
            OtpCodePage(
                onCodeConfirmed = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object OtpCode : AuthScreen(route = "OTP")
}