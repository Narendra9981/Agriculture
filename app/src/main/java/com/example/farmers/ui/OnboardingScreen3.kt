package com.example.farmers.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*

@Composable
fun OnboardingScreen3(onGetStarted: () -> Unit = {}, onBack: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, AgriLightGreen.copy(alpha = 0.2f))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Top Illustration Placeholder: Disease Detection
            DiseaseDetectionIllustration()

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Instant Disease Detection",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = AgriGreen,
                    fontSize = 28.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "Upload or scan crop images to identify plant diseases and receive instant AI-based treatment solutions.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = AgriDarkGreen.copy(alpha = 0.7f),
                    lineHeight = 24.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Feature Cards
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PremiumFeatureCard(
                    icon = Icons.Default.DocumentScanner,
                    text = "AI Leaf Scanning",
                    iconColor = AgriGreen
                )
                PremiumFeatureCard(
                    icon = Icons.Default.Coronavirus,
                    text = "Disease Prediction",
                    iconColor = Color(0xFFD32F2F) // Deep Red for disease
                )
                PremiumFeatureCard(
                    icon = Icons.Default.MedicalServices,
                    text = "Treatment Suggestions",
                    iconColor = Color(0xFFFB8C00) // Orange for treatment/care
                )
            }
        }

        // Bottom Section: Indicators and Navigation Buttons
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 48.dp, start = 24.dp, end = 24.dp)
        ) {
            // Page Indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                IndicatorDot(isActive = false)
                IndicatorDot(isActive = false)
                IndicatorDot(isActive = true)
            }

            // Back Button
            TextButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Back",
                    color = AgriDarkGreen.copy(alpha = 0.6f),
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Get Started Button
            Button(
                onClick = onGetStarted,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgriGreen),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .height(56.dp)
                    .width(160.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun DiseaseDetectionIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "detection")
    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanLine"
    )

    Box(
        modifier = Modifier
            .size(240.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background Circle
        Box(modifier = Modifier.size(200.dp).background(AgriGlow.copy(alpha = 0.2f), CircleShape))
        
        // Leaf Icon
        Icon(
            imageVector = Icons.Default.Grass,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = AgriGreen.copy(alpha = 0.6f)
        )

        // Smartphone Frame (Stylized)
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 180.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE8F5E9))
            ) {
                // Scanning Line Animation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .offset(y = scanLineY.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, AgriGreen, Color.Transparent)
                            )
                        )
                )
                
                // Detection Point
                Icon(
                    imageVector = Icons.Default.CenterFocusWeak,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).align(Alignment.Center),
                    tint = AgriGreen.copy(alpha = 0.4f)
                )
            }
        }

        // Floating Tech Icons
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            modifier = Modifier.size(32.dp).align(Alignment.TopEnd).offset(x = (-10).dp, y = 20.dp),
            tint = AgriAccent
        )

        Icon(
            imageVector = Icons.Default.Healing,
            contentDescription = null,
            modifier = Modifier.size(40.dp).align(Alignment.BottomStart).offset(x = 10.dp, y = (-20).dp),
            tint = Color(0xFFFB8C00)
        )
    }
}

@Composable
fun PremiumFeatureCard(icon: ImageVector, text: String, iconColor: Color) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.8f),
        border = CardDefaults.outlinedCardBorder(),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(iconColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AgriDarkGreen
                )
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = AgriGreen.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen3Preview() {
    FarmersTheme {
        OnboardingScreen3()
    }
}
