package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
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
import com.example.farmers.data.FirebaseManager
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onBack: () -> Unit = {},
    onAskAi: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    var userName by remember { mutableStateOf("Farmer") }
    var userLocation by remember { mutableStateOf("India") }
    var userPhone by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            userEmail = currentUser.email ?: ""
            FirebaseManager.getUserProfile(currentUser.uid) { profile ->
                if (profile != null) {
                    userName = profile["name"] as? String ?: currentUser.displayName ?: "Farmer"
                    userLocation = profile["state"] as? String ?: "India"
                    userPhone = profile["mobile"] as? String ?: ""
                } else {
                    userName = currentUser.displayName ?: "Farmer"
                    userLocation = "India"
                    userPhone = currentUser.phoneNumber ?: ""
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Farmer Profile", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = AgriGreen)
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = AgriGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Profile",
                onHomeClick = onBack
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFF1F8E9), Color(0xFFE8F5E9))
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
                // Profile Header Section
                ProfileHeader(name = userName, location = userLocation)
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Farming Overview
                Text(
                    text = "Farming Overview",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                FarmingOverviewSection()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Farm Details
                Text(
                    text = "Account & Farm Details",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                FarmDetailsSection(phone = userPhone, email = userEmail)
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // AI Farming Insights Card
                AiProfileInsightsCard(onAskAi)
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Preferences & Settings
                Text(
                    text = "Preferences",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                PreferencesSection()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Achievements
                Text(
                    text = "Achievements",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                AchievementsSection()
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String, location: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGlow),
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(AgriVibrantGreen.copy(alpha = 0.15f), CircleShape)
                    .border(BorderStroke(3.dp, AgriVibrantGreen), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp), tint = AgriGreen)
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                HeaderBadge("Farmer Badge", Icons.Default.History)
                HeaderBadge("Smart Farming", Icons.Default.Agriculture)
            }
        }
    }
}

@Composable
fun HeaderBadge(text: String, icon: ImageVector) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AgriVibrantGreen.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, AgriVibrantGreen.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = AgriGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, style = MaterialTheme.typography.labelMedium.copy(color = AgriDarkGreen, fontWeight = FontWeight.ExtraBold))
        }
    }
}

@Composable
fun FarmingOverviewSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        SharedInsightMiniCard("Total Crops", "14 Varieties", 0.7f, AgriBlue, Modifier.weight(1f))
        SharedInsightMiniCard("Harvest Rate", "92% Success", 0.92f, AgriVibrantGreen, Modifier.weight(1f))
    }
}

@Composable
fun FarmDetailsSection(phone: String, email: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGlow),
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            if (phone.isNotBlank()) {
                DetailItem("Mobile Number", phone, Icons.Default.Phone, AgriGreen)
            }
            if (email.isNotBlank()) {
                DetailItem("Email Address", email, Icons.Default.Email, AgriBlue)
            }
            DetailItem("Land Size", "12.5 Acres", Icons.Default.Landscape, AgriBlue)
            DetailItem("Soil Type", "Alluvial / Loamy", Icons.Default.Texture, Color(0xFF795548))
            DetailItem("Primary Crops", "Wheat, Rice, Mustard", Icons.Default.Grass, AgriGreen)
        }
    }
}

@Composable
fun DetailItem(label: String, value: String, icon: ImageVector, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(40.dp).background(color.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontWeight = FontWeight.Bold))
            Text(text = value, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
        }
    }
}

@Composable
fun AiProfileInsightsCard(onAskAi: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color(0xFFFFF9C4), // Brighter Yellow
        border = BorderStroke(2.dp, AgriOrange.copy(alpha = 0.3f)),
        shadowElevation = 8.dp
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(70.dp).background(Color.White, CircleShape).border(BorderStroke(2.dp, AgriOrange), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = AgriOrange, modifier = Modifier.size(36.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Your Farming AI Insights", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = Color(0xFFE65100)))
                Text(text = "Improve yield by 12% with NPK optimization.", style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFEF6C00), fontWeight = FontWeight.Bold))
                TextButton(onClick = onAskAi, contentPadding = PaddingValues(0.dp)) {
                    Text("Ask AgriBot", color = Color(0xFFE65100), fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFFE65100))
                }
            }
        }
    }
}

@Composable
fun PreferencesSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGlow),
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PreferenceToggle("Weather Alerts", true)
            PreferenceToggle("Market Price Updates", true)
            PreferenceToggle("Disease Outbreak Alerts", false)
            PreferenceToggle("Voice Assistant", true)
        }
    }
}

@Composable
fun PreferenceToggle(label: String, initialValue: Boolean) {
    var checked by remember { mutableStateOf(initialValue) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label, 
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = AgriDarkGreen
            )
        )
        Switch(
            checked = checked, 
            onCheckedChange = { checked = it }, 
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White, 
                checkedTrackColor = AgriVibrantGreen,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
fun AchievementsSection() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        val badges = listOf(
            "Smart Farmer" to Icons.Default.EmojiEvents,
            "High Yield" to Icons.Default.Star,
            "AI Expert" to Icons.Default.Verified
        )
        items(badges) { (name, icon) ->
            Surface(
                modifier = Modifier.width(130.dp).height(120.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = BorderStroke(2.dp, AgriGlow),
                shadowElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(modifier = Modifier.size(50.dp).background(AgriAccent.copy(alpha = 0.15f), CircleShape), contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = AgriAccent, modifier = Modifier.size(36.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = name, 
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriDarkGreen
                        ), 
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    FarmersTheme {
        UserProfileScreen()
    }
}
