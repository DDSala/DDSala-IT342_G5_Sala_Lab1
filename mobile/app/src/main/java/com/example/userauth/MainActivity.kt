package com.example.userauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.userauth.ui.theme.UserAuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserAuthTheme {
                AppNavHost() // This calls the function below
            }
        }
    }
}

@Composable
fun AppNavHost() {
    var currentScreen by remember { mutableStateOf("login") }
    var loggedInUser by remember { mutableStateOf<User?>(null) }

    when (currentScreen) {
        "login" -> LoginScreen(
            onLoginSuccess = { user ->
                loggedInUser = user
                currentScreen = "dashboard"
            },
            onNavigateToRegister = { currentScreen = "register" }
        )
        "register" -> RegisterScreen(
            onRegisterSuccess = { currentScreen = "login" },
            onBackToLogin = { currentScreen = "login" }
        )
        "dashboard" -> loggedInUser?.let { user ->
            DashboardScreen(
                user = user,
                onViewProfile = { currentScreen = "profile" },
                onLogout = {
                    loggedInUser = null
                    currentScreen = "login"
                }
            )
        }
        "profile" -> loggedInUser?.let { user ->
            ProfileScreen(
                user = user,
                onBack = { currentScreen = "dashboard" }
            )
        }
    }
}