package com.example.myapplication.views.compose

data class FetchingError(
    val message: String = "",
    val title: String = "",
    val statusCode: Int = 500,
)