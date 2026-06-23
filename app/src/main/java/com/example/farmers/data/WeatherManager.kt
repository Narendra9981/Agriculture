package com.example.farmers.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.*

data class WeatherInfo(
    val location: String,
    val date: String,
    val temperature: String,
    val condition: String,
    val humidity: String,
    val wind: String,
    val uvIndex: String,
    val icon: ImageVector
)

data class ForecastDay(
    val name: String,
    val icon: ImageVector,
    val temp: String,
    val rain: String
)

object WeatherManager {
    
    fun getCurrentWeather(): WeatherInfo {
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
        
        // Dynamic Condition based on hour
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val condition = when {
            hour in 6..11 -> "Sunny"
            hour in 12..16 -> "Mostly Sunny"
            hour in 17..19 -> "Clear Sky"
            else -> "Cloudy"
        }
        
        val icon = when(condition) {
            "Sunny" -> Icons.Default.WbSunny
            "Mostly Sunny" -> Icons.Default.WbSunny
            "Clear Sky" -> Icons.Default.WbSunny
            else -> Icons.Default.WbCloudy
        }

        return WeatherInfo(
            location = "Ludhiana, Punjab",
            date = dayFormat.format(calendar.time),
            temperature = "32°C", // Simulated dynamic baseline
            condition = condition,
            humidity = "48%",
            wind = "14 km/h",
            uvIndex = "7 (High)",
            icon = icon
        )
    }

    fun getWeeklyForecast(): List<ForecastDay> {
        val forecast = mutableListOf<ForecastDay>()
        val calendar = Calendar.getInstance()
        val dayNameFormat = SimpleDateFormat("EEE", Locale.getDefault())

        for (i in 0 until 7) {
            val dayName = dayNameFormat.format(calendar.time)
            
            // Varied conditions for the week
            val (icon, temp, rain) = when (i % 3) {
                0 -> Triple(Icons.Default.WbSunny, "34°/24°", "2%")
                1 -> Triple(Icons.Default.WbCloudy, "31°/22°", "15%")
                else -> Triple(Icons.Default.Thunderstorm, "28°/20°", "75%")
            }

            forecast.add(ForecastDay(dayName, icon, temp, rain))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return forecast
    }
}
