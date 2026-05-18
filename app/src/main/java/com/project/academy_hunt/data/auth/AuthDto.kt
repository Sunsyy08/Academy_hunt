package com.project.academy_hunt.data.auth

data class LoginRequest(
    val email   : String,
    val password: String
)

data class RegisterRequest(
    val email   : String,
    val password: String,
    val name    : String,
    val role    : String  // "student" | "academy"
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data   : AuthData?
)

data class AuthData(
    val user : UserDto,
    val token: String
)

data class UserDto(
    val id   : Int,
    val email: String,
    val name : String,
    val role : String
)