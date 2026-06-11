package com.example.farmers.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*

@Composable
fun SplashScreen(onTimeout: () -> Unit = {}) {
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    // Background glow animation
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    // Fade and Scale animations for entry
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1500),
        label = "alpha"
    )
    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        // Simulate loading or wait for timeout
        // kotlinx.coroutines.delay(3000)
        // onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(AgriWhite, Color.White, AgriLightGreen.copy(alpha = 0.3f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Subtle Background Patterns (Simplified "Fields")
        FarmingBackground(glowAlpha)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .graphicsLayer(
                    alpha = alphaAnim,
                    scaleX = scaleAnim,
                    scaleY = scaleAnim
                )
        ) {
            AgriBotLogo()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "AgriBot",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 42.sp,
                    letterSpacing = 2.sp
                ),
                color = AgriGreen
            )

            Text(
                text = "Smart AI Farming Assistant",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                ),
                color = AgriDarkGreen.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "An Intelligent Chatbot For Farmers With\nCrop And Disease Prediction",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = AgriDarkGreen.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 48.dp)
            )
        }

        // Bottom Loading Indicator
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .width(200.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = AgriGreen,
                trackColor = AgriGlow.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun AgriBotLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "logo")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(160.dp)
            .scale(pulseScale)
    ) {
        // Outer Glow
        Surface(
            modifier = Modifier
                .size(140.dp)
                .alpha(0.2f),
            shape = CircleShape,
            color = AgriGlow,
            shadowElevation = 8.dp
        ) {}

        // Main Logo Container
        Surface(
            modifier = Modifier.size(120.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 12.dp,
            tonalElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                // Background Gradient inside logo
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(AgriLightGreen.copy(alpha = 0.1f), Color.White)
                            )
                        )
                )

                // Combined Icons for AI + Nature
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .offset(x = (-8).dp, y = (-8).dp),
                    tint = AgriGreen
                )
                
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .offset(x = 12.dp, y = 12.dp),
                    tint = AgriAccent
                )
                
                // Tech/Circuit lines (Simulated)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = AgriGreen.copy(alpha = 0.3f),
                        start = Offset(size.width * 0.2f, size.height * 0.8f),
                        end = Offset(size.width * 0.4f, size.height * 0.6f),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = AgriGreen.copy(alpha = 0.3f),
                        start = Offset(size.width * 0.8f, size.height * 0.2f),
                        end = Offset(size.width * 0.6f, size.height * 0.4f),
                        strokeWidth = strokeWidth
                    )
                }
            }
        }
    }
}

@Composable
fun FarmingBackground(glowAlpha: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Simple hill/field shapes
        drawCircle(
            color = AgriLightGreen.copy(alpha = 0.05f * glowAlpha),
            radius = width * 0.8f,
            center = Offset(width * 1.2f, height * 1.1f)
        )
        
        drawCircle(
            color = AgriGreen.copy(alpha = 0.03f * glowAlpha),
            radius = width * 0.6f,
            center = Offset(-width * 0.2f, height * 1.05f)
        )
        
        // Sunrise glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(AgriAccent.copy(alpha = 0.1f * glowAlpha), Color.Transparent),
                center = Offset(width * 0.1f, height * 0.1f),
                radius = width * 0.4f
            ),
            radius = width * 0.4f,
            center = Offset(width * 0.1f, height * 0.1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FarmersTheme {
        SplashScreen()
    }
}
