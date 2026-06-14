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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.farmers.data.AuthRepository
import com.example.farmers.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    onPhoneLoginClick: () -> Unit = {},
    onEmailLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(context) }
    var isLoading by remember { mutableStateOf(false) }

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

                // Google Social - Integrated with Credential Manager
                Surface(
                    onClick = { 
                        scope.launch {
                            isLoading = true
                            val success = authRepository.loginWithGoogle()
                            isLoading = false
                            if (success) onLoginSuccess()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(4.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFDADCE0)),
                    shadowElevation = 0.5.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color(0xFF4285F4))
                        } else {
                            AsyncImage(
                                model = "https://www.gstatic.com/images/branding/product/1x/googleg_48dp.png",
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "Continue with Google",
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF3C4043),
                                fontSize = 14.sp,
                                letterSpacing = 0.2.sp
                            )
                        }
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
