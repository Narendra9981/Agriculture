package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastScreen(
    onBack: () -> Unit = {},
    onChatExpert: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WbCloudy, contentDescription = null, tint = AgriBlue, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Smart Weather Forecast", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Current Location", tint = AgriVibrantGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Weather",
                onHomeClick = onHomeClick,
                onChatClick = onChatExpert,
                onScanClick = onScanClick,
                onSchemesClick = onSchemesClick,
                onMarketClick = onMarketClick,
                onProfileClick = onProfileClick
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFE3F2FD), Color(0xFFF1F8E9))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Current Weather Hero
                WeatherHeroSection()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // 7-Day Forecast
                Text(
                    text = "7-Day Forecast",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                WeeklyForecastRow()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Farming Weather Insights
                Text(
                    text = "AI Farming Insights",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                FarmingWeatherInsights()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // AI Advice Card
                AiWeatherAdviceCard(onChatClick = onChatExpert)
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Climate Analytics
                Text(
                    text = "Climate Analytics",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                ClimateAnalyticsSection()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Weather Alert Section
                Text(
                    text = "Critical Alerts",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                WeatherAlertCard()
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun WeatherHeroSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriBlue.copy(alpha = 0.3f)),
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Ludhiana, Punjab",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                    )
                    Text(
                        text = "Tuesday, 15 April",
                        style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
                    )
                }
                Icon(Icons.Default.WbSunny, contentDescription = null, tint = AgriOrange, modifier = Modifier.size(56.dp))
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "32°C",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 72.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriDarkGreen
                )
            )
            Text(
                text = "Mostly Sunny",
                style = MaterialTheme.typography.headlineSmall.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherStatItem("Humidity", "45%", Icons.Default.WaterDrop, AgriBlue)
                WeatherStatItem("Wind", "12 km/h", Icons.Default.Air, Color(0xFF455A64))
                WeatherStatItem("UV Index", "6 (Med)", Icons.Default.WbSunny, AgriOrange)
            }
        }
    }
}

@Composable
fun WeatherStatItem(label: String, value: String, icon: ImageVector, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
        Text(text = label, style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray, fontWeight = FontWeight.Bold))
    }
}

data class ForecastDay(val name: String, val icon: ImageVector, val temp: String, val rain: String)

@Composable
fun WeeklyForecastRow() {
    val days = listOf(
        ForecastDay("Wed", Icons.Default.WbSunny, "31°/22°", "5%"),
        ForecastDay("Thu", Icons.Default.WbCloudy, "29°/20°", "15%"),
        ForecastDay("Fri", Icons.Default.CloudQueue, "28°/19°", "40%"),
        ForecastDay("Sat", Icons.Default.Thunderstorm, "25°/18°", "80%"),
        ForecastDay("Sun", Icons.Default.WbCloudy, "27°/19°", "30%")
    )
    
    LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        items(days) { day ->
            Surface(
                modifier = Modifier.width(110.dp).height(140.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = BorderStroke(2.dp, AgriBlue.copy(alpha = 0.2f)),
                shadowElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = day.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold))
                    Icon(day.icon, contentDescription = null, tint = AgriBlue, modifier = Modifier.size(36.dp))
                    Text(text = day.temp, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Opacity, contentDescription = null, modifier = Modifier.size(14.dp), tint = AgriBlue)
                        Text(text = day.rain, style = MaterialTheme.typography.labelMedium.copy(color = AgriGreen, fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun FarmingWeatherInsights() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        SharedInsightMiniCard(
            title = "Irrigation",
            value = "Best at 6PM",
            progress = 0.7f,
            color = AgriBlue,
            modifier = Modifier.weight(1f)
        )
        SharedInsightMiniCard(
            title = "Harvesting",
            value = "Ideal Today",
            progress = 0.9f,
            color = AgriVibrantGreen,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AiWeatherAdviceCard(onChatClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color(0xFFE8F5E9),
        border = BorderStroke(2.dp, AgriVibrantGreen.copy(alpha = 0.4f)),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(64.dp).background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Today’s AI Farming Advice",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                )
                Text(
                    text = "Rain expected in the evening. Avoid pesticide spraying today.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
                )
                TextButton(
                    onClick = onChatClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Ask AgriBot", color = AgriVibrantGreen, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp), tint = AgriVibrantGreen)
                }
            }
        }
    }
}

@Composable
fun ClimateAnalyticsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SharedInsightMiniCard("Soil Moisture", "42%", 0.42f, Color(0xFF795548), Modifier.weight(1f))
            SharedInsightMiniCard("Air Quality", "Good", 0.85f, Color(0xFF009688), Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SharedInsightMiniCard("Temp Score", "Optimal", 0.92f, AgriVibrantGreen, Modifier.weight(1f))
            SharedInsightMiniCard("Water Req.", "Low", 0.35f, AgriBlue, Modifier.weight(1f))
        }
    }
}

@Composable
fun WeatherAlertCard() {
    val infiniteTransition = rememberInfiniteTransition(label = "alert")
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderGlow"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFFFEBEE),
        border = BorderStroke(2.dp, AgriRed.copy(alpha = borderAlpha)),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Thunderstorm, contentDescription = null, tint = AgriRed, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = "Heavy Rain Warning",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = Color(0xFFB71C1C))
                )
                Text(
                    text = "High intensity rainfall expected 4 PM - 8 PM. Secure your crops.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = AgriRed, fontWeight = FontWeight.Bold, lineHeight = 18.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherForecastScreenPreview() {
    FarmersTheme {
        WeatherForecastScreen()
    }
}
