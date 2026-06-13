package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.farmers.ui.theme.*

@Composable
fun WelcomeScreen(
    onPhoneLoginClick: () -> Unit = {},
    onEmailLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onGoogleClick: (String) -> Unit = {} // Now takes the selected email
) {
    var showGooglePicker by remember { mutableStateOf(false) }
    
    val googleAccounts = listOf(
        "kanamalanarendra1162.sse@saveeth.com",
        "arjun.singh@gmail.com",
        "narendra.farmers@gmail.com",
        "contact@agribot.ai",
        "support@farmersapp.in"
    )

    if (showGooglePicker) {
        val accountScrollState = rememberScrollState()
        AlertDialog(
            onDismissRequest = { showGooglePicker = false },
            title = { Text("Choose an account", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(accountScrollState),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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

                    // Add another account option
                    Surface(
                        onClick = { 
                            showGooglePicker = false
                            // Simulated: Open add account flow
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(32.dp).background(Color.LightGray.copy(alpha = 0.2f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PersonAdd, null, modifier = Modifier.size(18.dp), tint = Color.Gray)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Add another account", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color.Gray))
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE3F2FD), Color.White, Color(0xFFE8F5E9))
                )
            )
    ) {
        // UNIQUE STYLE: Background Glow Elements
        val infiniteTransition = rememberInfiniteTransition(label = "bgGlow")
        val glowScale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "glowScale"
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = (-50).dp)
                .graphicsLayer(scaleX = glowScale, scaleY = glowScale)
                .background(AgriGlow.copy(alpha = 0.2f), CircleShape)
        )
        
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 100.dp, y = 100.dp)
                .graphicsLayer(scaleX = glowScale, scaleY = glowScale)
                .background(AgriVibrantGreen.copy(alpha = 0.1f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // 1. Top Logo
            Surface(
                modifier = Modifier.size(90.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = BorderStroke(2.dp, AgriGlow),
                shadowElevation = 10.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = AgriVibrantGreen,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            // 2. Branding
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "AgriBot",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriDarkGreen,
                        letterSpacing = (-1).sp
                    )
                )
                Text(
                    text = "Smart Farming Meets AI Style",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = AgriGreen,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }

            // 3. Unique Image Frame
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = RoundedCornerShape(topStart = 80.dp, bottomEnd = 80.dp, topEnd = 24.dp, bottomStart = 24.dp),
                border = BorderStroke(3.dp, Color.White),
                shadowElevation = 15.dp
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1625246333195-78d9c38ad449?q=80&w=2070&auto=format&fit=crop"),
                    contentDescription = "Farmer AI",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Overlay Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
                            )
                        )
                )
            }

            // 4. Action Buttons (Refined Paths)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Phone Login (Primary for Farmers)
                Button(
                    onClick = onPhoneLoginClick,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgriGreen),
                    elevation = ButtonDefaults.buttonElevation(6.dp)
                ) {
                    Icon(Icons.Default.Phone, null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Login with Phone", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
                }

                // Email Login
                OutlinedButton(
                    onClick = onEmailLoginClick,
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(2.dp, AgriBlue)
                ) {
                    Icon(Icons.Default.Email, null, tint = AgriBlue, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Continue with Mail", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = AgriBlue)
                }

                // Sign Up
                TextButton(
                    onClick = onSignUpClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("New here? ", color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text("Sign Up Now", color = AgriVibrantGreen, fontWeight = FontWeight.ExtraBold)
                }

                // Google Social
                Surface(
                    onClick = { showGooglePicker = true },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.AccountCircle, null, modifier = Modifier.size(24.dp), tint = Color.Gray)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Sign in with Google", fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                }
            }

            // 5. Footer
            Text(
                text = "By continuing, you agree to our Terms & Privacy.",
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray.copy(alpha = 0.7f)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    FarmersTheme {
        WelcomeScreen()
    }
}
