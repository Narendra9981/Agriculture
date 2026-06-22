// AgriBot Web Companion Portal Main Entry Point
import { translations, t } from "./translations.js";

// Global Application State
export let currentState = {
    currentScreen: "screen-splash",
    currentLanguage: "English",
    theme: "green",
    user: null,
    profile: {
        name: "Arjun Singh",
        location: "Ludhiana, Punjab",
        farmSize: "5.5",
        soilType: "Clay Loam",
        crops: "Wheat, Rice"
    },
    selectedScheme: null
};

// Initialize App
const initApp = async () => {
    // Dynamically import feature modules first to ensure app.js is fully evaluated
    await Promise.all([
        import("./auth.js").catch(err => console.error("auth.js failed to load", err)),
        import("./chat.js").catch(err => console.error("chat.js failed to load", err)),
        import("./feed.js").catch(err => console.error("feed.js failed to load", err)),
        import("./map.js").catch(err => console.error("map.js failed to load", err)),
        import("./profile.js").catch(err => console.error("profile.js failed to load", err)),
        import("./share.js").catch(err => console.error("share.js failed to load", err))
    ]);

    // Dynamic Time in Status Bar
    updateStatusBarTime();
    setInterval(updateStatusBarTime, 60000);

    // Initial Splash timeout
    setTimeout(() => {
        // If user session exists, redirect to dashboard.
        // Otherwise, redirect to welcome screen.
        if (!currentState.user) {
            navigateTo("screen-welcome");
        } else {
            navigateTo("screen-dashboard");
        }
    }, 1500);

    // Initial translations & components rendering
    compileTranslations();
    if (window.renderWeatherForecast) window.renderWeatherForecast();
    if (window.renderSchemesList) window.renderSchemesList();
    if (window.initChatGreeting) window.initChatGreeting();
};

if (document.readyState === "loading") {
    window.addEventListener("DOMContentLoaded", initApp);
} else {
    initApp();
}

// Update Simulated Time
function updateStatusBarTime() {
    const timeSpan = document.getElementById("status-time");
    if (timeSpan) {
        const now = new Date();
        let hours = now.getHours();
        let minutes = now.getMinutes();
        if (hours < 10) hours = "0" + hours;
        if (minutes < 10) minutes = "0" + minutes;
        timeSpan.textContent = `${hours}:${minutes}`;
    }
}

// Navigation / Router Orchestrator
export function navigateTo(screenId) {
    // Hide all screens
    const screens = document.querySelectorAll(".app-screen");
    screens.forEach(s => s.classList.remove("active"));

    // Show destination screen
    const destScreen = document.getElementById(screenId);
    if (destScreen) {
        destScreen.classList.add("active");
        currentState.currentScreen = screenId;
    }

    // Update active sidebar tab
    updateActiveSidebarTab(screenId);

    // Close mobile drawer if open
    const sidebar = document.getElementById("app-sidebar");
    if (sidebar) sidebar.classList.remove("open");

    // Scroll to top of viewport
    const viewport = document.querySelector(".portal-main");
    if (viewport) viewport.scrollTop = 0;
}

// Attach navigateTo to window so it is accessible from inline HTML event handlers
window.navigateTo = navigateTo;

// Toggle mobile sidebar drawer
window.toggleMobileSidebar = function() {
    const sidebar = document.getElementById("app-sidebar");
    if (sidebar) sidebar.classList.toggle("open");
};

function updateActiveSidebarTab(screenId) {
    const tabs = document.querySelectorAll(".sidebar-item");
    tabs.forEach(t => t.classList.remove("active"));

    if (screenId === "screen-dashboard") document.getElementById("tab-home")?.classList.add("active");
    if (screenId === "screen-chat") document.getElementById("tab-chat")?.classList.add("active");
    if (screenId === "screen-scan") document.getElementById("tab-scan")?.classList.add("active");
    if (screenId === "screen-weather") document.getElementById("tab-weather")?.classList.add("active");
    if (screenId === "screen-fertilizer") document.getElementById("tab-fertilizer")?.classList.add("active");
    if (screenId === "screen-fertilizer-pred") document.getElementById("tab-fertilizer-pred")?.classList.add("active");
    if (screenId === "screen-schemes" || screenId === "screen-scheme-detail") document.getElementById("tab-schemes")?.classList.add("active");
    if (screenId === "screen-profile" || screenId === "screen-edit-profile") document.getElementById("tab-profile")?.classList.add("active");
    if (screenId === "screen-settings") document.getElementById("tab-settings")?.classList.add("active");
}

// Global localization updates
window.changeLanguage = function(lang) {
    currentState.currentLanguage = lang;
    
    // Update dropdown select elements
    const selectSettings = document.getElementById("settings-lang-select");
    if (selectSettings) selectSettings.value = lang;
    const selectHeader = document.getElementById("header-lang-select");
    if (selectHeader) selectHeader.value = lang;

    // Compile translations
    compileTranslations();
    
    // Re-initialize Chat screen
    if (window.initChatGreeting) window.initChatGreeting();
    
    // Re-render components
    if (window.renderSchemesList) window.renderSchemesList();
};

export function compileTranslations() {
    const elements = document.querySelectorAll("[data-t]");
    elements.forEach(el => {
        const key = el.getAttribute("data-t");
        const val = t(key, currentState.currentLanguage);
        if (el.tagName === "INPUT" && el.placeholder) {
            el.placeholder = val;
        } else {
            el.innerHTML = val;
        }
    });

    // Update greeting
    const greet = document.getElementById("user-greeting");
    if (greet) {
        const welcomeText = t("welcome", currentState.currentLanguage);
        const nameOnly = currentState.profile.name || "Farmer";
        const locationStr = currentState.profile.location || currentState.profile.state || "Ludhiana, Punjab";
        const loc = locationStr.split(",")[0];
        // Replace name dynamically
        if (currentState.currentLanguage === "English") {
            greet.textContent = `Welcome, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Hindi (हिन्दी)") {
            greet.textContent = `स्वागत है, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Punjabi (ਪੰਜਾਬੀ)") {
            greet.textContent = `ਜੀ ਆਇਆਂ ਨੂੰ, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Marathi (मराठी)") {
            greet.textContent = `स्वागत आहे, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Gujarati (ગુજરાતી)") {
            greet.textContent = `સ્વાગત છે, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Kannada (ಕನ್ನಡ)") {
            greet.textContent = `ಸ್ವಾಗತ, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Telugu (తెలుగు)") {
            greet.textContent = `స్వాగతం, ${nameOnly} • ${loc}`;
        } else if (currentState.currentLanguage === "Tamil (தமிழ்)") {
            greet.textContent = `வரவேற்கிறோம், ${nameOnly} • ${loc}`;
        }
    }
}

window.compileTranslations = compileTranslations;

// Global visual theme configurations
window.changeTheme = function(themeName) {
    currentState.theme = themeName;
    document.body.className = "";
    document.body.classList.add(`theme-${themeName}`);

    // Update theme selectors active states
    const btns = document.querySelectorAll(".theme-btn");
    btns.forEach(b => b.classList.remove("active"));
    const activeBtns = document.querySelectorAll(`.theme-btn.theme-${themeName}`);
    activeBtns.forEach(btn => btn.classList.add("active"));
};

// Expose updateUIProfileValues proxy
export function updateUIProfileValues() {
    if (window.updateUIProfileValues) {
        window.updateUIProfileValues();
    }
}

// Feature modules are dynamically imported inside DOMContentLoaded handler.

