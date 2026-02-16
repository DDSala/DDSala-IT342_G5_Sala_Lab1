@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.userauth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp // Corrected for deprecation
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF6200EE), Color(0xFF3700B3))
)

@Composable
fun LoginScreen(onLoginSuccess: (User) -> Unit, onNavigateToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(BackgroundGradient)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.padding(20.dp), tint = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Welcome Back", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Text("Login to your account", color = Color.White.copy(alpha = 0.7f))

            Spacer(modifier = Modifier.height(40.dp))

            ModernTextField(value = email, onValueChange = { email = it }, label = "Email", icon = Icons.Default.Email)
            Spacer(modifier = Modifier.height(16.dp))
            // Changed from .Password to .Lock as Password is often missing in default libs
            ModernTextField(value = password, onValueChange = { password = it }, label = "Password", icon = Icons.Default.Lock, isPassword = true)

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            val response = RetrofitClient.instance.login(LoginRequest(email, password))
                            if (response.isSuccessful) {
                                response.body()?.let { onLoginSuccess(it) }
                            } else {
                                Toast.makeText(context, "Invalid Login", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF6200EE))
            ) {
                Text("SIGN IN", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(onClick = onNavigateToRegister) {
                Text("Create new account", color = Color.White)
            }
        }

        Text(
            text = "Property of DDSala, Lab Activity",
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onBackToLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(BackgroundGradient)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Get Started", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Spacer(modifier = Modifier.height(40.dp))

            ModernTextField(value = username, onValueChange = { username = it }, label = "Username", icon = Icons.Default.Person)
            Spacer(modifier = Modifier.height(16.dp))
            ModernTextField(value = email, onValueChange = { email = it }, label = "Email", icon = Icons.Default.Email)
            Spacer(modifier = Modifier.height(16.dp))
            // Changed from .Password to .Lock
            ModernTextField(value = password, onValueChange = { password = it }, label = "Password", icon = Icons.Default.Lock, isPassword = true)

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            val request = mapOf("username" to username, "email" to email, "password" to password)
                            val response = RetrofitClient.instance.register(request)
                            if (response.isSuccessful) {
                                onRegisterSuccess()
                            } else {
                                Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF6200EE))
            ) {
                Text("CREATE ACCOUNT", fontWeight = FontWeight.Bold)
            }

            TextButton(onClick = onBackToLogin) {
                Text("Already have an account? Login", color = Color.White)
            }
        }

        Text(
            text = "Property of DDSala, Lab Activity",
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun DashboardScreen(user: User, onViewProfile: () -> Unit, onLogout: () -> Unit) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = { TextButton(onClick = onLogout) { Text("Logout", color = Color.Red) } },
            dismissButton = { TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") } }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Home", fontWeight = FontWeight.Bold) },
                // Switched to AutoMirrored ExitToApp
                actions = { IconButton(onClick = { showLogoutDialog = true }) { Icon(Icons.AutoMirrored.Filled.ExitToApp, null) } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(modifier = Modifier.size(60.dp), shape = CircleShape, color = Color.White) {
                        Icon(Icons.Default.Person, null, modifier = Modifier.padding(15.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Welcome,", fontSize = 14.sp)
                        Text(user.username, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onViewProfile,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.AccountBox, null)
                Spacer(modifier = Modifier.width(12.dp))
                Text("View Profile")
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Property of DDSala, Lab Activity",
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 10.dp),
                color = Color.Gray,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun ProfileScreen(user: User, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Person, null, modifier = Modifier.fillMaxSize().padding(25.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            ModernInfoCard("Username", user.username)
            ModernInfoCard("Email", user.email ?: "N/A")

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Property of DDSala, Lab Activity",
                modifier = Modifier.padding(bottom = 10.dp),
                color = Color.Gray,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
fun ModernTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isPassword: Boolean = false) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(label, color = Color.White.copy(alpha = 0.6f)) },
        leadingIcon = { Icon(icon, null, tint = Color.White) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.15f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@Composable
fun ModernInfoCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}