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
fun OnboardingScreen2(onNext: () -> Unit = {}, onBack: () -> Unit = {}, onSkip: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFE3F2FD)) // Soft sky blue tint
                )
            )
    ) {
        // Skip Button
        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp)
        ) {
            Text(
                text = "Skip",
                color = AgriGreen,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Top Illustration Placeholder
            SmartFarmingIllustration()

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Smart Crop Recommendations",
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
                text = "Get AI-powered suggestions based on soil nutrients, weather conditions, and seasonal farming data.",
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
                AnalysisCard(
                    icon = Icons.Default.Science,
                    text = "Soil & NPK Analysis",
                    iconColor = Color(0xFF795548) // Earthy brown
                )
                AnalysisCard(
                    icon = Icons.Default.WbCloudy,
                    text = "Weather Prediction",
                    iconColor = Color(0xFF2196F3) // Sky blue
                )
                AnalysisCard(
                    icon = Icons.Default.Compost,
                    text = "Best Crop Suggestions",
                    iconColor = Color(0xFF4CAF50) // Leaf green
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
                IndicatorDot(isActive = true)
                IndicatorDot(isActive = false)
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

            // Next Button
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgriGreen),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .height(56.dp)
                    .width(130.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Composable
fun SmartFarmingIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "illustration")
    val floatAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    Box(
        modifier = Modifier
            .size(240.dp)
            .offset(y = floatAnim.dp),
        contentAlignment = Alignment.Center
    ) {
        // Decorative background shapes
        Box(modifier = Modifier.size(200.dp).background(Color(0xFFBBDEFB).copy(alpha = 0.4f), CircleShape))
        
        // AI & Data Icons
        Icon(
            imageVector = Icons.Default.CloudQueue,
            contentDescription = null,
            modifier = Modifier.size(64.dp).align(Alignment.TopStart).offset(x = 20.dp, y = 10.dp),
            tint = Color(0xFF2196F3).copy(alpha = 0.6f)
        )

        Icon(
            imageVector = Icons.Default.Leaderboard,
            contentDescription = null,
            modifier = Modifier.size(80.dp).align(Alignment.Center),
            tint = AgriGreen
        )

        Icon(
            imageVector = Icons.Default.Agriculture,
            contentDescription = null,
            modifier = Modifier.size(48.dp).align(Alignment.BottomEnd).offset(x = (-20).dp, y = (-10).dp),
            tint = Color(0xFF8D6E63)
        )

        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = null,
            modifier = Modifier.size(40.dp).align(Alignment.TopEnd).offset(x = (-10).dp, y = 20.dp),
            tint = Color(0xFFFFB300)
        )
    }
}

@Composable
fun AnalysisCard(icon: ImageVector, text: String, iconColor: Color) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.7f),
        border = CardDefaults.outlinedCardBorder(),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(iconColor.copy(alpha = 0.15f), CircleShape),
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
                    fontWeight = FontWeight.SemiBold,
                    color = AgriDarkGreen
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen2Preview() {
    FarmersTheme {
        OnboardingScreen2()
    }
}
