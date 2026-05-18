package com.project.academy_hunt.data.auth

class AuthRepository(private val api: AuthApi) {

    suspend fun login(email: String, password: String): Result<AuthData> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        email   : String,
        password: String,
        name    : String,
        role    : String
    ): Result<AuthData> {
        return try {
            val response = api.register(RegisterRequest(email, password, name, role))
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}