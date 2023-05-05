package com.example.myapplication.data.network

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {
    override suspend fun login(): Result<Boolean> {
        return Result.failure(Throwable())
    }
}