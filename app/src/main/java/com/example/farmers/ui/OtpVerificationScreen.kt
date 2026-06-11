package com.example.farmers.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmers.ui.theme.*
import com.example.farmers.data.AuthManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    onVerifySuccess: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var otpText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    
    var timerSeconds by remember { mutableIntStateOf(59) }
    var isVerifying by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = timerSeconds) {
        if (timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        }
    }

    // AUTO-VERIFY WITH REAL FIREBASE AUTH
    LaunchedEffect(otpText) {
        if (otpText.length == 6 && !isVerifying) {
            isVerifying = true
            focusManager.clearFocus()
            AuthManager.verifyOtp(otpText) { success, error ->
                isVerifying = false
                if (success) {
                    onVerifySuccess()
                } else {
                    Toast.makeText(context, "OTP Error: $error", Toast.LENGTH_LONG).show()
                    otpText = "" // Reset on failure
                    focusRequester.requestFocus()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(50)
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = AgriDarkGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, AgriWhite, Color(0xFFFFFDE7))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "AgriBot",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriGreen,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                OtpSecurityIllustration()

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Phone Verification",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriDarkGreen
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Enter the 6-digit code for secure login.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AgriDarkGreen.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = Color.White,
                    border = BorderStroke(2.dp, AgriGlow),
                    shadowElevation = 12.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = otpText,
                                onValueChange = { if (it.length <= 6) otpText = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                decorationBox = { }
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.clickable { focusRequester.requestFocus() }
                            ) {
                                repeat(6) { index ->
                                    val char = otpText.getOrNull(index)?.toString() ?: ""
                                    val isFocused = otpText.length == index
                                    
                                    Box(
                                        modifier = Modifier
                                            .width(46.dp)
                                            .height(58.dp)
                                            .background(if (isFocused) AgriVibrantGreen.copy(alpha = 0.1f) else Color.White, RoundedCornerShape(14.dp))
                                            .border(width = 2.dp, color = if (isFocused) AgriVibrantGreen else Color.LightGray, shape = RoundedCornerShape(14.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = char, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        if (timerSeconds > 0) {
                            Text(text = "Resend in 00:${timerSeconds.toString().padStart(2, '0')}", color = Color.Gray, fontWeight = FontWeight.Bold)
                        } else {
                            TextButton(onClick = { 
                                timerSeconds = 59 
                                // AuthManager.sendOtp(...) logic here
                            }) {
                                Text("Resend OTP Now", color = AgriVibrantGreen, fontWeight = FontWeight.ExtraBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        if (isVerifying) {
                            CircularProgressIndicator(color = AgriGreen)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun OtpSecurityIllustration() {
    val infiniteTransition = rememberInfiniteTransition(label = "security")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(150.dp).background(AgriGlow.copy(alpha = 0.3f), CircleShape))
        
        Icon(
            imageVector = Icons.Default.PhonelinkLock,
            contentDescription = null,
            modifier = Modifier.size(90.dp).graphicsLayer(scaleX = pulse, scaleY = pulse),
            tint = AgriGreen
        )

        Icon(
            imageVector = Icons.Default.VerifiedUser,
            contentDescription = null,
            modifier = Modifier
                .size(44.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-10).dp, y = (-10).dp)
                .background(Color.White, CircleShape)
                .padding(6.dp),
            tint = AgriAccent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OtpVerificationScreenPreview() {
    FarmersTheme {
        OtpVerificationScreen()
    }
}
