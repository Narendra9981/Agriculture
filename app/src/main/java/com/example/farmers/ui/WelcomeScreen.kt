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
        Pair("Kanamala Narendra", "kanamalanarendra950@gmail.com"),
        Pair("kanamala manideep", "manideepkanamala@gmail.com"),
        Pair("Clg Saveetha", "saveethaclg0@gmail.com"),
        Pair("Kanamala Narendra", "narendrakanamala7@gmail.com"),
        Pair("KANAMALA NARENDRA", "kanamalanarendra1162.sse@saveetha.com"),
        Pair("Kanamala Narendra", "narendrakanamala53@gmail.com"),
        Pair("Saveetha clg Saveetha clg", "ssaveethaclg0@gmail.com"),
        Pair("Kanamala Nani", "kanamalanani4@gmail.com")
    )

    if (showGooglePicker) {
        val accountScrollState = rememberScrollState()
        AlertDialog(
            onDismissRequest = { showGooglePicker = false },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Choose an account", 
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF202124),
                            fontSize = 24.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("to continue to ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF3C4043)))
                        Text("AgriBot", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF1A73E8), fontWeight = FontWeight.Medium))
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 450.dp)
                        .verticalScroll(accountScrollState)
                ) {
                    HorizontalDivider(color = Color(0xFFDADCE0), thickness = 0.5.dp)
                    googleAccounts.forEach { (name, email) ->
                        Surface(
                            onClick = { 
                                showGooglePicker = false
                                onGoogleClick(email) 
                            },
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Transparent
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 14.dp, horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(36.dp).background(
                                        when(name.take(1).uppercase()) {
                                            "K" -> Color(0xFF1A73E8)
                                            "S" -> Color(0xFF7B1FA2)
                                            else -> Color(0xFF0F9D58)
                                        }, 
                                        CircleShape
                                    ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(name.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        name, 
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF3C4043),
                                            fontSize = 14.sp
                                        )
                                    )
                                    Text(
                                        email, 
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(0xFF70757A),
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }
                        HorizontalDivider(color = Color(0xFFDADCE0), thickness = 0.5.dp)
                    }

                    // Use another account option
                    Surface(
                        onClick = { showGooglePicker = false },
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 14.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(36.dp).border(1.dp, Color(0xFFDADCE0), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AccountCircle, null, modifier = Modifier.size(24.dp), tint = Color(0xFF5F6368))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                "Use another account", 
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF3C4043),
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    HorizontalDivider(color = Color(0xFFDADCE0), thickness = 0.5.dp)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Before using this app, you can review AgriBot's privacy policy and terms of service.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF70757A), fontSize = 11.sp),
                        lineHeight = 16.sp
                    )
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showGooglePicker = false }) {
                    Text("Cancel", color = Color(0xFF1A73E8), fontWeight = FontWeight.Medium)
                }
            },
            shape = RoundedCornerShape(8.dp),
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
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF2F2F2),
                    border = BorderStroke(1.dp, Color(0xFFDADCE0).copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Official-looking Google "G" icon simulation
                        Box(
                            modifier = Modifier.size(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle, // Placeholder for G
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF4285F4) // Google Blue
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Continue with Google", 
                            fontWeight = FontWeight.Medium, 
                            color = Color(0xFF3C4043),
                            fontSize = 15.sp,
                            letterSpacing = 0.2.sp
                        )
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
