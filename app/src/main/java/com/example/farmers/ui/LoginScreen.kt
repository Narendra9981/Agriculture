package com.example.farmers.ui

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity
import com.example.farmers.ui.theme.*
import com.example.farmers.data.LanguageManager
import com.example.farmers.data.AuthManager
import com.example.farmers.data.FirebaseManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onCreateAccount: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onBack: () -> Unit = {},
    onGoogleClick: (String) -> Unit = {}
) {
    var showGooglePicker by remember { mutableStateOf(false) }
    val googleAccounts = listOf(
        "kanamalanarendra1162.sse@saveeth.com",
        "arjun.singh@gmail.com"
    )

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }
    
    var isLoading by remember { mutableStateOf(false) }
    var loadingText by remember { mutableStateOf("Processing...") }
    
    val context = LocalContext.current

    if (showGooglePicker) {
        AlertDialog(
            onDismissRequest = { showGooglePicker = false },
            title = { Text("Choose an account", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    googleAccounts.forEach { email ->
                        Surface(
                            onClick = { 
                                showGooglePicker = false
                                onGoogleClick(email) 
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF5F5F5),
                            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(32.dp).background(AgriGreen, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(email.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(email, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showGooglePicker = false }) {
                    Text("Cancel", color = AgriGreen, fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.White
        )
    }
    
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("971960077402-uo3013lpb6966c80pa9ooprhf98lnlmm.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                isLoading = true
                loadingText = "Signing in with Google..."
                AuthManager.signInWithGoogle(idToken) { success, error ->
                    isLoading = false
                    if (success) {
                        val currentAccount = FirebaseAuth.getInstance().currentUser
                        val uid = currentAccount?.uid ?: ""
                        val profile = mapOf(
                            "name" to (account.displayName ?: "Google User"),
                            "email" to (account.email ?: ""),
                            "timestamp" to System.currentTimeMillis()
                        )
                        FirebaseManager.saveUserProfile(uid, profile)
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Firebase Google Auth failed: $error", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(context, "Google Sign-In failed: ID Token missing", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            val statusCode = e.statusCode
            val errorMessage = when (statusCode) {
                10 -> "Developer Error (10): Ensure you have added your debug SHA-1 key to your Firebase Console settings."
                12500 -> "Configuration Error (12500): Check Play Services configuration and package name compatibility."
                12501 -> "Sign-In Canceled: Flow was closed by user or dismissed."
                12502 -> "In Progress: Sign-in request is already active."
                else -> "Google Error ($statusCode): ${e.localizedMessage ?: "Unknown cause"}"
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, AgriWhite, Color(0xFFF1F8E9))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Brand Logo
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(2.dp, AgriGlow),
                    shadowElevation = 6.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = null,
                            tint = AgriGreen,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = LanguageManager.getString("app_name"),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriGreen,
                        letterSpacing = (-1).sp
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                )
                Text(
                    text = "Sign in to access your dashboard",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray, fontWeight = FontWeight.Medium),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                // Login Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    border = BorderStroke(2.dp, AgriGlow),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Email field
                        SharedInputField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = "Email Address",
                            icon = Icons.Default.Email,
                            keyboardType = KeyboardType.Email
                        )

                        // Password field
                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            label = { Text("Password", fontWeight = FontWeight.Bold, color = AgriGreen) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.Lock, null, tint = AgriVibrantGreen) },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null, tint = AgriVibrantGreen)
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = AgriDarkGreen,
                                unfocusedTextColor = AgriDarkGreen,
                                focusedBorderColor = AgriVibrantGreen,
                                unfocusedBorderColor = AgriGlow
                            )
                        )

                        // Remember Me and Forgot Password
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(checkedColor = AgriGreen)
                                )
                                Text("Remember Me", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen))
                            }
                            TextButton(onClick = onForgotPassword) {
                                Text("Forgot Password?", color = AgriGreen, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                            }
                        }

                        // Sign In Button
                        Button(
                            onClick = {
                                if (emailInput.isBlank() || passwordInput.isBlank()) {
                                    Toast.makeText(context, "Please enter your email and password", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                
                                isLoading = true
                                loadingText = "Signing in..."
                                AuthManager.loginWithEmail(emailInput, passwordInput) { success, error ->
                                    isLoading = false
                                    if (success) {
                                        onLoginSuccess()
                                    } else {
                                        Toast.makeText(context, "Login failed: $error", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AgriGreen),
                            elevation = ButtonDefaults.buttonElevation(4.dp)
                        ) {
                            Text("Sign In", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                        }

                        // OR Divider
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            HorizontalDivider(Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                            Text("  OR  ", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                            HorizontalDivider(Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                        }

                        // Google Sign-In Button
                        OutlinedButton(
                            onClick = {
                                showGooglePicker = true
                            },
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, Color.LightGray.copy(alpha = 0.6f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AgriDarkGreen)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Google Logo",
                                    tint = AgriBlue,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("Sign in with Google", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Register Link
                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account? ", color = Color.Gray, fontWeight = FontWeight.Medium)
                    TextButton(onClick = onCreateAccount, contentPadding = PaddingValues(0.dp)) {
                        Text("Register Now", color = AgriGreen, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }

            // Elegant Loading Overlay
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = AgriGreen, strokeWidth = 4.dp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = loadingText,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FarmersTheme {
        LoginScreen()
    }
}
