// --- WEATHER FORECAST RENDER (Map Module) ---

const forecasts = [
    { day: "Thursday", temp: "32°C / 24°C", icon: "☀️", cond: "Sunny" },
    { day: "Friday", temp: "33°C / 25°C", icon: "☀️", cond: "Sunny" },
    { day: "Saturday", temp: "31°C / 23°C", icon: "⛅", cond: "Partly Cloudy" },
    { day: "Sunday", temp: "29°C / 22°C", icon: "🌧️", cond: "Showers" },
    { day: "Monday", temp: "30°C / 23°C", icon: "⛅", cond: "Partly Cloudy" },
    { day: "Tuesday", temp: "32°C / 24°C", icon: "☀️", cond: "Sunny" },
    { day: "Wednesday", temp: "33°C / 25°C", icon: "☀️", cond: "Sunny" }
];

export function renderWeatherForecast() {
    const container = document.getElementById("weather-forecast-list");
    if (!container) return;
    
    container.innerHTML = forecasts.map(f => `
        <div class="forecast-row">
            <span class="forecast-day">${f.day}</span>
            <div class="forecast-cond-group">
                <span class="forecast-icon">${f.icon}</span>
                <span>${f.cond}</span>
            </div>
            <span class="forecast-temp-val">${f.temp}</span>
        </div>
    `).join('');
}

// Make it accessible for init loads
window.renderWeatherForecast = renderWeatherForecast;
