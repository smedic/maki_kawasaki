package com.solevo.dist.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solevo.dist.screens.BottomBarScreen
import com.solevo.dist.screens.DesignSystemScreen
import com.solevo.dist.screens.OrdersPage
import com.solevo.dist.screens.StatementsPage

@Composable
fun HomeNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Orders.route
    ) {
        composable(route = BottomBarScreen.Orders.route) {
            OrdersPage(
                onDesignSystemClick = {
                    navController.navigate(OrdersScreen.DesignSystem.route)
                }
            )
        }
        composable(route = OrdersScreen.DesignSystem.route) {
            DesignSystemScreen(onBackClicked = {
                navController.popBackStack()
            })
        }
        composable(route = BottomBarScreen.Statements.route) {
            StatementsPage(
                name = BottomBarScreen.Statements.route,
                onClick = { }
            )
        }
//        detailsNavGraph(navController = navController)
    }
}

//fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
//    navigation(
//        route = Graph.STATEMENTS,
//        startDestination = StatementsScreen.Information.route
//    ) {
//        composable(route = StatementsScreen.Information.route) {
//            ScreenContent(name = StatementsScreen.Information.route) {
//                navController.navigate(StatementsScreen.Overview.route)
//            }
//        }
//        composable(route = StatementsScreen.Overview.route) {
//            ScreenContent(name = StatementsScreen.Overview.route) {
//                navController.popBackStack(
//                    route = StatementsScreen.Information.route,
//                    inclusive = false
//                )
//            }
//        }
//    }
//}

sealed class OrdersScreen(val route: String) {
    object DesignSystem : OrdersScreen(route = "DESIGN_SYSTEM")
}

sealed class StatementsScreen(val route: String) {
    object Information : StatementsScreen(route = "INFORMATION")
    object Overview : StatementsScreen(route = "OVERVIEW")
}