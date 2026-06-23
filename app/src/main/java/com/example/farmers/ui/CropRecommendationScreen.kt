package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*
import com.example.farmers.data.FirebaseManager
import com.example.farmers.data.CropRecommendationManager
import com.example.farmers.data.RecommendedCrop
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropRecommendationScreen(
    onBack: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var nitrogen by remember { mutableStateOf("") }
    var phosphorus by remember { mutableStateOf("") }
    var potassium by remember { mutableStateOf("") }
    var phValue by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var rainfall by remember { mutableStateOf("") }
    
    var stateExpanded by remember { mutableStateOf(false) }
    var selectedState by remember { mutableStateOf("Punjab") }
    
    var seasonExpanded by remember { mutableStateOf(false) }
    var selectedSeason by remember { mutableStateOf("Kharif") }
    
    var soilExpanded by remember { mutableStateOf(false) }
    var selectedSoil by remember { mutableStateOf("Alluvial") }
    
    var isAnalyzing by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    var isReasonExpanded by remember { mutableStateOf(false) }
    var currentRecommendation by remember { mutableStateOf<RecommendedCrop?>(null) }
    
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("AI Crop Recommendation", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
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
                activeTab = "Scan",
                onHomeClick = onHomeClick,
                onChatClick = onChatClick,
                onScanClick = onScanClick,
                onSchemesClick = onSchemesClick,
                onMarketClick = onMarketClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFF1F8E9), Color(0xFFE1F5FE))
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
                // Header Illustration Section
                RecommendationHeader()
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Soil Analysis Input Card
                Text(
                    text = "Soil & Environment Data",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(14.dp))
                
                SoilAnalysisCard(
                    n = nitrogen, onNChange = { nitrogen = it },
                    p = phosphorus, onPChange = { phosphorus = it },
                    k = potassium, onKChange = { potassium = it },
                    ph = phValue, onPhChange = { phValue = it },
                    temp = temperature, onTempChange = { temperature = it },
                    hum = humidity, onHumChange = { humidity = it },
                    rain = rainfall, onRainChange = { rainfall = it }
                )
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Location & Season Section
                Text(
                    text = "Location & Season",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(14.dp))
                
                LocationSeasonCard(
                    state = selectedState,
                    stateExpanded = stateExpanded,
                    onStateExpandChange = { stateExpanded = it },
                    onStateSelect = { selectedState = it },
                    season = selectedSeason,
                    seasonExpanded = seasonExpanded,
                    onSeasonExpandChange = { seasonExpanded = it },
                    onSeasonSelect = { selectedSeason = it },
                    soil = selectedSoil,
                    soilExpanded = soilExpanded,
                    onSoilExpandChange = { soilExpanded = it },
                    onSoilSelect = { selectedSoil = it }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Analyze Button
                Button(
                    onClick = { 
                        isAnalyzing = true 
                        // Save data to Firebase
                        val soilData = mapOf(
                            "N" to nitrogen,
                            "P" to phosphorus,
                            "K" to potassium,
                            "pH" to phValue,
                            "Temp" to temperature,
                            "Humidity" to humidity,
                            "Rainfall" to rainfall
                        )
                        FirebaseManager.saveSoilData("guest_user", soilData)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("AI is Analyzing...", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    } else {
                        Icon(Icons.Default.SettingsSuggest, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Analyze & Recommend", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
                
                LaunchedEffect(isAnalyzing) {
                    if (isAnalyzing) {
                        // AI Model Simulation
                        currentRecommendation = CropRecommendationManager.recommendCrop(
                            nitrogen, phosphorus, potassium, phValue, temperature, humidity, rainfall
                        )
                        
                        delay(2000) // Realistic analysis delay
                        isAnalyzing = false
                        showResult = true
                        scrollState.animateScrollTo(1000)
                    }
                }
                
                if (showResult) {
                    Spacer(modifier = Modifier.height(36.dp))
                    currentRecommendation?.let { recommendation ->
                        RecommendationResultCard(
                            crop = recommendation,
                            isReasonExpanded = isReasonExpanded,
                            onReasonToggle = { isReasonExpanded = !isReasonExpanded }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(28.dp))
                    SmartFarmingInsights()
                }
                
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}

@Composable
fun RecommendationHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(AgriGlow.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Agriculture, contentDescription = null, modifier = Modifier.size(100.dp), tint = AgriGreen)
            Icon(
                Icons.Default.Science,
                contentDescription = null,
                modifier = Modifier.size(48.dp).align(Alignment.BottomEnd).offset(x = (-12).dp, y = (-12).dp).background(Color.White, CircleShape).padding(6.dp),
                tint = Color(0xFF795548)
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Find the Best Crop for Your Farm",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Get AI-powered crop suggestions based on soil nutrients and weather conditions.",
            style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 10.dp)
        )
    }
}

@Composable
fun SoilAnalysisCard(
    n: String, onNChange: (String) -> Unit,
    p: String, onPChange: (String) -> Unit,
    k: String, onKChange: (String) -> Unit,
    ph: String, onPhChange: (String) -> Unit,
    temp: String, onTempChange: (String) -> Unit,
    hum: String, onHumChange: (String) -> Unit,
    rain: String, onRainChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGlow),
        shadowElevation = 6.dp
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SharedInputField(n, onNChange, "Nitrogen (N)", Icons.Default.Science, Modifier.weight(1f), KeyboardType.Number)
                SharedInputField(p, onPChange, "Phosphorus (P)", Icons.Default.Science, Modifier.weight(1f), KeyboardType.Number)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SharedInputField(k, onKChange, "Potassium (K)", Icons.Default.Science, Modifier.weight(1f), KeyboardType.Number)
                SharedInputField(ph, onPhChange, "Soil pH Level", Icons.Default.Opacity, Modifier.weight(1f), KeyboardType.Number)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SharedInputField(temp, onTempChange, "Temp (°C)", Icons.Default.Thermostat, Modifier.weight(1f), KeyboardType.Number)
                SharedInputField(hum, onHumChange, "Humidity (%)", Icons.Default.WaterDrop, Modifier.weight(1f), KeyboardType.Number)
            }
            SharedInputField(rain, onRainChange, "Rainfall (mm)", Icons.Default.CloudQueue, modifier = Modifier, keyboardType = KeyboardType.Number)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSeasonCard(
    state: String, stateExpanded: Boolean, onStateExpandChange: (Boolean) -> Unit, onStateSelect: (String) -> Unit,
    season: String, seasonExpanded: Boolean, onSeasonExpandChange: (Boolean) -> Unit, onSeasonSelect: (String) -> Unit,
    soil: String, soilExpanded: Boolean, onSoilExpandChange: (Boolean) -> Unit, onSoilSelect: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGlow),
        shadowElevation = 6.dp
    ) {
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ExposedDropdownMenuBox(
                expanded = stateExpanded,
                onExpandedChange = onStateExpandChange
            ) {
                OutlinedTextField(
                    value = state,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("State / District", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriGreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(20.dp)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stateExpanded) },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AgriDarkGreen,
                        unfocusedTextColor = AgriDarkGreen,
                        focusedBorderColor = AgriVibrantGreen,
                        unfocusedBorderColor = AgriGlow
                    )
                )
                ExposedDropdownMenu(expanded = stateExpanded, onDismissRequest = { onStateExpandChange(false) }) {
                    listOf("Punjab", "Haryana", "UP", "Maharashtra").forEach { s ->
                        DropdownMenuItem(text = { Text(s, fontWeight = FontWeight.Bold) }, onClick = { onStateSelect(s); onStateExpandChange(false) })
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ExposedDropdownMenuBox(
                    expanded = seasonExpanded,
                    onExpandedChange = onSeasonExpandChange,
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = season,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Season", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriGreen) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                        leadingIcon = { Icon(Icons.Default.WbSunny, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(20.dp)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = seasonExpanded) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AgriDarkGreen,
                            unfocusedTextColor = AgriDarkGreen,
                            focusedBorderColor = AgriVibrantGreen,
                            unfocusedBorderColor = AgriGlow
                        )
                    )
                    ExposedDropdownMenu(expanded = seasonExpanded, onDismissRequest = { onSeasonExpandChange(false) }) {
                        listOf("Kharif", "Rabi", "Zaid").forEach { s ->
                            DropdownMenuItem(text = { Text(s, fontWeight = FontWeight.Bold) }, onClick = { onSeasonSelect(s); onSeasonExpandChange(false) })
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = soilExpanded,
                    onExpandedChange = onSoilExpandChange,
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = soil,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Soil Type", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriGreen) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                        leadingIcon = { Icon(Icons.Default.Landscape, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(20.dp)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = soilExpanded) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AgriDarkGreen,
                            unfocusedTextColor = AgriDarkGreen,
                            focusedBorderColor = AgriVibrantGreen,
                            unfocusedBorderColor = AgriGlow
                        )
                    )
                    ExposedDropdownMenu(expanded = soilExpanded, onDismissRequest = { onSoilExpandChange(false) }) {
                        listOf("Alluvial", "Black", "Red", "Clayey").forEach { s ->
                            DropdownMenuItem(text = { Text(s, fontWeight = FontWeight.Bold) }, onClick = { onSoilSelect(s); onSoilExpandChange(false) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendationResultCard(crop: RecommendedCrop, isReasonExpanded: Boolean, onReasonToggle: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        border = BorderStroke(3.dp, AgriVibrantGreen.copy(alpha = 0.5f)),
        shadowElevation = 12.dp
    ) {
        Column(modifier = Modifier.padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(AgriGlow),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Grass, contentDescription = null, modifier = Modifier.size(70.dp), tint = AgriGreen)
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "Best Match: ${crop.name}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
            )
            
            Text(
                text = "${crop.score}% Suitability Score",
                style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFFFFA000), fontWeight = FontWeight.ExtraBold)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                SharedResultInfoItem("Yield", crop.yield, Icons.AutoMirrored.Filled.TrendingUp)
                SharedResultInfoItem("Water", crop.water, Icons.Default.WaterDrop)
                SharedResultInfoItem("Season", crop.season, Icons.Default.WbSunny)
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            Button(
                onClick = onReasonToggle,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgriGlow.copy(alpha = 0.4f))
            ) {
                Text("Why this crop?", color = AgriDarkGreen, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (isReasonExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AgriDarkGreen
                )
            }

            AnimatedVisibility(visible = isReasonExpanded) {
                Column(modifier = Modifier.padding(top = 18.dp)) {
                    Text(
                        text = crop.reason,
                        style = MaterialTheme.typography.bodyMedium.copy(color = AgriGreen, fontWeight = FontWeight.Bold, lineHeight = 20.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun SmartFarmingInsights() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "AI Smart Insights",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
        )
        
        Spacer(modifier = Modifier.height(14.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SharedInsightMiniCard("Soil Health", "Excellent", 0.9f, Color(0xFF795548), Modifier.weight(1f))
            SharedInsightMiniCard("Market Profit", "High", 0.85f, Color(0xFF9C27B0), Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CropRecommendationScreenPreview() {
    FarmersTheme {
        CropRecommendationScreen()
    }
}
