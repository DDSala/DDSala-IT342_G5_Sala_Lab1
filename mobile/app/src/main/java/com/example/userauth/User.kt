package com.example.userauth

data class User(
    val username: String,
    val token: String,
    val email: String? = null // Make this optional since the backend doesn't send it yet
)

data class LoginRequest(
    val email: String,
    val password: String
)