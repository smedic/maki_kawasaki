package com.solevo.dist.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solevo.dist.screens.SplashScreen
import com.solevo.dist.screens.TabsScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            TabsScreen()
        }
        composable(route = Graph.SPLASH) {
            SplashScreen(onLoadingEnd = {
                navController.popBackStack()
                navController.navigate(it)
            })
        }
    }
}

object Graph {
    const val SPLASH = "splash_graph"
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}