package com.example.farmers.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*
import com.example.farmers.data.FirebaseManager
import com.example.farmers.data.AuthManager
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackToLogin: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    var stateExpanded by remember { mutableStateOf(false) }
    var selectedState by remember { mutableStateOf("Select State / District") }
    val states = listOf("Punjab", "Haryana", "Uttar Pradesh", "Maharashtra", "Gujarat", "Karnataka")

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isRegistering by remember { mutableStateOf(false) }
    var regStep by remember { mutableStateOf("Initializing Security...") }
    var regProgress by remember { mutableStateOf(0.1f) }

    Scaffold(
        topBar = {
            if (!isRegistering) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onBackToLogin) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = AgriDarkGreen)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, AgriWhite, Color(0xFFFFF8E1))
                    )
                )
                .padding(innerPadding)
        ) {
            if (isRegistering) {
                // AUTHENTICATION HANDSHAKE OVERLAY
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                        CircularProgressIndicator(color = AgriGreen, modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Secure Registration", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                        Spacer(modifier = Modifier.height(16.dp))
                        LinearProgressIndicator(progress = { regProgress }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = AgriVibrantGreen, trackColor = AgriGlow)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(regStep, style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.Bold))
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Eco, null, tint = AgriGreen, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AgriBot", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = AgriGreen))
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Create Your Account", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen))

                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        border = BorderStroke(2.dp, AgriGlow),
                        shadowElevation = 8.dp
                    ) {
                        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            SharedInputField(value = name, onValueChange = { name = it }, label = "Full Name", icon = Icons.Default.Person)
                            SharedInputField(value = mobile, onValueChange = { mobile = it }, label = "Mobile Number", icon = Icons.Default.Phone, keyboardType = KeyboardType.Phone)
                            SharedInputField(value = email, onValueChange = { email = it }, label = "Email Address", icon = Icons.Default.Email, keyboardType = KeyboardType.Email)

                            RegistrationPasswordField(value = password, onValueChange = { password = it }, label = "Password", visible = passwordVisible, onVisibilityChange = { passwordVisible = !passwordVisible })
                            RegistrationPasswordField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = "Confirm Password", visible = confirmPasswordVisible, onVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible })

                            ExposedDropdownMenuBox(expanded = stateExpanded, onExpandedChange = { stateExpanded = !stateExpanded }) {
                                OutlinedTextField(
                                    value = selectedState, onValueChange = {}, readOnly = true,
                                    modifier = Modifier.fillMaxWidth().menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                                    leadingIcon = { Icon(Icons.Default.LocationOn, null, tint = AgriVibrantGreen) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stateExpanded) },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = AgriDarkGreen, unfocusedTextColor = AgriDarkGreen, focusedBorderColor = AgriVibrantGreen, unfocusedBorderColor = AgriGlow)
                                )
                                ExposedDropdownMenu(expanded = stateExpanded, onDismissRequest = { stateExpanded = false }) {
                                    states.forEach { state -> DropdownMenuItem(text = { Text(state) }, onClick = { selectedState = state; stateExpanded = false }) }
                                }
                            }

                            Button(
                                onClick = {
                                    if (email.isBlank() || password.isBlank() || name.isBlank()) {
                                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }
                                    if (password != confirmPassword) {
                                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }
                                    
                                    isRegistering = true
                                    regStep = "Creating account..."
                                    regProgress = 0.3f
                                    
                                    AuthManager.signUpWithEmail(email, password) { success, error ->
                                        if (success) {
                                            regStep = "Saving profile..."
                                            regProgress = 0.7f
                                            
                                            // Get UID from AuthManager
                                            val uid = AuthManager.getUid().ifEmpty { email.replace(".", "_") }
                                            val profile = mapOf(
                                                "name" to name,
                                                "mobile" to mobile,
                                                "state" to selectedState,
                                                "timestamp" to System.currentTimeMillis()
                                            )
                                            
                                            // Save to database
                                            FirebaseManager.saveUserProfile(uid, profile)
                                            
                                            coroutineScope.launch {
                                                regStep = "Setup Complete!"
                                                regProgress = 1.0f
                                                delay(100)
                                                isRegistering = false
                                                onRegisterSuccess()
                                            }
                                        } else {
                                            isRegistering = false
                                            Toast.makeText(context, "Registration failed: $error", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AgriGreen)
                            ) {
                                Text("Register Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@Composable
fun RegistrationPasswordField(value: String, onValueChange: (String) -> Unit, label: String, visible: Boolean, onVisibilityChange: () -> Unit) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label, fontWeight = FontWeight.Bold, color = AgriGreen) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = { Icon(Icons.Default.Lock, null, tint = AgriVibrantGreen) },
        trailingIcon = {
            val image = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = onVisibilityChange) { Icon(image, null, tint = AgriVibrantGreen) }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = AgriDarkGreen, unfocusedTextColor = AgriDarkGreen, focusedBorderColor = AgriVibrantGreen, unfocusedBorderColor = AgriGlow)
    )
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    FarmersTheme {
        RegistrationScreen()
    }
}
