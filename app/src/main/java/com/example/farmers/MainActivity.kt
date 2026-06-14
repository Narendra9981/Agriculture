package com.example.farmers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.farmers.ui.*
import com.example.farmers.ui.theme.FarmersTheme
import com.example.farmers.data.FirebaseManager
import com.example.farmers.data.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay

enum class Screen {
    Splash, Welcome, Onboarding1, Onboarding2, Onboarding3, Login, Registration, OtpVerification, HomeDashboard, AiChatbot, CropRecommendation, DiseaseDetection, DiseaseResult, WeatherForecast, FertilizerRecommendation, GovernmentSchemes, FertilizerPrediction, MarketPrice, UserProfile, Settings, SchemeDetail, EditProfile
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseManager.init(this) // Initialize Firebase connection
        enableEdgeToEdge()
        setContent {
            FarmersTheme {
                var currentScreen by remember { mutableStateOf(Screen.Splash) }
                var selectedSchemeName by remember { mutableStateOf("PM-Kisan Nidhi") }

                LaunchedEffect(Unit) {
                    delay(800) // Shortened splash delay
                    currentScreen = Screen.Login
                }

                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        // Instant transitions
                        fadeIn(animationSpec = tween(0)).togetherWith(fadeOut(animationSpec = tween(0)))
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        Screen.Splash -> SplashScreen()
                        Screen.Welcome -> WelcomeScreen(
                            onPhoneLoginClick = { currentScreen = Screen.Login },
                            onEmailLoginClick = { currentScreen = Screen.Login },
                            onSignUpClick = { currentScreen = Screen.Registration },
                            onLoginSuccess = {
                                currentScreen = Screen.HomeDashboard // Direct login to dashboard
                            }
                        )
                        Screen.Onboarding1 -> OnboardingScreen1(
                            onNext = { currentScreen = Screen.Onboarding2 },
                            onBack = { currentScreen = Screen.Welcome },
                            onSkip = { currentScreen = Screen.Login }
                        )
                        Screen.Onboarding2 -> OnboardingScreen2(
                            onNext = { currentScreen = Screen.Onboarding3 },
                            onBack = { currentScreen = Screen.Onboarding1 },
                            onSkip = { currentScreen = Screen.Login }
                        )
                        Screen.Onboarding3 -> OnboardingScreen3(
                            onGetStarted = { currentScreen = Screen.Login },
                            onBack = { currentScreen = Screen.Onboarding2 }
                        )
                        Screen.Login -> LoginScreen(
                            onLoginSuccess = { currentScreen = Screen.HomeDashboard },
                            onCreateAccount = { currentScreen = Screen.Registration },
                            onForgotPassword = { /* Navigate to Recovery */ },
                            onGoogleClick = { email -> 
                                AuthManager.loginMockUser(email)
                                currentScreen = Screen.HomeDashboard // Route directly to dashboard
                            }
                        )
                        Screen.Registration -> RegistrationScreen(
                            onRegisterSuccess = { currentScreen = Screen.HomeDashboard },
                            onBackToLogin = { currentScreen = Screen.Login }
                        )
                        Screen.OtpVerification -> OtpVerificationScreen(
                            onVerifySuccess = { currentScreen = Screen.HomeDashboard },
                            onBack = { currentScreen = Screen.Login } // Fix: Back to Login
                        )
                        Screen.HomeDashboard -> HomeDashboardScreen(
                            onNavigate = { destination ->
                                when (destination) {
                                    "Chat" -> currentScreen = Screen.AiChatbot
                                    "CropRec" -> currentScreen = Screen.CropRecommendation
                                    "Detection" -> currentScreen = Screen.DiseaseDetection
                                    "Weather" -> currentScreen = Screen.WeatherForecast
                                    "Fertilizer" -> currentScreen = Screen.FertilizerRecommendation
                                    "FertPred" -> currentScreen = Screen.FertilizerPrediction
                                    "Schemes" -> currentScreen = Screen.GovernmentSchemes
                                    "Market" -> currentScreen = Screen.MarketPrice
                                    "Profile" -> currentScreen = Screen.UserProfile
                                }
                            }
                        )
                        Screen.AiChatbot -> AiChatbotScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.CropRecommendation -> CropRecommendationScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.DiseaseDetection -> DiseaseDetectionScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onScanComplete = { currentScreen = Screen.DiseaseResult },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.DiseaseResult -> DiseaseResultScreen(
                            onBack = { currentScreen = Screen.DiseaseDetection },
                            onScanAnother = { currentScreen = Screen.DiseaseDetection },
                            onChatExpert = { currentScreen = Screen.AiChatbot },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.WeatherForecast -> WeatherForecastScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onChatExpert = { currentScreen = Screen.AiChatbot },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.FertilizerRecommendation -> FertilizerRecommendationScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.FertilizerPrediction -> FertilizerPredictionScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onAskAi = { currentScreen = Screen.AiChatbot },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.GovernmentSchemes -> GovernmentSchemesScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onAskAi = { currentScreen = Screen.AiChatbot },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile },
                            onSchemeApply = { schemeName ->
                                selectedSchemeName = schemeName
                                currentScreen = Screen.SchemeDetail 
                            }
                        )
                        Screen.SchemeDetail -> SchemeDetailScreen(
                            schemeName = selectedSchemeName,
                            onBack = { currentScreen = Screen.GovernmentSchemes },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                        Screen.MarketPrice -> {
                            LaunchedEffect(Unit) {
                                currentScreen = Screen.HomeDashboard
                            }
                        }
                        Screen.UserProfile -> UserProfileScreen(
                            onBack = { currentScreen = Screen.HomeDashboard },
                            onAskAi = { currentScreen = Screen.AiChatbot },
                            onSettingsClick = { currentScreen = Screen.Settings },
                            onEditClick = { currentScreen = Screen.EditProfile }
                        )
                        Screen.EditProfile -> EditProfileScreen(
                            onBack = { currentScreen = Screen.UserProfile },
                            onSaveSuccess = { currentScreen = Screen.UserProfile }
                        )
                        Screen.Settings -> SettingsScreen(
                            onBack = { currentScreen = Screen.UserProfile },
                            onLogout = {
                                AuthManager.logout()
                                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                                GoogleSignIn.getClient(this@MainActivity, gso).signOut()
                                currentScreen = Screen.Login
                            },
                            onEditProfile = { currentScreen = Screen.EditProfile },
                            onHomeClick = { currentScreen = Screen.HomeDashboard },
                            onChatClick = { currentScreen = Screen.AiChatbot },
                            onScanClick = { currentScreen = Screen.DiseaseDetection },
                            onSchemesClick = { currentScreen = Screen.GovernmentSchemes },
                            onMarketClick = { currentScreen = Screen.MarketPrice },
                            onProfileClick = { currentScreen = Screen.UserProfile }
                        )
                    }
                }
            }
        }
    }
}
