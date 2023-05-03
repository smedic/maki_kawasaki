package com.example.myapplication.data.network

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {
    override suspend fun login(): Boolean {
        return authService.login()
    }
}