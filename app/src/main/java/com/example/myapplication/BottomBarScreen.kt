package com.example.myapplication

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Orders : BottomBarScreen(
        route = "ORDERS",
        title = "Orders",
        icon = R.drawable.orders_icon
    )

    object Statements : BottomBarScreen(
        route = "STATEMENTS",
        title = "Statements",
        icon = R.drawable.statements_icon,
    )
}