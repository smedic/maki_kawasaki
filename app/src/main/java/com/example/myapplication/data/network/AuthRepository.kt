package com.example.myapplication.data.network

interface AuthRepository {
    suspend fun login(): Boolean
}