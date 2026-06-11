package com.example.farmers.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun OnboardingScreen1(onNext: () -> Unit = {}, onBack: () -> Unit = {}, onSkip: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, AgriWhite)
                )
            )
    ) {
        // Back and Skip Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = AgriGreen
                )
            }
            
            TextButton(onClick = onSkip) {
                Text(
                    text = "Skip",
                    color = AgriGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Top Illustration Placeholder
            FarmingIllustration()

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = "Welcome to AgriBot",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = AgriGreen,
                    fontSize = 32.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "Your smart AI farming assistant for better crop growth and disease protection.",
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
                FeatureCard(
                    icon = Icons.Default.SmartToy,
                    text = "AI Farming Chatbot"
                )
                FeatureCard(
                    icon = Icons.Default.Grass,
                    text = "Smart Crop Suggestions"
                )
                FeatureCard(
                    icon = Icons.Default.PestControl,
                    text = "Instant Disease Detection"
                )
            }
        }

        // Bottom Section: Indicators and Next Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 48.dp, start = 24.dp, end = 24.dp)
        ) {
            // Page Indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                IndicatorDot(isActive = true)
                IndicatorDot(isActive = false)
                IndicatorDot(isActive = false)
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
fun FarmingIllustration() {
    Box(
        modifier = Modifier
            .size(240.dp)
            .background(AgriGlow.copy(alpha = 0.3f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Simple stylized elements for Illustration
        Icon(
            imageVector = Icons.Default.Agriculture,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = AgriGreen.copy(alpha = 0.6f)
        )
        
        Icon(
            imageVector = Icons.Default.SettingsSuggest,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-20).dp, y = 20.dp),
            tint = AgriAccent
        )

        Icon(
            imageVector = Icons.Default.PhoneAndroid,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomStart)
                .offset(x = 20.dp, y = (-20).dp),
            tint = AgriDarkGreen
        )
    }
}

@Composable
fun FeatureCard(icon: ImageVector, text: String) {
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
                    .background(AgriLightGreen.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AgriGreen,
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

@Composable
fun IndicatorDot(isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(width = if (isActive) 24.dp else 8.dp, height = 8.dp)
            .clip(CircleShape)
            .background(if (isActive) AgriGreen else AgriGlow)
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen1Preview() {
    FarmersTheme {
        OnboardingScreen1()
    }
}
