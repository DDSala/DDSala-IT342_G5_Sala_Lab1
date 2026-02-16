package com.example.userauth

data class User(
    val username: String,
    val token: String,
    val email: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)