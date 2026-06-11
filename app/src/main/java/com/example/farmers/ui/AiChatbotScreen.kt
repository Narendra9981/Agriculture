package com.example.farmers.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Send
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import com.example.farmers.ui.theme.*
import com.example.farmers.data.LanguageManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChatMessage(val text: String, val isFromUser: Boolean)

object ChatbotLocalizer {
    fun getGreeting(lang: String): List<ChatMessage> {
        return when (lang) {
            "Hindi (हिन्दी)" -> listOf(
                ChatMessage("नमस्ते किसान भाई! मैं एग्रीबॉट हूँ। आज मैं आपकी क्या सहायता कर सकता हूँ? 🌿", isFromUser = false),
                ChatMessage("इस मौसम के लिए कौन सी फसल सबसे अच्छी है?", isFromUser = true),
                ChatMessage("लुधियाना और वर्तमान अप्रैल के मौसम के आधार पर, गेहूं या सरसों बेहतरीन विकल्प हैं। क्या आप विस्तृत मार्गदर्शिका चाहेंगे? 🌾", isFromUser = false)
            )
            "Punjabi (ਪੰਜਾਬੀ)" -> listOf(
                ChatMessage("ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ ਕਿਸਾਨ ਵੀਰੋ! ਮੈਂ ਐਗਰੀਬੋਟ ਹਾਂ। ਅੱਜ ਮੈਂ ਤੁਹਾਡੀ ਕੀ ਮਦਦ ਕਰ ਸਕਦਾ ਹਾਂ? 🌿", isFromUser = false),
                ChatMessage("ਇਸ ਮੌਸਮ ਲਈ ਕਿਹੜੀ ਫਸਲ ਸਭ ਤੋਂ ਵਧੀਆ ਹੈ?", isFromUser = true),
                ChatMessage("ਲੁਧਿਆਣਾ ਅਤੇ ਮੌਜੂਦਾ ਅਪ੍ਰੈਲ ਦੇ ਮੌਸਮ ਦੇ ਅਧਾਰ ਤੇ, ਕਣਕ ਜਾਂ ਸਰ੍ਹੋਂ ਵਧੀਆ ਵਿਕਲਪ ਹਨ। ਕੀ ਤੁਸੀਂ ਵਿਸਤ੍ਰਿਤ ਗਾਈਡ ਚਾਹੁੰਦੇ ਹੋ? 🌾", isFromUser = false)
            )
            "Marathi (मराठी)" -> listOf(
                ChatMessage("नमस्कार शेतकरी बंधूंनो! मी एग्रीबॉट आहे. आज मी तुम्हाला कशी मदत करू शकतो? 🌿", isFromUser = false),
                ChatMessage("या हंगामासाठी कोणती पीक सर्वोत्तम आहे?", isFromUser = true),
                ChatMessage("लुधियाना आणि सध्याच्या एप्रिलमधील हवामानावर आधारित, गहू किंवा मोहरी हे उत्कृष्ट पर्याय आहेत. तुम्हाला तपशीलवार मार्गदर्शक हवे आहे का? 🌾", isFromUser = false)
            )
            "Gujarati (ગુજરાતી)" -> listOf(
                ChatMessage("નમસ્તે ખેડૂત મિત્રો! હું એગ્રીબોટ છું. આજે હું તમને કેવી રીતે મદદ કરી શકું? 🌿", isFromUser = false),
                ChatMessage("આ સીઝન માટે કયો પાક શ્રેષ્ઠ છે?", isFromUser = true),
                ChatMessage("લુધિયાણા અને વર્તમાન એપ્રિલ હવામાનના આધારે, ઘઉં અથવા રાઈ ઉત્તમ પસંદગી છે. શું તમને વિગતવાર માર્ગદર્શિકા ગમશે? 🌾", isFromUser = false)
            )
            "Kannada (ಕನ್ನಡ)" -> listOf(
                ChatMessage("ನಮಸ್ಕಾರ ರೈತ ಬಾಂಧವರೇ! ನಾನು ಅಗ್ರಿಬಾಟ್. ಇಂದು ನಿಮಗೆ ಹೇಗೆ ಸಹಾಯ ಮಾಡಲಿ? 🌿", isFromUser = false),
                ChatMessage("ಈ ಋತುವಿಗೆ ಯಾವ ಬೆಳೆ ಉತ್ತಮವಾಗಿದೆ?", isFromUser = true),
                ChatMessage("ಲೂಧಿಯಾನ ಮತ್ತು ಪ್ರಸ್ತುತ ಏಪ್ರಿಲ್ ಹವಾಮಾನದ ಆಧಾರದ ಮೇಲೆ, ಗೋಧಿ ಅಥವಾ ಸಾಸಿವೆ ಅತ್ಯುತ್ತಮ ಆಯ್ಕೆಗಳಾಗಿವೆ. ನಿಮಗೆ ವಿವರವಾದ ಮಾರ್ಗದರ್ಶಿ ಬೇಕೇ? 🌾", isFromUser = false)
            )
            "Telugu (తెలుగు)" -> listOf(
                ChatMessage("నమస్కారం రైతు సోదరులారా! నేను అగ్రిబాట్. ఈ రోజు మీకు ఏ విధంగా సహాయపడగలను? 🌿", isFromUser = false),
                ChatMessage("ఈ సీజన్ లో ఏ పంట ఉత్తమం?", isFromUser = true),
                ChatMessage("లూధియానా మరియు ప్రస్తుత ఏప్రిల్ వాతావరణం ఆధారంగా, గోధుమ లేదా ఆవాలు అద్భుతమైన ఎంపికలు. మీకు వివరణాత్మక గైడ్ కావాలా? 🌾", isFromUser = false)
            )
            else -> listOf(
                ChatMessage("Hello Farmer! I am AgriBot. How can I help you today? 🌿", isFromUser = false),
                ChatMessage("Which crop is best for this season?", isFromUser = true),
                ChatMessage("Based on your location (Ludhiana) and current April weather, Wheat or Mustard are excellent choices. Would you like a detailed guide? 🌾", isFromUser = false)
            )
        }
    }

    fun getPlaceholder(lang: String): String {
        return when (lang) {
            "Hindi (हिन्दी)" -> "अपनी खेती से जुड़ा प्रश्न पूछें..."
            "Punjabi (ਪੰਜਾਬੀ)" -> "ਆਪਣੇ ਖੇਤੀਬਾੜੀ ਸਬੰਧੀ ਪ੍ਰਸ਼ਨ ਪੁੱਛੋ..."
            "Marathi (मराठी)" -> "तुमचा शेतीविषयक प्रश्न विचारा..."
            "Gujarati (ગુજરાતી)" -> "તમારી ખેતી વિશે પ્રશ્ન પૂછો..."
            "Kannada (ಕನ್ನಡ)" -> "ನಿಮ್ಮ ಕೃಷಿ ಪ್ರಶ್ನೆಯನ್ನು ಕೇಳಿ..."
            "Telugu (తెలుగు)" -> "మీ వ్యవసాయ ప్రశ్నను అడగండి..."
            else -> "Ask your farming question..."
        }
    }

    fun getVoiceQuestion(lang: String): String {
        return when (lang) {
            "Hindi (हिन्दी)" -> "गेहूं के लिए सबसे अच्छा उर्वरक कौन सा है?"
            "Punjabi (ਪੰਜਾਬੀ)" -> "ਕਣਕ ਲਈ ਸਭ ਤੋਂ ਵਧੀਆ ਖਾਦ ਕਿਹੜੀ ਹੈ?"
            "Marathi (मराठी)" -> "गव्हासाठी कोणते खत सर्वोत्तम आहे?"
            "Gujarati (ગુજરાતી)" -> "ઘਉં માટે કયું ખાતર શ્રેષ્ઠ છે?"
            "Kannada (ಕನ್ನಡ)" -> "ಗೋಧಿಗೆ ಅತ್ಯುತ್ತಮ ಗೊಬ್ಬರ ಯಾವುದು?"
            "Telugu (తెలుగు)" -> "గోధుమలకు ఏ ఎరువులు ఉత్తమం?"
            else -> "What is the best fertilizer for wheat?"
        }
    }

    fun getVoiceTitle(lang: String): String {
        return when (lang) {
            "Hindi (हिन्दी)" -> "एग्रीबॉट सुन रहा है..."
            "Punjabi (ਪੰਜਾਬੀ)" -> "ਐਗਰੀਬੋਟ ਸੁਣ ਰਿਹਾ ਹੈ..."
            "Marathi (मराठी)" -> "एग्रीबॉट ऐकत आहे..."
            "Gujarati (ગુજરાતી)" -> "એગ્રીબોટ સાંભળી રહ્યું છે..."
            "Kannada (ಕನ್ನಡ)" -> "ಅಗ್ರಿಬಾಟ್ ಆಲಿಸುತ್ತಿದೆ..."
            "Telugu (తెలుగు)" -> "అగ్రిబాట్ వింటోంది..."
            else -> "AgriBot is listening..."
        }
    }

    fun getVoiceSubtitle(lang: String): String {
        return when (lang) {
            "Hindi (हिन्दी)" -> "अपनी खेती की समस्या बोलें"
            "Punjabi (ਪੰਜਾਬੀ)" -> "ਆਪਣੀ ਖੇਤੀ ਦੀ ਸਮੱਸਿਆ ਬੋਲੋ"
            "Marathi (मराठी)" -> "तुमची शेतीची समस्या सांगा"
            "Gujarati (ગુજરાતી)" -> "તમારી ખેતીની સમસ્યા બોલો"
            "Kannada (ಕನ್ನಡ)" -> "ನಿಮ್ಮ ಕೃಷಿ ಸಮಸ್ಯೆಯನ್ನು ತಿಳಿಸಿ"
            "Telugu (తెలుగు)" -> "మీ వ్యవసాయ समस्याను చెప్పండి"
            else -> "Speak your farming concern"
        }
    }

    fun getOnlineText(lang: String): String {
        return when (lang) {
            "Hindi (हिन्दी)" -> "ऑनलाइन"
            "Punjabi (ਪੰਜਾਬੀ)" -> "ਔਨਲਾਈਨ"
            "Marathi (मराठी)" -> "ऑनलाइन"
            "Gujarati (ગુજરાતી)" -> "ઓનલાઇન"
            "Kannada (ಕನ್ನಡ)" -> "ಆನ್‌ಲೈನ್"
            "Telugu (తెలుగు)" -> "ఆన్‌లైన్"
            else -> "Online"
        }
    }

    fun getAssistantTitle(lang: String): String {
        return when (lang) {
            "Hindi (हिन्दी)" -> "एग्रीबॉट एआई सहायक"
            "Punjabi (ਪੰਜਾਬੀ)" -> "ਐਗਰੀਬੋਟ ਏਆਈ ਸਹਾਇਕ"
            "Marathi (मराठी)" -> "एग्रीबॉट एआय सहाय्यक"
            "Gujarati (ગુજરાતી)" -> "એગ્રીબોટ એઆઈ સહાયક"
            "Kannada (ಕನ್ನಡ)" -> "ಅಗ್ರಿಬಾಟ್ ಎಐ ಸಹಾಯಕ"
            "Telugu (తెలుగు)" -> "అగ్రిబాట్ ఎఐ సహాయకుడు"
            else -> "AgriBot AI Assistant"
        }
    }

    fun getResponse(userMsg: String, lang: String): String {
        val msg = userMsg.lowercase()
        val isCrop = msg.contains("crop") || msg.contains("recommend") || msg.contains("फसल") || msg.contains("ਫਸਲ") || msg.contains("पीक") || msg.contains("પાક") || msg.contains("ಬೆಳೆ") || msg.contains("పంట")
        val isDisease = msg.contains("disease") || msg.contains("scan") || msg.contains("leaf") || msg.contains("रोग") || msg.contains("बीमारी") || msg.contains("ਪਛਾਣ") || msg.contains("ਬਿਮਾਰੀ") || msg.contains("ओळख") || msg.contains("પાન") || msg.contains("ಎಲೆ") || msg.contains("ఆకు")
        val isWeather = msg.contains("weather") || msg.contains("rain") || msg.contains("मौसम") || msg.contains("ਮੌਸਮ") || msg.contains("हवामान") || msg.contains("હવામાન") || msg.contains("ಹವಾಮಾನ") || msg.contains("వాతావరణ")
        val isFertilizer = msg.contains("fertilizer") || msg.contains("npk") || msg.contains("urea") || msg.contains("खाद") || msg.contains("उर्वरक") || msg.contains("ਖਾਦ") || msg.contains("खत") || msg.contains("ખાતર") || msg.contains("ಗೊಬ್ಬರ") || msg.contains("ఎరువులు")
        val isIrrigation = msg.contains("irrigation") || msg.contains("water") || msg.contains("सिंचाई") || msg.contains("ਸਿੰਚਾਈ") || msg.contains("सिंचन") || msg.contains("સિંચાઈ") || msg.contains("ನೀರಾವರಿ") || msg.contains("సాగు")
        val isScheme = msg.contains("scheme") || msg.contains("subsidy") || msg.contains("govt") || msg.contains("योजना") || msg.contains("ਯੋਜਨਾ") || msg.contains("યોજનાઓ") || msg.contains("ಯೋಜನೆ") || msg.contains("పథకం") || msg.contains("పథకాలు")
        val isHelp = msg.contains("help") || msg.contains("hello") || msg.contains("hi") || msg.contains("नमस्ते") || msg.contains("ਸਤਿ") || msg.contains("नमस्कार") || msg.contains("ನಮਸಕಾರ") || msg.contains("నమస్కారం")

        return when (lang) {
            "Hindi (हिन्दी)" -> {
                when {
                    isCrop -> "मैं हमारी एआई फसल अनुशंसा (Crop Recommendation) उपकरण का उपयोग करने की सलाह देता हूँ! यह मिट्टी के घटकों (NPK) का विश्लेषण करता है। 🌾"
                    isDisease -> "आप फसल रोगों की तुरंत पहचान करने के लिए हमारे एआई लीफ स्कैनर का उपयोग कर सकते हैं। प्रभावित पत्ते की एक फोटो लें! 🔍"
                    isWeather -> "आपके क्षेत्र में वर्तमान मौसम पूर्वानुमान धूप और 32°C है। यह कृषि प्रबंधन के लिए बहुत अच्छा समय है! ☀️"
                    isFertilizer -> "हमारा उर्वरक अनुशंसा उपकरण आपको आपके लक्षित फसल के आधार पर यूरिया या पोटाश की सटीक मात्रा डालने में मदद करता है। 🧪"
                    isIrrigation -> "पानी बचाने और मिट्टी की नमी बनाए रखने के लिए ड्रिप या स्प्रिंकलर सिंचाई का उपयोग करने की सलाह दी जाती है। 💧"
                    isScheme -> "आप पीएम-किसान या पीएम-कुसुम योजना के तहत सौर पंप सब्सिडी के लिए पात्र हो सकते हैं। सरकारी योजनाएं टैब देखें! 🏛️"
                    isHelp -> "मैं आपकी सहायता के लिए हूँ! आप मुझसे फसल अनुशंसा, पत्ती स्कैनिंग, मौसम, उर्वरक गाइड, या सरकारी योजनाओं के बारे में पूछ सकते हैं। 😊"
                    else -> "मैं समझता हूँ! आपके कृषि सहायक के रूप में, मैं डैशबोर्ड से हमारे मृदा स्वास्थ्य और स्मार्ट एआई उपकरणों की जाँच करने की सलाह देता हूँ। 🌿"
                }
            }
            "Punjabi (ਪੰਜਾਬੀ)" -> {
                when {
                    isCrop -> "ਮੈਂ ਸਾਡੀ ਏਆਈ ਫਸਲ ਸਿਫਾਰਸ਼ (Crop Recommendation) ਟੂਲ ਦੀ ਵਰਤੋਂ ਕਰਨ ਦੀ ਸਿਫਾਰਸ਼ ਕਰਦਾ ਹਾਂ! ਇਹ ਮਿੱਟੀ ਦੇ ਤੱਤਾਂ (NPK) ਦਾ ਵਿਸਲੇਸ਼ਣ ਕਰਦਾ ਹੈ। 🌾"
                    isDisease -> "ਤੁਸੀਂ ਫਸਲਾਂ ਦੀਆਂ ਬਿਮਾਰੀਆਂ ਦੀ ਤੁਰੰਤ ਪਛਾਣ ਕਰਨ ਲਈ ਸਾਡੇ ਏਆਈ ਲੀਫ ਸਕੈਨਰ ਦੀ ਵਰਤੋਂ ਕਰ ਸਕਦੇ ਹੋ। ਪ੍ਰਭਾਵਿਤ ਪੱਤੇ ਦੀ ਇੱਕ ਫੋਟੋ ਲਓ! 🔍"
                    isWeather -> "ਤੁਹਾਡੇ ਖੇਤਰ ਵਿੱਚ ਮੌਸਮ ਦੀ ਭਵਿੱਖਬਾਣੀ ਧੁੱਪ ਅਤੇ 32°C ਹੈ। ਖੇਤੀਬਾੜੀ ਦੇ ਕੰਮਾਂ ਲਈ ਵਧੀਆ ਸਮਾਂ ਹੈ! ☀️"
                    isFertilizer -> "ਸਾਡਾ ਖਾਦ ਗਾਈਡ ਟੂਲ ਤੁਹਾਨੂੰ ਤੁਹਾਡੀ ਫਸਲ ਦੇ ਅਧਾਰ ਤੇ ਯੂਰੀਆ ਜਾਂ ਪੋਟਾਸ਼ ਦੀ ਸਹੀ ਮਾਤਰਾ ਪਾਉਣ ਵਿੱਚ ਮਦਦ ਕਰਦਾ ਹੈ। 🧪"
                    isIrrigation -> "ਪਾਣੀ ਦੀ ਬਚਤ ਕਰਨ ਅਤੇ ਮਿੱਟੀ ਦੀ ਨਮੀ ਬਣਾਈ ਰੱਖਣ ਲਈ ਤੁਪਕਾ ਜਾਂ ਫੁਹਾਰਾ ਸਿੰਚਾਈ ਦੀ ਵਰਤੋਂ ਕਰਨ ਦੀ ਸਿਫਾਰਸ਼ ਕੀਤੀ ਜਾਂਦੀ ਹੈ। 💧"
                    isScheme -> "ਤੁਸੀਂ ਪੀਐਮ-ਕਿਸਾਨ ਜਾਂ ਪੀਐਮ-ਕੁਸੁਮ ਯੋਜਨਾ ਦੇ ਤਹਿਤ ਸਬਸਿਡੀਆਂ ਲਈ ਯੋਗ ਹੋ ਸਕਦੇ ਹੋ। ਸਰਕਾਰੀ ਯੋਜਨਾਵਾਂ ਟੈਬ ਦੇਖੋ! 🏛️"
                    isHelp -> "ਮੈਂ ਤੁਹਾਡੀ ਮਦਦ ਲਈ ਇੱਥੇ ਹਾਂ! ਤੁਸੀਂ ਮੈਨੂੰ ਫਸਲਾਂ ਦੀ ਸਿਫਾਰਸ਼, ਪੱਤੇ ਦੀ ਸਕੈਨਿੰਗ, ਮੌਸਮ, ਖਾਦ ਗਾਈਡਾਂ, ਜਾਂ ਸਰਕਾਰੀ ਸਕੀਮਾਂ ਬਾਰੇ ਪੁੱਛ ਸਕਦੇ ਹੋ। 😊"
                    else -> "ਮੈਂ ਸਮਝਦਾ ਹਾਂ! ਤੁਹਾਡੇ ਖੇਤੀ ਸਹਾਇਕ ਵਜੋਂ, ਮੈਂ ਡੈਸ਼ਬੋਰਡ ਤੋਂ ਸਾਡੀ ਮਿੱਟੀ ਦੀ ਸਿਹਤ ਅਤੇ ਸਮਾਰਟ ਏਆਈ ਟੂਲਸ ਦੀ ਜਾਂਚ ਕਰਨ ਦੀ ਸਿਫਾਰਸ਼ ਕਰਦਾ ਹਾਂ। 🌿"
                }
            }
            "Marathi (मराठी)" -> {
                when {
                    isCrop -> "मी आमचे एआय पीक शिफारस (Crop Recommendation) साधन वापरण्याचा सल्ला देतो! ते मातीचे (NPK) विश्लेषण करते. 🌾"
                    isDisease -> "पीक रोगांचे त्वरित वर्गीकरण करण्यासाठी तुम्ही आमचे एआय लीफ स्कॅनर साधन वापरू शकता. बाधित पानाचा फोटो घ्या! 🔍"
                    isWeather -> "तुमच्या प्रदेशात हवामान अंदाज ३२°C आणि ऊन आहे. शेती कामांसाठी ही योग्य वेळ आहे! ☀️"
                    isFertilizer -> "आमचे खत मार्गदर्शक तुम्हाला तुमच्या पिकाच्या उद्दिष्टानुसार युरिया किंवा पोटाशचे प्रमाण अचूकपणे वापरण्यास मदत करते. 🧪"
                    isIrrigation -> "पाण्याची बचत करण्यासाठी आणि मातीतील ओलावा टिकवून ठेवण्यासाठी ठिबक किंवा तुषार सिंचनाचा वापर करण्याचा सल्ला दिला जातो. 💧"
                    isScheme -> "तुम्ही पीएम-किसान किंवा पीएम-कुसुम योजनेअंतर्गत सौर पंप अनुदानासाठी पात्र असू शकता. सरकारी योजना टॅब पहा! 🏛️"
                    isHelp -> "मी तुमच्या मदतीसाठी येथे आहे! तुम्ही मला पीक शिफारस, पानांचे स्कॅनिंग, हवामान अंदाज, खत मार्गदर्शक किंवा सरकारी योजनांबद्दल विचारू शकता. 😊"
                    else -> "मी समजतो! तुमचा शेती सहाय्यक म्हणून, मी डॅशबोर्डवरून मृदा आरोग्य आणि स्मार्ट एआय साधनांची माहिती घेण्याचा सल्ला देतो. 🌿"
                }
            }
            "Gujarati (ગુજરાતી)" -> {
                when {
                    isCrop -> "હું અમારી એઆઈ પાકની ભલામણ (Crop Recommendation) સાધનનો ઉપયોગ કરવાની ભલામણ કરું છું! તે જમીનના (NPK) સ્તરનું વિશ્લેષણ કરે છે. 🌾"
                    isDisease -> "પાકના રોગોની ત્વરિત ઓળખ માટે તમે અમારા એઆઈ લીફ સ્કેનર સાધનનો ઉપયોગ કરી શકો છો. અસરગ્રસ્ત પાનનો ફોટો લો! 🔍"
                    isWeather -> "તમારા વિસ્તારમાં હવામાનની આગાહી ૩૨°C અને તડકો છે. આ ખેતી માટે ઉત્તમ સમય છે! ☀️"
                    isFertilizer -> "અમારા ખાતર માર્ગદર્શિકા તમને તમારા પાક લક્ષ્યના આધારે યુરિયા કે પોટાશની ચોક્કસ માત્રા વાપરવા માટે માર્ગદર્શન આપે છે. 🧪"
                    isIrrigation -> "પાણી બચાવવા અને જમીનમાં ભેજ જાળવવા માટે ટપક અથવા ફુવારા પદ્ધતિનો ઉપયોગ કરવાની ભલામણ કરવામાં આવે છે. 💧"
                    isScheme -> "તમે પીએમ-કિસાન અથવા પીએમ-કુસુમ યોજના હેઠળ સોલર پંપ સબસિડી માટે પાત્ર હોઈ શકો છો. સરકારી યોજનાઓ ટેબ જુઓ! 🏛️"
                    isHelp -> "હું તમારી મદદ માટે અહીં છું! તમે મને પાકની ભલામણ, પાનના સ્કેનિંગ, હવામાન, ખાતર અથવા સરકારી યોજનાઓ વિશે પૂછી શકો છો. 😊"
                    else -> "હું સમજું છું! તમારા ખેતી સહાયક તરીકે, હું ડેશબોર્ડ પરથી જમીનનું સ્વાસ્થ્ય અને સ્માર્ટ એઆઈ સાધનો તપાસવાની ભલામણ કરું છું. 🌿"
                }
            }
            "Kannada (ಕನ್ನಡ)" -> {
                when {
                    isCrop -> "ನಮ್ಮ ಎಐ ಬೆಳೆ ಶಿఫಾರಸು (Crop Recommendation) ಉಪಕರಣವನ್ನು ಬಳಸಲು ನಾನು ಶಿಫಾರಸು ಮಾಡುತ್ತೇನೆ! ಇದು ಮಣ್ಣಿನ (NPK) ಮಟ್ಟವನ್ನು ವಿಶ್ಲೇಷಿಸುತ್ತದೆ. 🌾"
                    isDisease -> "ಬೆಳೆ ರೋಗಗಳನ್ನು ತಕ್ಷಣ ಪತ್ತೆಹಚ್ಚಲು ನೀವು ನಮ್ಮ ಎಐ ಲೀಫ್ ಸ್ಕ್ಯಾನರ್ ಉಪಕರಣವನ್ನು ಬಳಸಬಹುದು. ಬಾಧಿತ ಎಲೆಯ ಫೋಟೋ ತೆಗೆಯಿರಿ! 🔍"
                    isWeather -> "ನಿಮ್ಮ ಪ್ರದೇಶದಲ್ಲಿ ಹವಾಮಾನ ಮುನ್ಸೂಚನೆ 32°C ಮತ್ತು ಬಿಸಿಲಿನಿಂದ ಕೂಡಿದೆ. ಇದು ಕೃಷಿಗೆ ಉತ್ತಮ ಸಮಯ! ☀️"
                    isFertilizer -> "ನಮ್ಮ ಗೊಬ್ಬರ ಮಾರ್ಗದರ್ಶಿ ನಿಮ್ಮ ಬೆಳೆಯ ಆಧಾರದ ಮೇಲೆ ಯೂರಿಯಾ ಅಥವಾ ಪೊಟ್ಯಾಶ್ ಅನ್ನು ಎಷ್ಟು ಪ್ರಮಾಣದಲ್ಲಿ ಬಳಸಬೇಕೆಂದು ಮಾರ್ಗದರ್ಶನ ನೀಡುತ್ತದೆ. 🧪"
                    isIrrigation -> "ನೀರನ್ನು ಉಳಿಸಲು ಮತ್ತು ಮಣ್ಣಿನ ತೇವಾಂಶವನ್ನು ಕಾಪಾಡಿಕೊಳ್ಳಲು ಹನಿ ಅಥವಾ ಸಿಂಪಡಿಸುವ ನೀರಾವರಿ ಬಳಸಲು ಶಿಫಾರಸು ಮಾಡಲಾಗಿದೆ. 💧"
                    isScheme -> "ನೀವು పిఎం-కిసాన్ లేదా పిఎం-కుసుమ్ పథకం కింద సోలార్ పంప్ సబ్సిడీకి అర్హులు కావచ్చు. ప్రభుత్వ పథకాల ట్యాబ్‌ను చూడండి! 🏛️"
                    isHelp -> "ನಾನು ನಿಮ್ಮ ಸಹಾಯಕ್ಕಾಗಿ ಇಲ್ಲಿದ್ದೇನೆ! ಬೆಳೆ ಶಿಫಾರಸು, ಎಲೆ ಸ್ಕ್ಯಾನಿಂಗ್, ಹವಾಮಾನ, ಗೊಬ್ಬರ ಮಾರ್ಗದರ್ಶಿ ಅಥವಾ ಸರ್ಕಾರಿ ಯೋಜನೆಗಳ ಬಗ್ಗೆ ಕೇಳಬಹುದು. 😊"
                    else -> "ನನಗೆ ಅರ್ಥವಾಯಿತು! ನಿಮ್ಮ ಕೃಷಿ ಸಹಾಯಕರಾಗಿ, ಡ್ಯಾಶ್‌ಬೋರ್ಡ್‌ನಿಂದ ನಮ್ಮ ಮಣ್ಣಿನ ಆರೋಗ್ಯ ಮತ್ತು ಸ್ಮಾರ್ಟ್ ಎಐ ಪರಿಕರಗಳನ್ನು ಪರಿಶೀಲಿಸಲು ನಾನು ಶಿಫಾರಸು ಮಾಡುತ್ತೇನೆ. 🌿"
                }
            }
            "Telugu (తెలుగు)" -> {
                when {
                    isCrop -> "మా ఎఐ పంట సిఫార్సు (Crop Recommendation) సాధనాన్ని ఉపయోగించమని నేను సిఫార్సు చేస్తున్నాను! ఇది నేలలోని (NPK) విలువలను విశ్లేషిస్తుంది. 🌾"
                    isDisease -> "పంట వ్యాధులను తక్షణమే గుర్తించడానికి మీరు మా ఎఐ లీఫ్ స్కానర్ సాధనాన్ని ఉపయోగించవచ్చు. వ్యాధి సోకిన ఆకు ఫోటో తీయండి! 🔍"
                    isWeather -> "మీ ప్రాంతంలో వాతావరణ సూచన 32°C మరియు ఎండగా ఉంటుంది. ఇది వ్యవసాయానికి అనుకూలమైన సమయం! ☀️"
                    isFertilizer -> "మా ఎరువుల గైడ్ మీ పంట లక్ష్యం ఆధారంగా యూరియా లేదా పొటాష్ ఎంత మోతాదులో వాడాలో మార్గదర్శకత్వం అందిస్తుంది. 🧪"
                    isIrrigation -> "నీటిని ఆదా చేయడానికి మరియు నేల తేమను కాపాడటానికి డ్రిప్ లేదా స్ప్రింక్లర్ సాగు పద్ధతిని ఉపయోగించడం మంచిది. 💧"
                    isScheme -> "మీరు పీఎం-キサన్ లేదా పీఎం-కుసుమ్ పథకం కింద సోలార్ పంప్ సబ్సిడీకి అర్హులు కావచ్చు. ప్రభుత్వ పథకాల ట్యాబ్‌ను చూడండి! 🏛️"
                    isHelp -> "నేను మీకు సహాయం చేయడానికి ఇక్కడ ఉన్నాను! పంట సిఫార్సు, ఆకు స్కానింగ్, వాతావరణం, ఎరువులు లేదా ప్రభుత్వ పథకాల గురించి నన్ను అడగవచ్చు. 😊"
                    else -> "నాకు అర్థమైంది! మీ వ్యవసాయ సహాయకుడిగా, డ్యాష్‌బోర్డ్ నుండి మా నేల ఆరోగ్యం మరియు ఎఐ సాధనాలను చూడాలని సిఫార్సు చేస్తున్నాను. 🌿"
                }
            }
            else -> {
                when {
                    isCrop -> "I recommend using our AI Crop Recommendation tool! It analyzes your soil's Nitrogen (N), Phosphorus (P), Potassium (K), and pH levels to suggest the perfect harvest. 🌾"
                    isDisease -> "You can use our AI Leaf Scanner tool to identify crop diseases instantly. Simply take a photo of the affected leaf, and the AI will analyze it! 🔍"
                    isWeather -> "The weather forecast in Ludhiana shows 32°C and sunny. It is a great time for irrigation or sowing depending on your crop lifecycle! ☀️"
                    isFertilizer -> "Our Fertilizer Recommendation tool guides you on exactly how much urea, phosphate, or potash to apply based on your crop target. 🧪"
                    isIrrigation -> "Using Drip or Sprinkler irrigation is highly recommended to save water and maintain optimal soil moisture. 💧"
                    isScheme -> "You might be eligible for PM-Kisan or solar pump subsidies under the PM-KUSUM scheme. Check the Welfare Schemes tab for direct links! 🏛️"
                    isHelp -> "I am here to assist you! You can ask me about Crop Recommendation, Leaf Scanning, Weather forecasts, Fertilizer guides, or Government Schemes. 😊"
                    else -> "I understand! As your farming assistant, I recommend checking our Soil Health and Smart AI tools from the dashboard. What specific farming guide do you need? 🌿"
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatbotScreen(
    onBack: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onSchemesClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    var messageText by remember { mutableStateOf("") }
    val currentLang = LanguageManager.currentLanguage
    
    val chatMessages = remember(currentLang) {
        mutableStateListOf<ChatMessage>().apply {
            addAll(ChatbotLocalizer.getGreeting(currentLang))
        }
    }
    
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var showVoiceSheet by remember { mutableStateOf(false) }

    // Scroll to bottom when messages update
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    if (showVoiceSheet) {
        VoiceAssistantBottomSheet(
            onDismissRequest = { showVoiceSheet = false },
            onSpeechRecognized = { spokenText ->
                if (spokenText.isNotBlank()) {
                    val userMsg = spokenText
                    chatMessages.add(ChatMessage(userMsg, isFromUser = true))
                    
                    coroutineScope.launch {
                        delay(800)
                        chatMessages.add(ChatMessage(
                            if (currentLang == "Hindi (हिन्दी)") "सोच रहा हूँ... 🤖" 
                            else if (currentLang == "Punjabi (ਪੰਜਾਬੀ)") "ਸੋਚ ਰਿਹਾ ਹਾਂ... 🤖" 
                            else "Thinking... 🤖", 
                            isFromUser = false
                        ))
                        delay(1200)
                        val response = ChatbotLocalizer.getResponse(userMsg, currentLang)
                        chatMessages.removeAt(chatMessages.size - 1) // Remove thinking
                        chatMessages.add(ChatMessage(response, isFromUser = false))
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            AgriChatTopBar(
                onBack = onBack,
                onMicClick = { showVoiceSheet = true }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.background(Color.White)) {
                SuggestionChipsRow { selected ->
                    chatMessages.add(ChatMessage(selected, true))
                    coroutineScope.launch {
                        delay(800)
                        chatMessages.add(ChatMessage(
                            if (currentLang == "Hindi (हिन्दी)") "सोच रहा हूँ... 🤖" 
                            else if (currentLang == "Punjabi (ਪੰਜਾਬੀ)") "ਸੋਚ ਰਿਹਾ ਹਾਂ... 🤖" 
                            else "Thinking... 🤖", 
                            isFromUser = false
                        ))
                        delay(1200)
                        val response = ChatbotLocalizer.getResponse(selected, currentLang)
                        chatMessages.removeAt(chatMessages.size - 1) // Remove thinking
                        chatMessages.add(ChatMessage(response, isFromUser = false))
                    }
                }
                AgriChatInput(
                    messageText = messageText,
                    onMessageChange = { messageText = it },
                    placeholder = ChatbotLocalizer.getPlaceholder(currentLang),
                    onScanClick = onScanClick,
                    onMicClick = { showVoiceSheet = true }
                ) {
                    if (messageText.isNotBlank()) {
                        val userMsg = messageText
                        chatMessages.add(ChatMessage(userMsg, isFromUser = true))
                        messageText = ""

                        coroutineScope.launch {
                            delay(800)
                            chatMessages.add(ChatMessage(
                                if (currentLang == "Hindi (हिन्दी)") "सोच रहा हूँ... 🤖" 
                                else if (currentLang == "Punjabi (ਪੰਜਾਬੀ)") "ਸੋਚ ਰਿਹਾ ਹਾਂ... 🤖" 
                                else "Thinking... 🤖", 
                                isFromUser = false
                            ))
                            delay(1200)
                            val response = ChatbotLocalizer.getResponse(userMsg, currentLang)
                            chatMessages.removeAt(chatMessages.size - 1) // Remove thinking
                            chatMessages.add(ChatMessage(response, isFromUser = false))
                        }
                    }
                }
                AgriBottomNavigation(
                    activeTab = "Bot",
                    onHomeClick = onHomeClick,
                    onScanClick = onScanClick,
                    onSchemesClick = onSchemesClick,
                    onMarketClick = onMarketClick,
                    onProfileClick = onProfileClick
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFF1F8E9), Color(0xFFE3F2FD))
                    )
                )
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    DiseaseScanShortcut(onScanClick)
                }

                items(chatMessages) { message ->
                    ChatBubble(message)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgriChatTopBar(
    onBack: () -> Unit,
    onMicClick: () -> Unit
) {
    val currentLang = LanguageManager.currentLanguage
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = ChatbotLocalizer.getAssistantTitle(currentLang),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(AgriVibrantGreen, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = ChatbotLocalizer.getOnlineText(currentLang), 
                            style = MaterialTheme.typography.labelMedium.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = AgriDarkGreen)
            }
        },
        actions = {
            IconButton(onClick = onMicClick) {
                Icon(Icons.Default.Mic, contentDescription = "Voice Assistant", tint = AgriVibrantGreen)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (message.isFromUser) AgriVibrantGreen else Color.White
    val textColor = if (message.isFromUser) Color.White else AgriDarkGreen
    val shape = if (message.isFromUser) {
        RoundedCornerShape(24.dp, 24.dp, 4.dp, 24.dp)
    } else {
        RoundedCornerShape(24.dp, 24.dp, 24.dp, 4.dp)
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Surface(
            color = bubbleColor,
            shape = shape,
            shadowElevation = 4.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(14.dp),
                style = MaterialTheme.typography.bodyLarge.copy(color = textColor, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
fun AgriChatInput(
    messageText: String,
    onMessageChange: (String) -> Unit,
    placeholder: String,
    onScanClick: () -> Unit,
    onMicClick: () -> Unit,
    onSend: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 12.dp,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onScanClick) {
                Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Upload Image", tint = AgriVibrantGreen)
            }
            
            TextField(
                value = messageText,
                onValueChange = onMessageChange,
                placeholder = { Text(placeholder, fontSize = 15.sp, fontWeight = FontWeight.Medium) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AgriDarkGreen,
                    unfocusedTextColor = AgriDarkGreen,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { onSend() })
            )
            
            IconButton(onClick = onMicClick) {
                Icon(Icons.Default.Mic, contentDescription = "Voice Input", tint = AgriVibrantGreen)
            }
            
            IconButton(
                onClick = onSend,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(AgriVibrantGreen)
                    .size(44.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
fun SuggestionChipsRow(onChipClick: (String) -> Unit = {}) {
    val suggestions = listOf(
        LanguageManager.getString("crop_rec") to Icons.Default.Agriculture,
        LanguageManager.getString("fert_guide") to Icons.Default.Science,
        LanguageManager.getString("disease_det") to Icons.Default.DocumentScanner,
        LanguageManager.getString("weather") to Icons.Default.WbCloudy
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(suggestions) { (label, icon) ->
            AssistChip(
                onClick = { onChipClick(label) },
                label = { Text(label, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = AgriGreen) },
                leadingIcon = { Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = AgriVibrantGreen) },
                shape = RoundedCornerShape(20.dp),
                colors = AssistChipDefaults.assistChipColors(containerColor = Color.White),
                elevation = AssistChipDefaults.assistChipElevation(4.dp)
            )
        }
    }
}

@Composable
fun DiseaseScanShortcut(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFE8F5E9),
        border = CardDefaults.outlinedCardBorder(),
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = AgriVibrantGreen, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Scan Crop Disease Instantly",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = AgriDarkGreen)
                )
                Text(
                    text = "Use AI camera to identify diseases",
                    style = MaterialTheme.typography.bodySmall.copy(color = AgriGreen, fontWeight = FontWeight.Bold)
                )
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(18.dp), tint = AgriVibrantGreen)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceAssistantBottomSheet(
    onDismissRequest: () -> Unit,
    onSpeechRecognized: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val currentLang = LanguageManager.currentLanguage
    
    // Waveform Animation State
    val infiniteTransition = rememberInfiniteTransition(label = "soundwave")
    val waveHeight1 by infiniteTransition.animateFloat(
        initialValue = 15f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
                15f at 0
                65f at 300
                15f at 600
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave1"
    )
    val waveHeight2 by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 850
                10f at 0
                80f at 400
                10f at 850
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave2"
    )
    val waveHeight3 by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 500
                20f at 0
                55f at 250
                20f at 500
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave3"
    )
    val waveHeight4 by infiniteTransition.animateFloat(
        initialValue = 15f,
        targetValue = 75f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 750
                15f at 0
                70f at 375
                15f at 750
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave4"
    )
    val waveHeight5 by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 400
                8f at 0
                45f at 200
                8f at 400
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave5"
    )

    // Pulse Animation for Mic Circle
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                1.0f at 0
                1.25f at 500
                1.0f at 1000
            },
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Simulate speech input delay
    LaunchedEffect(Unit) {
        delay(2500) // Listen for 2.5 seconds
        val question = ChatbotLocalizer.getVoiceQuestion(currentLang)
        onSpeechRecognized(question)
        sheetState.hide()
        onDismissRequest()
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp, top = 16.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = ChatbotLocalizer.getVoiceTitle(currentLang),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriDarkGreen
                )
            )
            
            Text(
                text = ChatbotLocalizer.getVoiceSubtitle(currentLang),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            )

            // Animated Visualizer Sound Waves
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val waveHeights = listOf(waveHeight1, waveHeight2, waveHeight3, waveHeight4, waveHeight5)
                val colors = listOf(AgriGreen, AgriVibrantGreen, AgriBlue, AgriVibrantGreen, AgriGreen)
                
                waveHeights.forEachIndexed { index, height ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .width(8.dp)
                            .height(height.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(colors[index])
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Pulsing Mic Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp)
            ) {
                // Outer Pulse Ring
                Box(
                    modifier = Modifier
                        .size((80 * pulseScale).dp)
                        .clip(CircleShape)
                        .background(AgriVibrantGreen.copy(alpha = 0.2f))
                )
                // Inner Mic Circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(AgriVibrantGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Microphone",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            
            Text(
                text = "\"${ChatbotLocalizer.getVoiceQuestion(currentLang)}\"",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = AgriGreen,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AiChatbotScreenPreview() {
    FarmersTheme {
        AiChatbotScreen()
    }
}

