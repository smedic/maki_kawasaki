package com.solevo.dist.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.solevo.dist.screens.CreatePinScreen
import com.solevo.dist.screens.LoginPage
import com.solevo.dist.screens.OtpCodePage
import com.solevo.dist.screens.SignInScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SignIn.route
    ) {
        composable(AuthScreen.SignIn.route) {
            SignInScreen(
                onSignIn = {
                    navController.navigate(AuthScreen.Login.route)
                },
            )
        }
        composable(AuthScreen.Login.route) {
            LoginPage(
                onCodeSent = {
                    navController.navigate(AuthScreen.OtpCode.route)
                },
            )
        }
        composable(AuthScreen.OtpCode.route) {
            OtpCodePage(
                onCodeConfirmed = {
                    navController.navigate(AuthScreen.CreatePin.route)
                },
            )
        }
        composable(AuthScreen.CreatePin.route) {
            CreatePinScreen(
                onFinish = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen(route = "SIGN_IN")
    object Login : AuthScreen(route = "LOGIN")
    object OtpCode : AuthScreen(route = "OTP")
    object CreatePin : AuthScreen(route = "CREATE_PIN")
}