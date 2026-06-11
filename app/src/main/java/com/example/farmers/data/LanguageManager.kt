package com.example.farmers.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LanguageManager {
    // Current selected language state - observed by the entire app
    var currentLanguage by mutableStateOf("English")
    
    // Supported languages map for logic
    private val languageCodes = mapOf(
        "English" to "en",
        "Hindi (हिन्दी)" to "hi",
        "Punjabi (ਪੰਜਾਬੀ)" to "pa",
        "Marathi (मराठी)" to "mr",
        "Gujarati (ગુજરાતી)" to "gu",
        "Kannada (ಕನ್ನಡ)" to "kn",
        "Telugu (తెలుగు)" to "te",
        "Tamil (தமிழ்)" to "ta"
    )

    fun setLanguage(language: String) {
        currentLanguage = language
    }

    /**
     * Helper function to get localized strings for the prototype.
     * In a production app, we would use Android string resources (strings.xml),
     * but for this high-fidelity prototype, we'll use a dynamic map to show
     * immediate language switching without restarting the activity.
     */
    fun getString(key: String): String {
        return translations[currentLanguage]?.get(key) ?: translations["English"]?.get(key) ?: key
    }

    private val translations = mapOf(
        "English" to mapOf(
            "app_name" to "AgriBot",
            "home" to "Home",
            "bot" to "Bot",
            "scan" to "Scan",
            "schemes" to "Schemes",
            "market" to "Market",
            "profile" to "Profile",
            "settings" to "Settings",
            "welcome" to "Welcome, Arjun Singh",
            "dashboard_title" to "Smart Farming Dashboard",
            "crop_rec" to "Crop Recommendation",
            "disease_det" to "Disease Detection",
            "weather" to "Weather Forecast",
            "fert_guide" to "Fertilizer Guide",
            "irrigation" to "Smart Irrigation",
            "mandi_price" to "Market Prices",
            "lang_region" to "Language & Region",
            "app_lang" to "App Language"
        ),
        "Hindi (हिन्दी)" to mapOf(
            "app_name" to "एग्रीबॉट",
            "home" to "मुख्य",
            "bot" to "बॉट",
            "scan" to "स्कैन",
            "schemes" to "योजनाएं",
            "market" to "बाज़ार",
            "profile" to "प्रोफ़ाइल",
            "settings" to "सेटिंग्स",
            "welcome" to "स्वागत है, अर्जुन सिंह",
            "dashboard_title" to "स्मार्ट खेती डैशबोर्ड",
            "crop_rec" to "फसल अनुशंसा",
            "disease_det" to "रोग की पहचान",
            "weather" to "मौसम का पूर्वानुमान",
            "fert_guide" to "उर्वरक गाइड",
            "irrigation" to "स्मार्ट सिंचाई",
            "mandi_price" to "मंडी भाव",
            "lang_region" to "भाषा और क्षेत्र",
            "app_lang" to "ऐप की भाषा"
        ),
        "Punjabi (ਪੰਜਾਬੀ)" to mapOf(
            "app_name" to "ਐਗਰੀਬੋਟ",
            "home" to "ਮੁੱਖ",
            "bot" to "ਬੋਟ",
            "scan" to "ਸਕੈਨ",
            "schemes" to "ਯੋਜਨਾਵਾਂ",
            "market" to "ਮਾਰਕੀਟ",
            "profile" to "ਪ੍ਰੋਫਾਈਲ",
            "settings" to "ਸੈਟਿੰਗਾਂ",
            "welcome" to "ਜੀ ਆਇਆਂ ਨੂੰ, ਅਰਜੁਨ ਸਿੰਘ",
            "dashboard_title" to "ਸਮਾਰਟ ਖੇਤੀ ਡੈਸ਼ਬੋਰਡ",
            "crop_rec" to "ਫਸਲ ਦੀ ਸਿਫਾਰਸ਼",
            "disease_det" to "ਬਿਮਾਰੀ ਦੀ ਪਛਾਣ",
            "weather" to "ਮੌਸਮ ਦੀ ਭਵਿੱਖਬਾਣੀ",
            "fert_guide" to "ਖਾਦ ਗਾਈਡ",
            "irrigation" to "ਸਮਾਰਟ ਸਿੰਚਾਈ",
            "mandi_price" to "ਮੰਡੀ ਦੇ ਭਾਅ",
            "lang_region" to "ਭਾਸ਼ਾ ਅਤੇ ਖੇਤਰ",
            "app_lang" to "ਐਪ ਦੀ ਭਾਸ਼ਾ"
        ),
        "Marathi (मराठी)" to mapOf(
            "app_name" to "एग्रीबॉट",
            "home" to "मुख्य",
            "bot" to "बॉट",
            "scan" to "स्कॅन",
            "schemes" to "योजना",
            "market" to "बाजार",
            "profile" to "प्रोफाइल",
            "settings" to "सेटिंग्स",
            "welcome" to "स्वागत आहे, अर्जुन सिंह",
            "dashboard_title" to "स्मार्ट शेती डॅशबोर्ड",
            "crop_rec" to "पीक शिफारस",
            "disease_det" to "रोग ओळख",
            "weather" to "हवामान अंदाज",
            "fert_guide" to "खत मार्गदर्शक",
            "irrigation" to "स्मार्ट सिंचन",
            "mandi_price" to "बाजार भाव",
            "lang_region" to "भाषा आणि प्रदेश",
            "app_lang" to "अॅपची भाषा"
        ),
        "Gujarati (ગુજરાતી)" to mapOf(
            "app_name" to "એગ્રીબોટ",
            "home" to "મુખ્ય",
            "bot" to "બોટ",
            "scan" to "સ્કેન",
            "schemes" to "યોજનાઓ",
            "market" to "બજાર",
            "profile" to "પ્રોફાઇલ",
            "settings" to "સેટિંગ્સ",
            "welcome" to "સ્વાગત છે, અર્જુન સિંહ",
            "dashboard_title" to "સ્માર્ટ ખેતી ડેશબોર્ડ",
            "crop_rec" to "પાકની ભલામણ",
            "disease_det" to "રોગની ઓળખ",
            "weather" to "હવામાન આગાહી",
            "fert_guide" to "ખાતર માર્ગદર્શિકા",
            "irrigation" to "સ્માર્ટ સિંચાઈ",
            "mandi_price" to "બજાર ભાવ",
            "lang_region" to "ભાષા અને પ્રદેશ",
            "app_lang" to "એપ્લિકેશન ભાષા"
        ),
        "Kannada (ಕನ್ನಡ)" to mapOf(
            "app_name" to "ಅಗ್ರಿಬಾಟ್",
            "home" to "ಮುಖಪುಟ",
            "bot" to "ಬಾಟ್",
            "scan" to "ಸ್ಕ್ಯಾನ್",
            "schemes" to "ಯೋಜನೆಗಳು",
            "market" to "ಮಾರುಕಟ್ಟೆ",
            "profile" to "ಪ್ರೊಫೈಲ್",
            "settings" to "ಸೆಟ್ಟಿಂಗ್‌ಗಳು",
            "welcome" to "ಸ್ವಾಗತ, ಅರ್ಜುನ್ ಸಿಂಗ್",
            "dashboard_title" to "ಸ್ಮಾರ್ಟ್ ಕೃಷಿ ಡ್ಯಾಶ್‌ಬೋರ್ಡ್",
            "crop_rec" to "ಬೆಳೆ ಶಿಫಾರಸು",
            "disease_det" to "ರೋಗ ಪತ್ತೆ",
            "weather" to "ಹವಾಮಾನ ಮುನ್ಸೂಚನೆ",
            "fert_guide" to "ಗೊಬ್ಬರ ಮಾರ್ಗದರ್ಶಿ",
            "irrigation" to "ಸ್ಮಾರ್ಟ್ ನೀರಾವರಿ",
            "mandi_price" to "ಮಾರುಕಟ್ಟೆ ದರಗಳು",
            "lang_region" to "ಭಾಷೆ ಮತ್ತು ಪ್ರದೇಶ",
            "app_lang" to "ಅಪ್ಲಿಕೇಶನ್ ಭಾಷೆ"
        ),
        "Telugu (తెలుగు)" to mapOf(
            "app_name" to "అగ్రిబాట్",
            "home" to "హోమ్",
            "bot" to "బాట్",
            "scan" to "స్కాన్",
            "schemes" to "పథకాలు",
            "market" to "మార్కెట్",
            "profile" to "ప్రొఫైల్",
            "settings" to "సెట్టింగ్‌లు",
            "welcome" to "స్వాగతం, అర్జున్ సింగ్",
            "dashboard_title" to "స్మార్ట్ ఫార్మింగ్ డాష్‌బోర్డ్",
            "crop_rec" to "పంట సిఫార్సు",
            "disease_det" to "వ్యాధి గుర్తింపు",
            "weather" to "హవామాన సూచన",
            "fert_guide" to "ఎరువుల గైడ్",
            "irrigation" to "స్మార్ట్ సాగు",
            "mandi_price" to "మార్కెట్ ధరలు",
            "lang_region" to "భాష మరియు ప్రాంతం",
            "app_lang" to "యాప్ భాష"
        ),
        "Tamil (தமிழ்)" to mapOf(
            "app_name" to "அக்ரிபாட்",
            "home" to "முகப்பு",
            "bot" to "பாட்",
            "scan" to "ஸ்கேன்",
            "schemes" to "திட்டங்கள்",
            "market" to "சந்தை",
            "profile" to "சுயவிவரம்",
            "settings" to "அமைப்புகள்",
            "welcome" to "வரவேற்கிறோம், அர்ஜுன் சிங்",
            "dashboard_title" to "ஸ்மார்ட் விவசாய டாஷ்போர்டு",
            "crop_rec" to "பயிர் பரிந்துரை",
            "disease_det" to "நோய் கண்டறிதல்",
            "weather" to "வானிலை முன்னறிவிப்பு",
            "fert_guide" to "உர வழிகாட்டி",
            "irrigation" to "ஸ்மார்ட் நீர்ப்பாசனம்",
            "mandi_price" to "சந்தை விலைகள்",
            "lang_region" to "மொழி மற்றும் பிராந்தியம்",
            "app_lang" to "பயன்பாட்டு மொழி"
        )
    )
}
