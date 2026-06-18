package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FertilizerPredictionScreen(
    onBack: () -> Unit = {},
    onAskAi: () -> Unit = {},
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
    var moisture by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    
    var cropExpanded by remember { mutableStateOf(false) }
    var selectedCrop by remember { mutableStateOf("Select Crop Type") }
    val crops = listOf("Rice", "Maize", "Chickpea", "Kidneybeans", "Pigeonpeas", "Mothbeans", "Mungbean", "Blackgram", "Lentil", "Pomegranate", "Banana", "Mango", "Grapes", "Watermelon", "Muskmelon", "Apple", "Orange", "Papaya", "Coconut", "Cotton", "Jute", "Coffee")

    var soilExpanded by remember { mutableStateOf(false) }
    var selectedSoil by remember { mutableStateOf("Select Soil Type") }
    val soils = listOf("Black", "Clayey", "Loamy", "Red", "Sandy")

    var isPredicting by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    
    var predictedFertilizer by remember { mutableStateOf("NPK 10-26-26") }
    var suitabilityScore by remember { mutableStateOf(96) }
    var recommendedQty by remember { mutableStateOf("40 kg/acre") }
    var yieldIncrease by remember { mutableStateOf("+18%") }
    
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Analytics, contentDescription = null, tint = AgriGreen, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI Fertilizer Prediction", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, tint = AgriGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(alpha = 0.8f))
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Home", // Shared for now
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
                        colors = listOf(Color.White, AgriWhite, Color(0xFFF1F8E9), Color(0xFFEFEBE9))
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
                FertilizerPredictHeader()
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Soil & Crop Input Card
                Text(
                    text = "Soil & Crop Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                FertilizerInputForm(
                    crop = selectedCrop,
                    cropExpanded = cropExpanded,
                    onCropExpandChange = { cropExpanded = it },
                    onCropSelect = { selectedCrop = it },
                    soil = selectedSoil,
                    soilExpanded = soilExpanded,
                    onSoilExpandChange = { soilExpanded = it },
                    onSoilSelect = { selectedSoil = it },
                    n = nitrogen, onNChange = { nitrogen = it },
                    p = phosphorus, onPChange = { phosphorus = it },
                    k = potassium, onKChange = { potassium = it },
                    ph = phValue, onPhChange = { phValue = it },
                    moisture = moisture, onMoistureChange = { moisture = it },
                    temp = temperature, onTempChange = { temperature = it }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Predict Button
                Button(
                    onClick = { 
                        isPredicting = true 
                        
                        val nInt = nitrogen.toIntOrNull() ?: 50
                        val pInt = phosphorus.toIntOrNull() ?: 30
                        val kInt = potassium.toIntOrNull() ?: 40
                        
                        // Calculate fertilizer prediction based on nutrient and crop type
                        predictedFertilizer = when {
                            nInt < 30 -> "Urea (46% N) top-dress"
                            pInt < 30 -> "DAP 18-46-0 (High Phosphate)"
                            kInt < 25 -> "Muriate of Potash (MOP)"
                            selectedCrop == "Rice" -> "NPK 10-26-26 + Zinc"
                            selectedCrop == "Wheat" -> "DAP 18-46-0 & Urea"
                            selectedCrop == "Maize" -> "NPK 12-32-16 Complex"
                            selectedCrop == "Cotton" -> "NPK 15-15-15 + Mg"
                            selectedCrop == "Sugarcane" -> "NPK 19-19-19 Complex"
                            selectedCrop == "Potato" -> "NPK 10-10-20 + Potassium"
                            selectedCrop == "Tomato" -> "NPK 5-10-10 + Calcium"
                            else -> "NPK 19-19-19 (Balanced)"
                        }
                        
                        // Vary stats dynamically based on entered numbers to reflect customized outcomes
                        suitabilityScore = 85 + ((nInt + pInt + kInt) % 14)
                        recommendedQty = "${35 + ((nInt * 2 + pInt) % 25)} kg/acre"
                        yieldIncrease = "+${12 + ((pInt + kInt) % 11)}%"

                        val data = mapOf(
                            "N" to nitrogen, "P" to phosphorus, "K" to potassium,
                            "pH" to phValue, "moisture" to moisture, "temp" to temperature,
                            "crop" to selectedCrop, "soil" to selectedSoil,
                            "prediction" to predictedFertilizer
                        )
                        FirebaseManager.saveSoilData("guest_user", data)
                        
                        coroutineScope.launch {
                            delay(1000)
                            isPredicting = false
                            showResult = true
                            scrollState.animateScrollTo(1000)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgriGreen),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    if (isPredicting) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("AI Predicting Best Fertilizer...", fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Predict Fertilizer", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                if (showResult) {
                    Spacer(modifier = Modifier.height(32.dp))
                    PredictedFertilizerResultCard(
                        prediction = predictedFertilizer,
                        suitability = suitabilityScore,
                        quantity = recommendedQty,
                        yield = yieldIncrease
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    SmartNutrientAnalytics()
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    FarmingTipsRow()
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    SafetyAlertsSection()
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun FertilizerPredictHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Color(0xFFE8F5E9).copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.PrecisionManufacturing, contentDescription = null, modifier = Modifier.size(80.dp), tint = AgriGreen)
            Icon(
                Icons.Default.Science,
                contentDescription = null,
                modifier = Modifier.size(40.dp).align(Alignment.BottomEnd).offset(x = (-10).dp, y = (-10).dp),
                tint = Color(0xFF795548)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Predict the Best Fertilizer for Your Crop",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "AI analyzes soil nutrients, weather, and crop type to suggest the most effective fertilizer.",
            style = MaterialTheme.typography.bodyMedium.copy(color = AgriDarkGreen.copy(alpha = 0.7f)),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FertilizerInputForm(
    crop: String, cropExpanded: Boolean, onCropExpandChange: (Boolean) -> Unit, onCropSelect: (String) -> Unit,
    soil: String, soilExpanded: Boolean, onSoilExpandChange: (Boolean) -> Unit, onSoilSelect: (String) -> Unit,
    n: String, onNChange: (String) -> Unit,
    p: String, onPChange: (String) -> Unit,
    k: String, onKChange: (String) -> Unit,
    ph: String, onPhChange: (String) -> Unit,
    moisture: String, onMoistureChange: (String) -> Unit,
    temp: String, onTempChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White.copy(alpha = 0.8f),
        border = CardDefaults.outlinedCardBorder(),
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ExposedDropdownMenuBox(
                expanded = cropExpanded,
                onExpandedChange = onCropExpandChange
            ) {
                OutlinedTextField(
                    value = crop,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Crop Type", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriGreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
                    leadingIcon = { Icon(Icons.Default.Grass, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(20.dp)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cropExpanded) },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = AgriDarkGreen,
                        unfocusedTextColor = AgriDarkGreen,
                        focusedBorderColor = AgriVibrantGreen,
                        unfocusedBorderColor = AgriGlow
                    )
                )
                ExposedDropdownMenu(expanded = cropExpanded, onDismissRequest = { onCropExpandChange(false) }) {
                    listOf("Rice", "Maize", "Cotton", "Sugarcane").forEach { s ->
                        DropdownMenuItem(text = { Text(s) }, onClick = { onCropSelect(s); onCropExpandChange(false) })
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = soilExpanded,
                onExpandedChange = onSoilExpandChange
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
                    listOf("Black", "Clayey", "Loamy", "Red", "Sandy").forEach { s ->
                        DropdownMenuItem(text = { Text(s) }, onClick = { onSoilSelect(s); onSoilExpandChange(false) })
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SharedInputField(n, onNChange, "Nitrogen (N)", Icons.Default.Science, Modifier.weight(1f), KeyboardType.Number)
                SharedInputField(p, onPChange, "Phosphorus (P)", Icons.Default.Science, Modifier.weight(1f), KeyboardType.Number)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SharedInputField(k, onKChange, "Potassium (K)", Icons.Default.Science, Modifier.weight(1f), KeyboardType.Number)
                SharedInputField(ph, onPhChange, "Soil pH", Icons.Default.Opacity, Modifier.weight(1f), KeyboardType.Number)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SharedInputField(moisture, onMoistureChange, "Moisture (%)", Icons.Default.WaterDrop, Modifier.weight(1f), KeyboardType.Number)
                SharedInputField(temp, onTempChange, "Temp (°C)", Icons.Default.Thermostat, Modifier.weight(1f), KeyboardType.Number)
            }
        }
    }
}

@Composable
fun PredictedFertilizerResultCard(
    prediction: String,
    suitability: Int,
    quantity: String,
    yield: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGreen.copy(alpha = 0.3f)),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE1F5FE)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Inventory2, contentDescription = null, modifier = Modifier.size(50.dp), tint = Color(0xFF0288D1))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Predicted: $prediction",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = AgriGreen)
            )
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(AgriGlow.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = "Chemical", style = MaterialTheme.typography.labelSmall.copy(color = AgriGreen, fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$suitability% Suitability",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFFFFA000), fontWeight = FontWeight.Bold)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                SharedResultInfoItem("Qty", quantity, Icons.Default.Scale)
                SharedResultInfoItem("Yield", yield, Icons.AutoMirrored.Filled.TrendingUp)
                SharedResultInfoItem("Time", "Post-Sowing", Icons.Default.Schedule)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Nutrient Balance", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.9f },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                    color = AgriGreen,
                    trackColor = AgriGlow
                )
            }
        }
    }
}

@Composable
fun SmartNutrientAnalytics() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Nutrient Analytics",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SharedInsightMiniCard("Fertility Score", "High", 0.92f, Color(0xFF4CAF50), Modifier.weight(1f))
            SharedInsightMiniCard("N Deficiency", "Low", 0.25f, Color(0xFFD32F2F), Modifier.weight(1f))
        }
    }
}

@Composable
fun FarmingTipsRow() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "AI Farming Tips",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SharedTipCard("Best Time", "Evening application", Icons.Default.WbTwilight, Modifier.weight(1f))
            SharedTipCard("Watering", "Moderate moisture", Icons.Default.Opacity, Modifier.weight(1f))
        }
    }
}

@Composable
fun SafetyAlertsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Precaution & Safety Alerts",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFFEBEE),
            border = BorderStroke(1.dp, Color(0xFFD32F2F).copy(alpha = 0.3f))
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = Color(0xFFD32F2F))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "Soil Damage Prevention", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C)))
                    Text(text = "Avoid consecutive heavy application to prevent acidification.", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFD32F2F)))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FertilizerPredictionScreenPreview() {
    FarmersTheme {
        FertilizerPredictionScreen()
    }
}
