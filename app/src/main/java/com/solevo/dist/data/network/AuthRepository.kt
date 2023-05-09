package com.solevo.dist.data.network

interface AuthRepository {
    suspend fun login(): Result<Boolean>
}