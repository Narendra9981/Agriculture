package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseResultScreen(
    scanResult: ScanResult? = null,
    onBack: () -> Unit = {},
    onScanAnother: () -> Unit = {},
    onChatExpert: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    
    // Fallback for preview or missing data
    val result = scanResult ?: ScanResult(
        "Tomato", "Early Blight", "Critical Action Required", listOf(
            Triple("Fungicide", "Apply Chlorothalonil", Icons.Default.Science),
            Triple("Copper Spray", "Prevent spore spread", Icons.Default.Opacity)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Disease Analysis Result", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share Report", tint = AgriVibrantGreen)
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
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFF1F8E9), Color(0xFFFFEBEE))
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
                // Scanned Crop Preview
                ScannedImagePreview(result.cropName)
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Main Result Card
                DiseaseResultCard(result.diseaseName, result.status)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // AI Treatment Suggestions
                Text(
                    text = "AI Treatment Suggestions",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                TreatmentSuggestionsRow(result.treatments)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Crop Health Status
                Text(
                    text = "Crop Health Analytics",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
                HealthStatusSection()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Expandable Info
                DiseaseInformationAccordion()
                
                Spacer(modifier = Modifier.height(36.dp))
                
                // Action Buttons
                ActionButtons(onScanAnother, onChatExpert)
                
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}

@Composable
fun ScannedImagePreview(cropName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .border(BorderStroke(3.dp, AgriVibrantGreen), RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(100.dp), tint = Color.Gray.copy(alpha = 0.4f))
            // Success indicator - Bright Green
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 12.dp, y = 12.dp)
                    .background(Color.White, CircleShape)
                    .padding(4.dp)
                    .border(BorderStroke(2.dp, AgriVibrantGreen), CircleShape)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(32.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "AI Scan Successfully Completed ✅",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriVibrantGreen)
        )
        Text(text = "$cropName Leaf Detected", style = MaterialTheme.typography.bodyMedium.copy(color = AgriGreen, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun DiseaseResultCard(diseaseName: String, status: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 12.dp,
        border = BorderStroke(2.dp, AgriRed.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$diseaseName Detected",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriRed)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(AgriOrange.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = "98% Match", style = MaterialTheme.typography.labelLarge.copy(color = AgriOrange, fontWeight = FontWeight.ExtraBold))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .background(AgriRed.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = "Status: $status", style = MaterialTheme.typography.labelLarge.copy(color = AgriRed, fontWeight = FontWeight.ExtraBold))
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LinearProgressIndicator(
                progress = { 0.98f },
                modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                color = AgriRed,
                trackColor = AgriRed.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun TreatmentSuggestionsRow(treatments: List<Triple<String, String, ImageVector>>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        treatments.forEach { (label, value, icon) ->
            val color = when(label) {
                "Pesticide", "Fungicide" -> AgriBlue
                "Organic", "Neem Oil" -> AgriVibrantGreen
                else -> Color(0xFF00B0FF)
            }
            Surface(
                modifier = Modifier.weight(1f).height(120.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                border = BorderStroke(2.dp, color.copy(alpha = 0.2f)),
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(modifier = Modifier.size(40.dp).background(color.copy(alpha = 0.15f), CircleShape), contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontWeight = FontWeight.Bold))
                    Text(text = value, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold, color = color), textAlign = TextAlign.Center)
                }
            }
        }
    }
}



@Composable
fun HealthStatusSection() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        SharedInsightMiniCard("Health Score", "42%", 0.42f, AgriRed, Modifier.weight(1f))
        SharedInsightMiniCard("Spread Risk", "Medium", 0.65f, AgriOrange, Modifier.weight(1f))
    }
}

@Composable
fun DiseaseInformationAccordion() {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        ExpandableInfoCard("Causes of Disease", "Pathogenic fungi thriving in extreme humidity and stagnant air.")
        ExpandableInfoCard("Key Symptoms", "Sharp yellow lesions with deep brown centers and curling edges.")
        ExpandableInfoCard("Prevention Methods", "Boost air circulation, use drip irrigation, and resistant seeds.")
    }
}

@Composable
fun ExpandableInfoCard(title: String, description: String) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, AgriGlow),
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen), modifier = Modifier.weight(1f))
                Icon(if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = AgriGreen)
            }
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = description,
                    modifier = Modifier.padding(top = 14.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray, fontWeight = FontWeight.Medium, lineHeight = 20.sp)
                )
            }
        }
    }
}

@Composable
fun ActionButtons(onScanAnother: () -> Unit, onChatExpert: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text("Get Full Treatment Guide", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        }
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = onScanAnother,
                modifier = Modifier.weight(1f).height(60.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, AgriGreen)
            ) {
                Text("Scan Again", color = AgriGreen, fontWeight = FontWeight.ExtraBold)
            }
            
            OutlinedButton(
                onClick = onChatExpert,
                modifier = Modifier.weight(1f).height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = AgriGlow.copy(alpha = 0.3f)),
                border = BorderStroke(2.dp, AgriGreen)
            ) {
                Text("Chat AI Expert", color = AgriDarkGreen, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiseaseResultScreenPreview() {
    FarmersTheme {
        DiseaseResultScreen()
    }
}
