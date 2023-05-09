package com.solevo.dist.data.network

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {
    override suspend fun login(): Result<Boolean> {
        return Result.success(true)
    }
}