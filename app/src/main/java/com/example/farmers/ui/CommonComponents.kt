package com.example.farmers.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*
import com.example.farmers.data.LanguageManager

@Composable
fun AgriBottomNavigation(
    activeTab: String = "Home",
    onHomeClick: () -> Unit = {},
    onChatClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    NavigationBar(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp)),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val navItems = listOf(
            Triple(LanguageManager.getString("home"), Icons.Default.Home, "Home"),
            Triple(LanguageManager.getString("bot"), Icons.AutoMirrored.Filled.Chat, "Bot"),
            Triple(LanguageManager.getString("scan"), Icons.Default.CameraAlt, "Scan"),
            Triple(LanguageManager.getString("schemes"), Icons.Default.AccountBalance, "Schemes"),
            Triple(LanguageManager.getString("profile"), Icons.Default.Person, "Profile")
        )

        navItems.forEach { (label, icon, key) ->
            val isSelected = activeTab == key
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    when(key) {
                        "Home" -> onHomeClick()
                        "Bot" -> onChatClick()
                        "Scan" -> onScanClick()
                        "Schemes" -> onSchemesClick()
                        "Profile" -> onProfileClick()
                    }
                },
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AgriGreen,
                    selectedTextColor = AgriGreen,
                    indicatorColor = AgriGlow
                )
            )
        }
    }
}

@Composable
fun SharedInsightMiniCard(title: String, value: String, progress: Float, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
            Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = color))
            Spacer(modifier = Modifier.weight(1f))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                color = color,
                trackColor = color.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun SharedTipCard(title: String, desc: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.7f),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, tint = AgriGreen, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
            Text(text = desc, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun SharedResultInfoItem(label: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = AgriGreen.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
        Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
    }
}

@Composable
fun SharedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriGreen) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = { Icon(icon, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(20.dp)) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AgriDarkGreen,
            unfocusedTextColor = AgriDarkGreen,
            focusedBorderColor = AgriVibrantGreen,
            unfocusedBorderColor = AgriGlow
        )
    )
}
