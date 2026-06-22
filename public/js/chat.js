import { t } from "./translations.js";
import { currentState } from "./app.js";

// --- CHATBOT SCREEN ACTIONS ---

const chatGreetings = {
    "Hindi (हिन्दी)": [
        { text: "नमस्ते किसान भाई! मैं एग्रीबॉट हूँ। आज मैं आपकी क्या सहायता कर सकता हूँ? 🌿", isFromUser: false },
        { text: "इस मौसम के लिए कौन सी फसल सबसे अच्छी है?", isFromUser: true },
        { text: "लुधियाना और वर्तमान अप्रैल के मौसम के आधार पर, गेहूं या सरसों बेहतरीन विकल्प हैं। क्या आप विस्तृत मार्गदर्शिका चाहेंगे? 🌾", isFromUser: false }
    ],
    "Punjabi (ਪੰਜਾਬੀ)": [
        { text: "ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ ਕਿਸਾਨ ਵੀਰੋ! ਮੈਂ ਐਗਰੀਬੋਟ ਹਾਂ। ਅੱਜ ਮੈਂ ਤੁਹਾਡੀ ਕੀ ਮਦਦ ਕਰ ਸਕਦਾ ਹਾਂ? 🌿", isFromUser: false },
        { text: "ਇਸ ਮੌਸਮ ਲਈ ਕਿਹੜੀ ਫਸਲ ਸਭ ਤੋਂ ਵਧੀਆ ਹੈ?", isFromUser: true },
        { text: "ਲੁਧਿਆਣਾ ਅਤੇ ਮੌਜੂਦਾ ਅਪ੍ਰੈਲ ਦੇ ਮੌਸਮ ਦੇ ਅਧਾਰ ਤੇ, ਕਣਕ ਜਾਂ ਸਰ੍ਹੋਂ ਵਧੀਆ ਵਿਕਲਪ ਹਨ। ਕੀ ਤੁਸੀਂ ਵਿਸਤ੍ਰਿਤ ਗਾਈਡ ਚਾਹੁੰਦੇ ਹੋ? 🌾", isFromUser: false }
    ],
    "Marathi (मराठी)": [
        { text: "नमस्कार शेतकरी बंधूंनो! मी एग्रीबॉट आहे. आज मी तुम्हाला कशी मदत करू शकतो? 🌿", isFromUser: false },
        { text: "या हंगामासाठी कोणती पीक सर्वोत्तम आहे?", isFromUser: true },
        { text: "लुधियाना आणि सध्याच्या एप्रिलमधील हवामानावर आधारित, गहू किंवा मोहरी हे उत्कृष्ट पर्याय आहेत. तुम्हाला तपशीलवार मार्गदर्शक हवे आहे का? 🌾", isFromUser: false }
    ],
    "Gujarati (ગુજરાતી)": [
        { text: "નમસ્તે ખેડૂત મિત્રો! હું એગ્રીબોટ છું. આજે હું તમને કેવી રીતે મદદ કરી શકું? 🌿", isFromUser: false },
        { text: "આ સીઝન માટે કયો પાક શ્રેષ્ઠ છે?", isFromUser: true },
        { text: "અહીં અને વર્તમાન એપ્રિલ હવામાનના આધારે, ઘઉં અથવા રાઈ ઉત્તમ પસંદગી છે. શું તમને વિગતવાર માર્ગદર્શિકા ગમશે? 🌾", isFromUser: false }
    ],
    "Kannada (ಕನ್ನಡ)": [
        { text: "ನಮಸ್ಕಾರ ರೈತ ಬಾಂಧವರೇ! ನಾನು ಅಗ್ರಿಬಾಟ್. ಇಂದು ನಿಮಗೆ ಹೇಗೆ ಸಹಾಯ ಮಾಡಲಿ? 🌿", isFromUser: false },
        { text: "ಈ ಋತುವಿಗೆ ಯಾವ ಬೆಳೆ ಉತ್ತಮವಾಗಿದೆ?", isFromUser: true },
        { text: "ಲೂಧಿಯಾನ ಮತ್ತು ಪ್ರಸ್ತುत ಏಪ್ರಿಲ್ ಹವಾಮಾನದ ಆಧಾರದ ಮೇಲೆ, ಗೋಧಿ ಅಥವಾ ಸಾಸಿವೆ ಅತ್ಯುತ್ತಮ ಆಯ್ಕೆಗಳಾಗಿವೆ. ನಿಮಗೆ ವಿವರವಾದ ಮಾರ್ಗದರ್ಶಿ ಬೇಕೇ? 🌾", isFromUser: false }
    ],
    "Telugu (తెలుగు)": [
        { text: "నమస్కారం రైతు సోదరులారా! నేను అగ్రిబాట్. ఈ రోజు మీకు ఏ విధంగా సహాయపడగలను? 🌿", isFromUser: false },
        { text: "ఈసీజన్ లో ఏ పంట ఉత్తమం?", isFromUser: true },
        { text: "లూధియానా మరియు ప్రస్తుత ఏప్రిల్ వాతావరణం ఆధారంగా, గోధుమ లేదా ఆవాలు అద్భుతమైన ఎంపికలు. మీకు వివరణాత్మక గైడ్ కావాలా? 🌾", isFromUser: false }
    ],
    "Tamil (தமிழ்)": [
        { text: "வணக்கம் விவசாயி! நான் அக்ரிபாட். இன்று நான் உங்களுக்கு எவ்வாறு உதவ முடியும்? 🌿", isFromUser: false },
        { text: "இந்த பருவத்திற்கு எந்த பயிர் சிறந்தது?", isFromUser: true },
        { text: "லுதியானா மற்றும் தற்போதைய ஏப்ரல் வானிலையின் அடிப்படையில், கோதுமை அல்லது கடுகு சிறந்த தேர்வுகள். விரிவான வழிகாட்டி வேண்டுமா? 🌾", isFromUser: false }
    ],
    "English": [
        { text: "Hello Farmer! I am AgriBot. How can I help you today? 🌿", isFromUser: false },
        { text: "Which crop is best for this season?", isFromUser: true },
        { text: "Based on your location (Ludhiana) and current April weather, Wheat or Mustard are excellent choices. Would you like a detailed guide? 🌾", isFromUser: false }
    ]
};

export function initChatGreeting() {
    const chatContainer = document.getElementById("chat-messages-container");
    if (!chatContainer) return;
    
    chatContainer.innerHTML = "";
    const lang = currentState.currentLanguage;
    const messages = chatGreetings[lang] || chatGreetings["English"];
    
    messages.forEach(msg => {
        appendChatBubble(msg.text, msg.isFromUser);
    });

    // Suggestion chips list
    const chipsWrapper = document.getElementById("chat-suggestion-chips");
    if (chipsWrapper) {
        const chipsList = [
            { label: t("crop_rec", lang), icon: "🌾" },
            { label: t("fert_guide", lang), icon: "🧪" },
            { label: t("disease_det", lang), icon: "🔍" },
            { label: t("weather", lang), icon: "☀️" }
        ];
        chipsWrapper.innerHTML = chipsList.map(c => `
            <div class="chip" onclick="handleChipClick('${c.label}')">
                <span>${c.icon}</span> ${c.label}
            </div>
        `).join('');
    }

    // Input placeholder update
    const chatInput = document.getElementById("chat-input-field");
    if (chatInput) {
        chatInput.placeholder = t("chat_placeholder", lang);
    }
}

// Make initChatGreeting accessible globally for language changes
window.initChatGreeting = initChatGreeting;

function appendChatBubble(text, isFromUser) {
    const chatContainer = document.getElementById("chat-messages-container");
    if (!chatContainer) return;

    const bubble = document.createElement("div");
    bubble.className = `chat-bubble ${isFromUser ? 'user' : 'bot'}`;
    bubble.textContent = text;
    chatContainer.appendChild(bubble);
    
    // Auto scroll
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

window.handleChipClick = function(chipLabel) {
    appendChatBubble(chipLabel, true);
    triggerChatBotResponse(chipLabel);
};

window.handleChatSubmit = function(e) {
    if (e.key === "Enter") {
        sendChatMessage();
    }
};

window.sendChatMessage = function() {
    const input = document.getElementById("chat-input-field");
    const val = input.value.trim();
    if (!val) return;

    appendChatBubble(val, true);
    input.value = "";

    triggerChatBotResponse(val);
};

function triggerChatBotResponse(userMsg) {
    const lang = currentState.currentLanguage;
    
    setTimeout(() => {
        appendChatBubble(t("thinking", lang), false);
        
        setTimeout(() => {
            const chatContainer = document.getElementById("chat-messages-container");
            if (chatContainer && chatContainer.lastChild) {
                chatContainer.removeChild(chatContainer.lastChild);
            }

            const finalResp = getChatbotLocalResponse(userMsg, lang);
            appendChatBubble(finalResp, false);
        }, 1200);
    }, 600);
}

function getChatbotLocalResponse(userMsg, lang) {
    const msg = userMsg.toLowerCase();
    
    const isCrop = msg.includes("crop") || msg.includes("recommend") || msg.includes("फसल") || msg.includes("ਫਸਲ") || msg.includes("पीक") || msg.includes("પાક") || msg.includes("ಬೆಳೆ") || msg.includes("పంట") || msg.includes("பயிர்");
    const isDisease = msg.includes("disease") || msg.includes("scan") || msg.includes("leaf") || msg.includes("रोग") || msg.includes("बीमारी") || msg.includes("ਪਛਾਣ") || msg.includes("ਬਿਮਾਰੀ") || msg.includes("ओळख") || msg.includes("ਪਾਨ") || msg.includes("ਐਲੇ") || msg.includes("ఆకు") || msg.includes("இலை") || msg.includes("நோய்");
    const isWeather = msg.includes("weather") || msg.includes("rain") || msg.includes("मौसम") || msg.includes("ਮੌਸਮ") || msg.includes("हवामान") || msg.includes("હવામાન") || msg.includes("ಹವಾಮಾನ") || msg.includes("వాతావరణ");
    const isFertilizer = msg.includes("fertilizer") || msg.includes("npk") || msg.includes("urea") || msg.includes("खाद") || msg.includes("उर्वरक") || msg.includes("ਖਾਦ") || msg.includes("खत") || msg.includes("ખાતર") || msg.includes("ಗೊಬ್ಬರ") || msg.includes("ఎరువులు");
    const isIrrigation = msg.includes("irrigation") || msg.includes("water") || msg.includes("सिंचाई") || msg.includes("ਸਿੰਚਾਈ") || msg.includes("सिंचन") || msg.includes("સિંચાઈ") || msg.includes("ನೀರಾವರಿ") || msg.includes("సాగు");
    const isScheme = msg.includes("scheme") || msg.includes("subsidy") || msg.includes("govt") || msg.includes("योजना") || msg.includes("ਯੋਜਨਾ") || msg.includes("યોજનાઓ") || msg.includes("ಯೋಜਨੇ") || msg.includes("పథకం") || msg.includes("పథకాలు");
    const isHelp = msg.includes("help") || msg.includes("hello") || msg.includes("hi") || msg.includes("नमस्ते") || msg.includes("ਸਤਿ") || msg.includes("नमस्कार") || msg.includes("ನಮಸ್ಕಾರ") || msg.includes("నమస్కారం");

    if (lang === "Hindi (हिन्दी)") {
        if (isCrop) return "मैं हमारी एआई फसल अनुशंसा (Crop Recommendation) उपकरण का उपयोग करने की सलाह देता हूँ! यह मिट्टी के घटकों (NPK) का विश्लेषण करता है। 🌾";
        if (isDisease) return "आप फसल रोगों की तुरंत पहचान करने के लिए हमारे एआई लीफ स्कैनर का उपयोग कर सकते हैं। प्रभावित पत्ते की एक फोटो लें! 🔍";
        if (isWeather) return "आपके क्षेत्र में वर्तमान मौसम पूर्वानुमान धूप और 32°C है। यह कृषि प्रबंधन के लिए बहुत अच्छा समय है! ☀️";
        if (isFertilizer) return "हमारा उर्वरक अनुशंसा उपकरण आपको आपके लक्षित फसल के आधार पर यूरिया या पोटाश की सटीक मात्रा डालने में मदद करता है। 🧪";
        if (isIrrigation) return "पानी बचाने और मिट्टी की नमी बनाए रखने के लिए ड्रिप या स्प्रिंकलर सिंचाई का उपयोग करने की सलाह दी जाती है। 💧";
        if (isScheme) return "आप पीएम-किसान या पीएम-कुसुम योजना के तहत सौर पंप सब्सिडी के लिए पात्र हो सकते हैं। सरकारी योजनाएं टैब देखें! 🏛️";
        if (isHelp) return "मैं आपकी सहायता के लिए हूँ! आप मुझसे फसल अनुशंसा, पत्ती स्कैनिंग, मौसम, उर्वरक गाइड, या सरकारी योजनाओं के बारे में पूछ सकते हैं। 😊";
        return "मैं समझता हूँ! आपके कृषि सहायक के रूप में, मैं डैशबोर्ड से हमारे मृदा स्वास्थ्य और स्मार्ट एआई उपकरणों की जाँच करने की सलाह देता हूँ। 🌿";
    }
    if (lang === "Punjabi (ਪੰਜਾਬੀ)") {
        if (isCrop) return "ਮੈਂ ਸਾਡੀ ਏਆਈ ਫਸਲ ਸਿਫਾਰਸ਼ (Crop Recommendation) ਟੂਲ ਦੀ ਵਰਤੋਂ ਕਰਨ ਦੀ ਸਿਫਾਰਸ਼ ਕਰਦਾ ਹਾਂ! ਇਹ ਮਿੱਟੀ ਦੇ ਤੱਤਾਂ (NPK) ਦਾ ਵਿਸਲੇਸ਼ਣ ਕਰਦਾ ਹੈ। 🌾";
        if (isDisease) return "ਤੁਸੀਂ ਫਸਲਾਂ ਦੀਆਂ ਬਿਮਾਰੀਆਂ ਦੀ ਤੁਰੰਤ ਪਛਾਣ ਕਰਨ ਲਈ ਸਾਡੇ ਏਆਈ ਲੀਫ ਸਕੈਨਰ ਦੀ ਵਰਤੋਂ ਕਰ ਸਕਦੇ ਹੋ। ਪ੍ਰਭਾਵਿਤ ਪੱਤੇ ਦੀ ਇੱਕ ਫੋਟੋ ਲਓ! 🔍";
        if (isWeather) return "ਤੁਹਾਡੇ ਖੇਤਰ ਵਿੱਚ ਮੌਸਮ ਦੀ ਭਵਿੱਖਬਾਣੀ ਧੁੱਪ ਅਤੇ 32°C ਹੈ। ਖੇਤੀਬਾੜੀ ਦੇ ਕੰਮਾਂ ਲਈ ਵਧੀਆ ਸਮਾਂ ਹੈ! ☀️";
        if (isFertilizer) return "ਸਾਡਾ ਖਾਦ ਗਾਈਡ ਟੂਲ ਤੁਹਾਨੂੰ ਤੁਹਾਡੀ ਫਸਲ ਦੇ ਅਧਾਰ ਤੇ ਯੂਰੀਆ ਜਾਂ ਪੋਟਾਸ਼ ਦੀ ਸਹੀ ਮਾਤਰਾ ਪਾਉਣ ਵਿੱਚ ਮਦਦ ਕਰਦਾ ਹੈ। 🧪";
        if (isIrrigation) return "ਪਾਣੀ ਦੀ ਬਚਤ ਕਰਨ ਅਤੇ ਮਿੱਟੀ ਦੀ ਨਮੀ ਬਣਾਈ ਰੱਖਣ ਲਈ ਤੁਪਕਾ ਜਾਂ ਫੁਹਾਰਾ ਸਿੰਚਾਈ ਦੀ ਵਰਤੋਂ ਕਰਨ ਦੀ ਸਿਫਾਰਸ਼ ਕੀਤੀ ਜਾਂਦੀ ਹੈ। 💧";
        if (isScheme) return "ਤੁਸੀਂ ਪੀਐਮ-ਕਿਸਾਨ ਜਾਂ ਪੀਐਮ-ਕੁਸੁਮ ਯੋਜਨਾ ਦੇ ਤਹਿਤ ਸਬਸਿਡੀਆਂ ਲਈ ਯੋਗ ਹੋ ਸਕਦੇ ਹੋ। ਸਰਕਾਰੀ ਯੋਜਨਾਵਾਂ ਟੈਬ ਦੇਖੋ! 🏛️";
        if (isHelp) return "ਮੈਂ ਤੁਹਾਡੀ ਮਦਦ ਲਈ ਇੱਥੇ ਹਾਂ! ਤੁਸੀਂ ਮੈਨੂੰ ਫਸਲਾਂ ਦੀ ਸਿਫਾਰਸ਼, ਪੱਤੇ ਦੀ ਸਕੈਨਿੰਗ, ਮੌਸਮ, ਖਾਦ ਗਾਈਡਾਂ, ਜਾਂ ਸਰਕਾਰੀ ਸਕੀਮਾਂ ਬਾਰੇ ਪੁੱਛ ਸਕਦੇ ਹੋ। 😊";
        return "ਮੈਂ ਸਮਝਦਾ ਹਾਂ! ਤੁਹਾਡੇ ਖੇਤੀ ਸਹਾਇਕ ਵਜੋਂ, ਮੈਂ ਡੈਸ਼ਬੋਰਡ ਤੋਂ ਸਾਡੀ ਮਿੱਟੀ ਦੀ ਸਿਹਤ ਅਤੇ ਸਮਾਰਟ ਏਆਈ ਟੂਲਸ ਦੀ ਜਾਂਚ ਕਰਨ ਦੀ ਸਿਫਾਰਸ਼ ਕਰਦਾ ਹਾਂ। 🌿";
    }
    if (lang === "Tamil (தமிழ்)") {
        if (isCrop) return "எங்கள் AI பயிர் பரிந்துரை கருவியைப் பயன்படுத்த நான் பரிந்துரைக்கிறேன்! இது உங்கள் மண்ணின் ஊட்டச்சத்துக்களை (NPK) ஆராய்கிறது. 🌾";
        if (isDisease) return "பயிர் நோய்களைக் கண்டறிய எங்கள் AI இலை ஸ்கேனர் கருவியைப் பயன்படுத்தலாம். பாதிக்கப்பட்ட இலையின் புகைப்படத்தை எடுக்கவும்! 🔍";
        if (isWeather) return "உங்களுடைய வானிலை முன்னறிவிப்பு 32°C மற்றும் வெயில் காலமாகக் காட்டுகிறது. ☀️";
        if (isFertilizer) return "எங்கள் உரப் பரிந்துரை கருவி, உங்கள் பயிரின் தேவைக்கேற்ப உரம் எவ்வளவு போட வேண்டும் என்று வழிகாட்டுகிறது. 🧪";
        if (isIrrigation) return "தண்ணீரைச் சேமிக்க சொட்டு நீர் அல்லது தெளிப்பு நீர் பாசனத்தைப் பயன்படுத்த பரிந்துரைக்கப்படுகிறது. 💧";
        if (isScheme) return "வழிகாட்டித் திட்டங்கள் தாவலைச் சரிபார்த்து அரசு மானியங்களுக்கு விண்ணப்பிக்கலாம்! 🏛️";
        if (isHelp) return "நான் உங்களுக்கு உதவ இருக்கிறேன்! பயிர் பரிந்துரை, இலை ஸ்கேன், வானிலை, மற்றும் திட்டங்கள் பற்றி கேட்கலாம். 😊";
        return "விவசாய உதவியாளராக, உங்கள் மண்ணின் ஆரோக்கியம் மற்றும் AI கருவிகளைச் சரிபார்க்க பரிந்துரைக்கிறேன். 🌿";
    }
    if (lang === "Telugu (తెలుగు)") {
        if (isCrop) return "మా AI పంట సిఫార్సు సాధనాన్ని ఉపయోగించమని నేను సిఫార్సు చేస్తున్నాను! ఇది మీ నేలలోని NPK విలువలను విశ్లేషిస్తుంది. 🌾";
        if (isDisease) return "పంట తెగుళ్లను గుర్తించడానికి మీరు మా AI ఆకు స్కానర్ సాధనాన్ని ఉపయోగించవచ్చు. ప్రభావిత ఆకు ఫోటో తీయండి! 🔍";
        if (isWeather) return "మీ ప్రాంతంలో ప్రస్తుత వాతావరణం 32°C మరియు ఎండగా ఉంటుంది. ☀️";
        if (isFertilizer) return "మా ఎరువుల సిఫార్సు సాధనం మీ పంటకు ఎంత ఎరువులు వేయాలో ఖచ్చితంగా తెలియజేస్తుంది. 🧪";
        if (isIrrigation) return "నీటిని పొదుపు చేయడానికి డ్రిప్ లేదా స్ప్రింక్లర్ సాగు పద్ధతిని ఉపయోగించండి. 💧";
        if (isScheme) return "మీరు ప్రభుత్వ సబ్సిడీల కొరకు అర్హులు కావచ్చు. ప్రభుత్వ పథకాల ట్యాబ్ చూడండి! 🏛️";
        if (isHelp) return "నేను మీకు సహాయం చేయడానికి ఇక్కడ ఉన్నాను! పంటల సిఫార్సు, తెగుళ్ల గుర్తింపు, వాతావరణం, ఎరువులు, పథకాల గురించి అడగండి. 😊";
        return "వ్యవసాయ సహాయకుడిగా, మీ నేల ఆరోగ్యం మరియు AI సాధనాలను తనిఖీ చేయమని నేను సిఫార్సు చేస్తున్నాను. 🌿";
    }
    if (isCrop) return "I recommend using our AI Crop Recommendation tool! It analyzes your soil's Nitrogen (N), Phosphorus (P), Potassium (K), and pH levels to suggest the perfect harvest. 🌾";
    if (isDisease) return "You can use our AI Leaf Scanner tool to identify crop diseases instantly. Simply take a photo of the affected leaf, and the AI will analyze it! 🔍";
    if (isWeather) return "The weather forecast in Ludhiana shows 32°C and sunny. It is a great time for irrigation or sowing depending on your crop lifecycle! ☀️";
    if (isFertilizer) return "Our Fertilizer Recommendation tool guides you on exactly how much urea, phosphate, or potash to apply based on your crop target. 🧪";
    if (isIrrigation) return "Using Drip or Sprinkler irrigation is highly recommended to save water and maintain optimal soil moisture. 💧";
    if (isScheme) return "You might be eligible for PM-Kisan or solar pump subsidies under the PM-KUSUM scheme. Check the Welfare Schemes tab for direct links! 🏛️";
    if (isHelp) return "I am here to assist you! You can ask me about Crop Recommendation, Leaf Scanning, Weather forecasts, Fertilizer guides, or Government Schemes. 😊";
    return "I understand! As your farming assistant, I recommend checking our Soil Health and Smart AI tools from the dashboard. What specific farming guide do you need? 🌿";
}

// Mic bottom sheet simulations
window.toggleVoiceSheet = function(show) {
    const sheet = document.getElementById("voice-assistant-sheet");
    const lang = currentState.currentLanguage;
    if (show) {
        document.getElementById("voice-title-label").textContent = getVoiceTitle(lang);
        document.getElementById("voice-subtitle-label").textContent = getVoiceSubtitle(lang);
        document.getElementById("voice-sample-btn").textContent = `"${getVoiceQuestion(lang)}"`;
        
        sheet.classList.remove("hidden");
        setTimeout(() => sheet.classList.add("active"), 10);
    } else {
        sheet.classList.remove("active");
        setTimeout(() => sheet.classList.add("hidden"), 300);
    }
};

function getVoiceTitle(lang) {
    if (lang === "Hindi (हिन्दी)") return "एग्रीबॉट सुन रहा है...";
    if (lang === "Punjabi (ਪੰਜਾਬੀ)") return "ਐਗਰੀਬੋਟ ਸੁਣ ਰਿਹਾ ਹੈ...";
    if (lang === "Marathi (मराठी)") return "एग्रीबॉट ऐकत आहे...";
    if (lang === "Gujarati (ગુજરાતી)") return "એગ્રીબોટ સાંભળી રહ્યું છે...";
    if (lang === "Kannada (ಕನ್ನಡ)") return "ಅಗ್ರಿಬಾಟ್ ಆಲಿಸುತ್ತಿದೆ...";
    if (lang === "Telugu (తెలుగు)") return "అగ్రిబాట్ వింటోంది...";
    return "AgriBot is listening...";
}

function getVoiceSubtitle(lang) {
    if (lang === "Hindi (हिन्दी)") return "अपनी खेती की समस्या बोलें";
    if (lang === "Punjabi (ਪੰਜਾਬੀ)") return "ਆਪਣੀ ਖੇਤੀ ਦੀ ਸਮੱਸਿਆ ਬੋਲੋ";
    if (lang === "Marathi (मराठी)") return "तुमची शेतीची समस्या सांगा";
    if (lang === "Gujarati (ગુજરાતી)") return "તમારી ખેતીની સમસ્યા બોલો";
    if (lang === "Kannada (ಕನ್ನಡ)") return "ನಿಮ್ಮ ಕೃಷಿ ಸಮಸ್ಯೆಯನ್ನು ತಿಳಿಸಿ";
    if (lang === "Telugu (తెలుగు)") return "మీ వ్యవసాయ సమస్యను చెప్పండి";
    return "Speak your farming concern";
}

function getVoiceQuestion(lang) {
    if (lang === "Hindi (हिन्दी)") return "गेहूं के लिए सबसे अच्छा उर्वरक कौन सा है?";
    if (lang === "Punjabi (ਪੰਜਾਬੀ)") return "ਕਣਕ ਲਈ ਸਭ ਤੋਂ ਵਧੀਆ ਖਾਦ ਕਿਹੜੀ ਹੈ?";
    if (lang === "Marathi (मराठी)") return "गव्हासाठी कोणते खत सर्वोत्तम आहे?";
    if (lang === "Gujarati (ગુજરાતી)") return "ઘઉં માટે કયું ખાતર શ્રેષ્ઠ છે?";
    if (lang === "Kannada (ಕನ್ನಡ)") return "ಗೋಧಿಗೆ ಅತ್ಯುತ್ತม ಗೊಬ್ಬರ ಯಾವುದು?";
    if (lang === "Telugu (తెలుగు)") return "గోధుమలకు ఏ ఎరువులు ఉత్తమం?";
    return "What is the best fertilizer for wheat?";
}

window.triggerVoiceSpeakSample = function() {
    const q = getVoiceQuestion(currentState.currentLanguage);
    toggleVoiceSheet(false);
    appendChatBubble(q, true);
    triggerChatBotResponse(q);
};
