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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FertilizerRecommendationScreen(
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
    var moisture by remember { mutableStateOf("") }
    
    var cropTypeExpanded by remember { mutableStateOf(false) }
    var selectedCrop by remember { mutableStateOf("Rice") }
    val crops = listOf("Rice", "Wheat", "Maize", "Cotton", "Sugarcane")

    var isAnalyzing by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Science, contentDescription = null, tint = AgriOrange, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("AI Fertilizer Guide", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ManageSearch, contentDescription = "Soil Analysis", tint = AgriGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Home",
                onHomeClick = onHomeClick,
                onChatClick = onChatClick,
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
                        colors = listOf(Color.White, Color(0xFFF1F8E9), Color(0xFFFFF9C4))
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
                FertilizerHeader()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Soil Input Card
                Text(
                    text = "Soil Nutrient Analysis",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(14.dp))
                
                FertilizerInputCard(
                    n = nitrogen, onNChange = { nitrogen = it },
                    p = phosphorus, onPChange = { phosphorus = it },
                    k = potassium, onKChange = { potassium = it },
                    ph = phValue, onPhChange = { phValue = it },
                    moisture = moisture, onMoistureChange = { moisture = it },
                    crop = selectedCrop,
                    cropExpanded = cropTypeExpanded,
                    onCropExpandChange = { cropTypeExpanded = it },
                    onCropSelect = { selectedCrop = it }
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Analyze Button
                Button(
                    onClick = { 
                        isAnalyzing = true 
                        // Save to Firebase
                        val data = mapOf(
                            "N" to nitrogen, "P" to phosphorus, "K" to potassium,
                            "pH" to phValue, "moisture" to moisture, "crop" to selectedCrop
                        )
                        FirebaseManager.saveSoilData("guest_user", data)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgriGreen),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Analyzing Soil Nutrients...", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    } else {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Analyze Soil & Recommend", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
                
                LaunchedEffect(isAnalyzing) {
                    if (isAnalyzing) {
                        delay(200)
                        isAnalyzing = false
                        showResult = true
                        scrollState.animateScrollTo(1000)
                    }
                }
                
                if (showResult) {
                    Spacer(modifier = Modifier.height(40.dp))
                    FertilizerResultCard()
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    NutrientAnalyticsSection()
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    FarmingTipsSection()
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    PrecautionAlerts()
                }
                
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}

@Composable
fun FertilizerHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(AgriGlow.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Compost, contentDescription = null, modifier = Modifier.size(100.dp), tint = AgriGreen)
            Icon(
                Icons.Default.WaterDrop,
                contentDescription = null,
                modifier = Modifier.size(48.dp).align(Alignment.BottomEnd).offset(x = (-12).dp, y = (-12).dp).background(Color.White, CircleShape).padding(6.dp),
                tint = AgriBlue
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Get Smart Fertilizer Suggestions",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "AI-powered fertilizer recommendations based on soil nutrients.",
            style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FertilizerInputCard(
    n: String, onNChange: (String) -> Unit,
    p: String, onPChange: (String) -> Unit,
    k: String, onKChange: (String) -> Unit,
    ph: String, onPhChange: (String) -> Unit,
    moisture: String, onMoistureChange: (String) -> Unit,
    crop: String, cropExpanded: Boolean, onCropExpandChange: (Boolean) -> Unit, onCropSelect: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
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
                SharedInputField(ph, onPhChange, "Soil pH", Icons.Default.Opacity, Modifier.weight(1f), KeyboardType.Number)
            }
            
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
                    listOf("Rice", "Wheat", "Maize", "Cotton").forEach { s ->
                        DropdownMenuItem(text = { Text(s, fontWeight = FontWeight.Bold) }, onClick = { onCropSelect(s); onCropExpandChange(false) })
                    }
                }
            }

            SharedInputField(moisture, onMoistureChange, "Soil Moisture (%)", Icons.Default.WaterDrop, modifier = Modifier, keyboardType = KeyboardType.Number)
        }
    }
}

@Composable
fun FertilizerResultCard() {
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
                    .background(Color(0xFFFFF9C4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(60.dp), tint = Color(0xFFFBC02D))
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "Recommended: Urea + DAP",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
            )
            
            Text(
                text = "Nitrogen Deficiency Detected",
                style = MaterialTheme.typography.titleSmall.copy(color = AgriRed, fontWeight = FontWeight.ExtraBold)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                SharedResultInfoItem("Quantity", "50 kg/acre", Icons.Default.Scale)
                SharedResultInfoItem("Type", "Chemical", Icons.Default.Category)
                SharedResultInfoItem("Timing", "Before Sowing", Icons.Default.Schedule)
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            LinearProgressIndicator(
                progress = { 0.75f },
                modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                color = AgriVibrantGreen,
                trackColor = AgriGlow
            )
            Text(
                text = "Estimated Yield Improvement: +15%",
                style = MaterialTheme.typography.labelLarge.copy(color = AgriGreen, fontWeight = FontWeight.ExtraBold),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun NutrientAnalyticsSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        SharedInsightMiniCard("Soil Health", "82%", 0.82f, Color(0xFF795548), Modifier.weight(1f))
        SharedInsightMiniCard("Fertility Score", "Good", 0.78f, AgriVibrantGreen, Modifier.weight(1f))
    }
}

@Composable
fun FarmingTipsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "AI Farming Tips",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SharedTipCard("Best Time", "Apply early morning", Icons.Default.WbSunny, Modifier.weight(1f))
            SharedTipCard("Watering", "Wait 24h after", Icons.Default.WaterDrop, Modifier.weight(1f))
        }
    }
}

@Composable
fun PrecautionAlerts() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFFFEBEE),
        border = BorderStroke(2.dp, AgriRed.copy(alpha = 0.4f)),
        shadowElevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ReportProblem, contentDescription = null, tint = AgriRed, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Excess Usage Risk", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = Color(0xFFB71C1C)))
                Text(text = "Over-application of Urea can burn young roots.", style = MaterialTheme.typography.bodyMedium.copy(color = AgriRed, fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FertilizerRecommendationScreenPreview() {
    FarmersTheme {
        FertilizerRecommendationScreen()
    }
}
