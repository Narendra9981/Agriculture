package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.data.FirebaseManager
import com.example.farmers.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    var name by remember { mutableStateOf("Arjun Singh") }
    var location by remember { mutableStateOf("Ludhiana, Punjab") }
    var experience by remember { mutableStateOf("10 Years") }
    var farmingType by remember { mutableStateOf("Mixed Farming") }
    var landSize by remember { mutableStateOf("12.5 Acres") }
    
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Profile", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture Edit
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(AgriVibrantGreen.copy(alpha = 0.15f), CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(70.dp), tint = AgriGreen)
                    }
                    IconButton(
                        onClick = { /* Handle Image Pick */ },
                        modifier = Modifier
                            .size(36.dp)
                            .background(AgriGreen, CircleShape)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Change Photo", tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Form Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    color = Color.White,
                    border = BorderStroke(2.dp, AgriGlow),
                    shadowElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        SharedInputField(
                            value = name,
                            onValueChange = { name = it },
                            label = "Full Name",
                            icon = Icons.Default.Person
                        )
                        
                        SharedInputField(
                            value = location,
                            onValueChange = { location = it },
                            label = "Location (City, State)",
                            icon = Icons.Default.LocationOn
                        )
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            SharedInputField(
                                value = experience,
                                onValueChange = { experience = it },
                                label = "Experience",
                                icon = Icons.Default.History,
                                modifier = Modifier.weight(1f)
                            )
                            SharedInputField(
                                value = landSize,
                                onValueChange = { landSize = it },
                                label = "Land Size",
                                icon = Icons.Default.Landscape,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        
                        SharedInputField(
                            value = farmingType,
                            onValueChange = { farmingType = it },
                            label = "Farming Type",
                            icon = Icons.Default.Agriculture
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Save Button
                Button(
                    onClick = {
                        val updatedData = mapOf(
                            "fullName" to name,
                            "location" to location,
                            "experience" to experience,
                            "landSize" to landSize,
                            "farmingType" to farmingType
                        )
                        FirebaseManager.saveUserProfile("arjun_singh", updatedData)
                        onSaveSuccess()
                    },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Save Changes", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    FarmersTheme {
        EditProfileScreen()
    }
}
