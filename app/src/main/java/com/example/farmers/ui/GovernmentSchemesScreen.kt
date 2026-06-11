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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovernmentSchemesScreen(
    onBack: () -> Unit = {},
    onAskAi: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSchemeApply: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, tint = AgriOrange, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Schemes & Subsidies", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, tint = AgriGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Schemes",
                onHomeClick = onHomeClick,
                onChatClick = onChatClick,
                onScanClick = onScanClick,
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
                        colors = listOf(Color.White, AgriWhite, Color(0xFFE3F2FD), Color(0xFFFFF3E0))
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
                // Header Illustration
                SchemesHeader()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Search & Filter Section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    border = BorderStroke(2.dp, AgriBlue.copy(alpha = 0.3f)),
                    shadowElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search 15+ schemes...", fontWeight = FontWeight.Medium) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = AgriVibrantGreen) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = AgriDarkGreen,
                                unfocusedTextColor = AgriDarkGreen,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = AgriVibrantGreen,
                                unfocusedBorderColor = AgriGlow
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            FilterChip(
                                selected = true,
                                onClick = {},
                                label = { Text("State", fontWeight = FontWeight.Bold, color = Color.White) },
                                leadingIcon = { Icon(Icons.Default.LocationOn, null, Modifier.size(18.dp)) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AgriVibrantGreen, selectedLabelColor = Color.White, selectedLeadingIconColor = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            )
                            FilterChip(
                                selected = false, 
                                onClick = {}, 
                                label = { Text("Welfare", fontWeight = FontWeight.Bold, color = AgriGreen) },
                                shape = RoundedCornerShape(12.dp),
                                colors = FilterChipDefaults.filterChipColors(containerColor = Color.White),
                                border = FilterChipDefaults.filterChipBorder(enabled = true, selected = false, borderColor = AgriGlow, borderWidth = 2.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Featured Schemes List (Expanded to 15+)
                Text(
                    text = "All Available Schemes",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                
                val allSchemes = listOf(
                    SchemeItem("PM-Kisan Nidhi", "₹6,000 yearly income support", Icons.Default.Payments, AgriVibrantGreen, "Eligible"),
                    SchemeItem("PM Fasal Bima", "Crop insurance against disasters", Icons.Default.Security, AgriBlue, "Apply Now"),
                    SchemeItem("Fertilizer Subsidy", "Subsidized Urea & DAP prices", Icons.Default.Science, Color(0xFF795548), "Eligible"),
                    SchemeItem("Kisan Credit Card", "Low-interest farming loans", Icons.Default.CreditCard, AgriOrange, "Eligible"),
                    SchemeItem("Soil Health Card", "Free soil testing and analysis", Icons.Default.Texture, AgriGreen, "Apply Now"),
                    SchemeItem("Solar Pump (PM-KUSUM)", "60% subsidy on solar water pumps", Icons.Default.WbSunny, AgriAccent, "Apply Now"),
                    SchemeItem("Pramparagat Krishi", "Support for organic farming", Icons.Default.Eco, AgriVibrantGreen, "Eligible"),
                    SchemeItem("National Livestock", "Subsidy for animal husbandry", Icons.Default.Pets, Color(0xFFE91E63), "Apply Now"),
                    SchemeItem("Micro Irrigation", "Drip & sprinkler system subsidy", Icons.Default.WaterDrop, AgriBlue, "Apply Now"),
                    SchemeItem("Agri-Infrastructure", "Funding for post-harvest storage", Icons.Default.Warehouse, Color(0xFF26A69A), "Eligible"),
                    SchemeItem("E-NAM Trading", "Online platform for mandi sales", Icons.Default.Storefront, AgriOrange, "Apply Now"),
                    SchemeItem("Integrated Farming", "Multi-crop system support", Icons.Default.Agriculture, AgriGreen, "Eligible"),
                    SchemeItem("Bee-Keeping Mission", "Incentives for honey production", Icons.Default.Hive, AgriAccent, "Apply Now"),
                    SchemeItem("Seed Subsidy", "Discount on high-yield seeds", Icons.Default.Grain, AgriVibrantGreen, "Eligible"),
                    SchemeItem("Fisheries Fund", "Support for pond aquaculture", Icons.Default.Phishing, AgriBlue, "Apply Now")
                ).filter { it.name.contains(searchQuery, ignoreCase = true) }

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    allSchemes.forEach { item ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            border = BorderStroke(2.dp, item.color.copy(alpha = 0.2f)),
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(56.dp).background(item.color.copy(alpha = 0.15f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(item.icon, contentDescription = null, tint = item.color, modifier = Modifier.size(30.dp))
                                }
                                Spacer(modifier = Modifier.width(18.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = item.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                                    Text(text = item.desc, style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray, fontWeight = FontWeight.Bold))
                                    Box(
                                        modifier = Modifier.padding(top = 6.dp).background(item.color.copy(alpha = 0.2f), RoundedCornerShape(8.dp)).padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(text = item.badge, style = MaterialTheme.typography.labelSmall.copy(color = item.color, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp))
                                    }
                                }
                                Button(
                                    onClick = { onSchemeApply(item.name) },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    modifier = Modifier.height(42.dp),
                                    elevation = ButtonDefaults.buttonElevation(4.dp)
                                ) {
                                    Text("Apply", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                                }
                            }
                        }
                    }
                    if (allSchemes.isEmpty()) {
                        Text("No schemes found matching '$searchQuery'", color = Color.Gray, modifier = Modifier.padding(16.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // AI Assistance Card
                AiAssistanceCard(onAskAi)
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun SchemesHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFFFE0B2).copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color(0xFFEF6C00))
            Icon(
                Icons.Default.VolunteerActivism,
                contentDescription = null,
                modifier = Modifier.size(52.dp).align(Alignment.BottomEnd).offset(x = (-12).dp, y = (-12).dp).background(Color.White, CircleShape).padding(8.dp),
                tint = AgriVibrantGreen
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Explore 15+ Farmer Welfare Programs",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Get access to government schemes, subsidies, and financial support for every type of farming.",
            style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 10.dp)
        )
    }
}

data class SchemeItem(val name: String, val desc: String, val icon: ImageVector, val color: Color, val badge: String)

@Composable
fun AiAssistanceCard(onAskAi: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color(0xFFE1F5FE),
        border = BorderStroke(2.dp, AgriBlue.copy(alpha = 0.4f)),
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(70.dp).background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = AgriBlue, modifier = Modifier.size(40.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "Need Help Choosing?",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = Color(0xFF01579B))
                )
                Text(
                    text = "AgriBot can recommend suitable schemes based on your profile.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF0277BD), fontWeight = FontWeight.Bold)
                )
                TextButton(onClick = onAskAi, contentPadding = PaddingValues(0.dp)) {
                    Text("Ask AI Assistant", color = Color(0xFF0288D1), fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF0288D1))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GovernmentSchemesScreenPreview() {
    FarmersTheme {
        GovernmentSchemesScreen()
    }
}
