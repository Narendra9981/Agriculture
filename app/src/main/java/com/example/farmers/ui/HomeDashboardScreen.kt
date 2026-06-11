package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.data.LanguageManager
import com.example.farmers.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboardScreen(
    onNavigate: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var startAnimations by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50)
        startAnimations = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = LanguageManager.getString("app_name"),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriDarkGreen,
                            letterSpacing = 1.sp
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { onNavigate("Profile") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = AgriGreen, modifier = Modifier.size(32.dp))
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = AgriGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Home",
                onHomeClick = { onNavigate("Home") },
                onChatClick = { onNavigate("Chat") },
                onScanClick = { onNavigate("Detection") },
                onSchemesClick = { onNavigate("Schemes") },
                onMarketClick = { onNavigate("Market") },
                onProfileClick = { onNavigate("Profile") }
            )
        },
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
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                // Welcome Section - Animated
                AnimatedVisibility(
                    visible = startAnimations,
                    enter = fadeIn(tween(800)) + slideInHorizontally(tween(800)) { -100 }
                ) {
                    Column {
                        Text(
                            text = LanguageManager.getString("welcome"),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                        )
                        Text(
                            text = LanguageManager.getString("dashboard_title"),
                            style = MaterialTheme.typography.bodyMedium.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Weather Widget - Animated
                AnimatedVisibility(
                    visible = startAnimations,
                    enter = fadeIn(tween(800, 200)) + scaleIn(tween(800, 200), initialScale = 0.9f)
                ) {
                    WeatherWidget()
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Feature Grid Header
                Text(
                    text = "Quick AI Tools",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                )
                Spacer(modifier = Modifier.height(16.dp))

                val features = listOf(
                    FeatureItem(LanguageManager.getString("crop_rec"), Icons.Default.Eco, AgriVibrantGreen, "CropRec"),
                    FeatureItem(LanguageManager.getString("disease_det"), Icons.Default.CenterFocusWeak, AgriOrange, "Detection"),
                    FeatureItem(LanguageManager.getString("weather"), Icons.Default.WbSunny, AgriBlue, "Weather"),
                    FeatureItem(LanguageManager.getString("fert_guide"), Icons.Default.Science, Color(0xFF795548), "Fertilizer"),
                    FeatureItem("Fertilizer Prediction", Icons.Default.Analytics, Color(0xFF673AB7), "FertPred"),
                    FeatureItem(LanguageManager.getString("schemes"), Icons.Default.AccountBalance, Color(0xFFE91E63), "Schemes")
                )

                // HIGHLY ANIMATED GRID
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    for (i in features.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AnimatedFeatureCard(
                                item = features[i],
                                isVisible = startAnimations,
                                delay = i * 100,
                                onClick = { onNavigate(features[i].route) },
                                modifier = Modifier.weight(1f)
                            )
                            if (i + 1 < features.size) {
                                AnimatedFeatureCard(
                                    item = features[i + 1],
                                    isVisible = startAnimations,
                                    delay = (i + 1) * 100,
                                    onClick = { onNavigate(features[i + 1].route) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // AI Assistant Banner - Animated
                AnimatedVisibility(
                    visible = startAnimations,
                    enter = fadeIn(tween(1000, 800)) + slideInVertically(tween(1000, 800)) { it / 2 }
                ) {
                    AiAssistantBanner(onClick = { onNavigate("Chat") })
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun AnimatedFeatureCard(
    item: FeatureItem,
    isVisible: Boolean,
    delay: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Scale on press
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "clickScale"
    )

    // Floating Animation
    val infiniteTransition = rememberInfiniteTransition(label = "cardEffects")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing, delayMillis = delay),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating"
    )

    // Icon Pulse
    val iconPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing, delayMillis = delay),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(800, delay)) + slideInVertically(tween(800, delay)) { 100 },
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .height(130.dp)
                .offset(y = floatOffset.dp)
                .graphicsLayer(scaleX = scale, scaleY = scale)
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(),
                    onClick = onClick
                ),
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            border = BorderStroke(2.dp, item.color.copy(alpha = 0.2f)),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .graphicsLayer(scaleX = iconPulse, scaleY = iconPulse)
                        .background(item.color.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(item.icon, contentDescription = null, tint = item.color, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriDarkGreen,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
fun WeatherWidget() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(32.dp),
        color = AgriBlue,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Ludhiana, Punjab", color = Color.White.copy(alpha = 0.8f), fontWeight = FontWeight.Bold)
                Text("32°C", style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.ExtraBold, color = Color.White))
                Text("Sunny • Humidity 45%", color = Color.White, fontWeight = FontWeight.Bold)
            }
            // Rotating Sun Icon
            val infiniteTransition = rememberInfiniteTransition(label = "rotate")
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(10000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "rotation"
            )
            Icon(
                Icons.Default.WbSunny, 
                contentDescription = null, 
                tint = AgriAccent, 
                modifier = Modifier.size(80.dp).graphicsLayer(rotationZ = rotation)
            )
        }
    }
}

@Composable
fun AiAssistantBanner(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(32.dp),
        color = AgriVibrantGreen.copy(alpha = 0.1f),
        border = BorderStroke(2.dp, AgriVibrantGreen.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Need help, Arjun?", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                Text("Ask AgriBot anything about your farm.", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = AgriGreen))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Chat Now", color = AgriVibrantGreen, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

data class FeatureItem(val title: String, val icon: ImageVector, val color: Color, val route: String)

@Preview(showBackground = true)
@Composable
fun HomeDashboardPreview() {
    FarmersTheme {
        HomeDashboardScreen()
    }
}
