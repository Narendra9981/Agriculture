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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*
import com.example.farmers.data.FirebaseManager
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemeDetailScreen(
    schemeName: String = "PM-Kisan Nidhi",
    onBack: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var applicationRef by remember { mutableStateOf("") }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                Button(
                    onClick = { 
                        showSuccessDialog = false 
                        onBack() 
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AgriGreen, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Application Submitted!", fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Your application for $schemeName has been successfully submitted.", color = Color.DarkGray)
                    Text("Reference ID: $applicationRef", fontWeight = FontWeight.Bold, color = AgriDarkGreen)
                    Text("Our local agriculture officer will review your records and contact you within 3-5 working days. 🌾", color = Color.Gray, fontSize = 13.sp)
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.White
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Scheme Details", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = AgriGreen)
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
                        colors = listOf(Color.White, AgriWhite, Color(0xFFFFF3E0))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Scheme Hero Image/Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color(0xFFE8F5E9), CircleShape)
                        .border(BorderStroke(3.dp, AgriVibrantGreen), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when {
                            schemeName.contains("Credit", true) -> Icons.Default.CreditCard
                            schemeName.contains("Insurance", true) || schemeName.contains("Bima", true) -> Icons.Default.Security
                            schemeName.contains("Solar", true) -> Icons.Default.WbSunny
                            schemeName.contains("Soil", true) -> Icons.Default.Texture
                            else -> Icons.Default.Payments
                        },
                        contentDescription = null, 
                        modifier = Modifier.size(60.dp), 
                        tint = AgriVibrantGreen
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = schemeName,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AgriVibrantGreen.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Active & Verified",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge.copy(color = AgriGreen, fontWeight = FontWeight.ExtraBold)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Detailed Information Sections
                SchemeInfoSection(
                    title = "About Scheme", 
                    content = getSchemeDescription(schemeName)
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                SchemeInfoSection("Eligibility Criteria", "• All small and marginal farmer families.\n• Landholding up to 2 hectares.\n• Citizen of India.")
                
                Spacer(modifier = Modifier.height(20.dp))
                
                SchemeInfoSection("Required Documents", "• Aadhaar Card\n• Land Ownership Papers\n• Bank Account Details\n• Passport Size Photo")
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Action Button
                Button(
                    onClick = {
                        applicationRef = "ABS-${(100000..999999).random()}"
                        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest_user"
                        val appData = mapOf(
                            "schemeName" to schemeName,
                            "appliedDate" to java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.getDefault()).format(java.util.Date()),
                            "status" to "Pending Review",
                            "referenceId" to applicationRef
                        )
                        FirebaseManager.saveSchemeApplication(uid, appData)
                        showSuccessDialog = true
                    },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text("Apply for this Scheme", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

fun getSchemeDescription(name: String): String {
    return when {
        name.contains("PM-Kisan", true) -> "Provides income support of ₹6,000 per year in three equal installments to all landholding farmer families across the country."
        name.contains("Bima", true) -> "Provides comprehensive insurance coverage against crop failure, helping in stabilizing the income of the farmers."
        name.contains("Credit", true) -> "A flexible credit delivery system to meet the comprehensive credit requirements of the farmers under a single window."
        name.contains("Solar", true) -> "The scheme aims to provide energy security to farmers by subsidizing solar water pumps and grid-connected solar power."
        else -> "This government initiative is designed to provide financial support and technical guidance to improve the welfare of the farming community."
    }
}

@Composable
fun SchemeInfoSection(title: String, content: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        border = BorderStroke(2.dp, AgriGlow),
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.DarkGray, lineHeight = 24.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SchemeDetailScreenPreview() {
    FarmersTheme {
        SchemeDetailScreen()
    }
}
