package com.example.farmers.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.farmers.ui.theme.*
import com.example.farmers.data.LanguageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }

    // State for Dynamic Dropdowns
    var selectedLanguage by remember { mutableStateOf(LanguageManager.currentLanguage) }
    var selectedRegion by remember { mutableStateOf("Punjab") }
    var selectedUnit by remember { mutableStateOf("Metric (kg/acre)") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = AgriGreen, fontWeight = FontWeight.Bold)
                }
            },
            title = { Text(dialogTitle, fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen) },
            text = { Text("This feature is coming soon in the next version of AgriBot! 🚀", color = Color.Gray) },
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.White
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = LanguageManager.getString("settings"), 
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        dialogTitle = "Reset Settings"
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = AgriGreen)
                    }
                    IconButton(onClick = {
                        dialogTitle = "Help Center"
                        showDialog = true
                    }) {
                        Icon(Icons.Default.HelpOutline, contentDescription = "Help", tint = AgriGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AgriBottomNavigation(
                activeTab = "Profile",
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
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Account Settings
                SettingsSection(title = "Account Settings") {
                    SettingsOption(Icons.Default.Person, "Edit Profile", AgriBlue, onClick = onEditProfile)
                    SettingsOption(Icons.Default.Lock, "Change Password", Color(0xFF7E57C2), onClick = {
                        dialogTitle = "Change Password"
                        showDialog = true
                    })
                    SettingsOption(Icons.Default.Mail, "Manage Email / Phone", Color(0xFF26A69A), onClick = {
                        dialogTitle = "Manage Contact"
                        showDialog = true
                    })
                    SettingsOption(Icons.Default.Logout, "Logout", AgriRed, textColor = AgriRed, onClick = onLogout)
                }

                // Notifications
                SettingsSection(title = "Notifications") {
                    SettingsToggle(Icons.Default.WbSunny, "Weather Alerts", true, AgriOrange)
                    SettingsToggle(Icons.Default.BugReport, "Crop Disease Alerts", true, AgriRed)
                    SettingsToggle(Icons.Default.TrendingUp, "Market Price Updates", true, AgriBlue)
                    SettingsToggle(Icons.Default.AccountBalance, "Government Schemes", true, AgriOrange)
                    SettingsToggle(Icons.Default.Lightbulb, "AI Farming Tips", true, Color(0xFFFBC02D))
                }

                // Language & Region
                SettingsSection(title = LanguageManager.getString("lang_region")) {
                    val languages = listOf("English", "Hindi (हिन्दी)", "Punjabi (ਪੰਜਾਬੀ)", "Marathi (मराठी)", "Gujarati (ગુજરાતી)", "Kannada (ಕನ್ನಡ)", "Telugu (తెలుగు)", "Tamil (தமிழ்)")
                    SettingsDropdown(
                        icon = Icons.Default.Language,
                        label = LanguageManager.getString("app_lang"),
                        value = selectedLanguage,
                        options = languages,
                        onOptionSelected = { 
                            selectedLanguage = it
                            LanguageManager.setLanguage(it)
                        },
                        iconColor = Color(0xFF5C6BC0)
                    )
                    
                    val regions = listOf("Punjab", "Haryana", "Uttar Pradesh", "Maharashtra", "Gujarat", "Karnataka", "Andhra Pradesh")
                    SettingsDropdown(
                        icon = Icons.Default.Map,
                        label = "Region / State",
                        value = selectedRegion,
                        options = regions,
                        onOptionSelected = { selectedRegion = it },
                        iconColor = AgriGreen
                    )
                    
                    val units = listOf("Metric (kg/acre)", "Imperial (lb/acre)", "Local (Quintal/Bigha)")
                    SettingsDropdown(
                        icon = Icons.Default.Straighten,
                        label = "Measurement Units",
                        value = selectedUnit,
                        options = units,
                        onOptionSelected = { selectedUnit = it },
                        iconColor = Color(0xFF8D6E63)
                    )
                }

                // AI Assistant
                SettingsSection(title = "AgriBot AI Assistant") {
                    SettingsToggle(Icons.Default.Mic, "Voice Assistant", true, AgriVibrantGreen)
                    SettingsToggle(Icons.Default.History, "Save Chat History", true, Color(0xFF455A64))
                    SettingsToggle(Icons.Default.Quickreply, "Auto Suggestions", true, AgriBlue)
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            dialogTitle = "Reset AI"
                            showDialog = true
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AgriVibrantGreen.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, AgriVibrantGreen)
                    ) {
                        Text("Reset AI Preferences", color = AgriDarkGreen, fontWeight = FontWeight.ExtraBold)
                    }
                }

                // App Info
                SettingsSection(title = "App Info") {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("App Version", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen))
                        Text("AgriBot v1.0", style = MaterialTheme.typography.bodyLarge.copy(color = AgriGreen, fontWeight = FontWeight.ExtraBold))
                    }
                    SettingsOption(Icons.Default.DeleteSweep, "Clear Cache", AgriGreen, textColor = AgriGreen, onClick = {
                        dialogTitle = "Clear Cache"
                        showDialog = true
                    })
                    SettingsOption(Icons.Default.Info, "About AgriBot", AgriBlue, onClick = {
                        dialogTitle = "About AgriBot"
                        showDialog = true
                    })
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen),
            modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = BorderStroke(2.dp, AgriGlow),
            shadowElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                content()
            }
        }
    }
}

@Composable
fun SettingsOption(icon: ImageVector, label: String, iconColor: Color, textColor: Color = Color.Unspecified, onClick: () -> Unit = {}) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = label, 
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold, 
                    color = if (textColor != Color.Unspecified) textColor else AgriDarkGreen
                ), 
                modifier = Modifier.weight(1f)
            )
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun SettingsToggle(icon: ImageVector, label: String, initialValue: Boolean, iconColor: Color) {
    var checked by remember { mutableStateOf(initialValue) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(18.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen), modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = AgriVibrantGreen)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDropdown(
    icon: ImageVector,
    label: String,
    value: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    iconColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(18.dp))
            Text(text = label, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = AgriDarkGreen), modifier = Modifier.weight(1f))
            Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(color = AgriGreen, fontWeight = FontWeight.ExtraBold))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.LightGray
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(text = selectionOption, fontWeight = FontWeight.Bold, color = AgriDarkGreen)
                    },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    FarmersTheme {
        SettingsScreen()
    }
}
