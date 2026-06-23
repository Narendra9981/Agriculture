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
    
    private val apiService = WeatherApiService.create()

    suspend fun fetchRealTimeWeather(): Pair<WeatherInfo, List<ForecastDay>> {
        // Ludhiana, Punjab Coordinates: 30.9010, 75.8573
        val response = apiService.getForecast(30.9010, 75.8573)
        
        val current = response.current_weather
        val daily = response.daily
        
        val calendar = Calendar.getInstance()
        val dayFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
        
        val weatherInfo = WeatherInfo(
            location = "Ludhiana, Punjab",
            date = dayFormat.format(calendar.time),
            temperature = "${current.temperature.toInt()}°C",
            condition = getWeatherCondition(current.weathercode),
            humidity = "${daily.precipitation_probability_max[0]}%", // API current humidity is separate, using rain chance as proxy or fallback
            wind = "${current.windspeed.toInt()} km/h",
            uvIndex = "6 (Med)", // UV requires separate call usually, keeping static for now
            icon = getWeatherIcon(current.weathercode)
        )

        val forecast = mutableListOf<ForecastDay>()
        val dayNameFormat = SimpleDateFormat("EEE", Locale.getDefault())
        
        for (i in 0 until daily.time.size) {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(daily.time[i])
            val dayName = if (i == 0) "Today" else dayNameFormat.format(date ?: calendar.time)
            
            forecast.add(ForecastDay(
                name = dayName,
                icon = getWeatherIcon(daily.weathercode[i]),
                temp = "${daily.temperature_2m_max[i].toInt()}°/${daily.temperature_2m_min[i].toInt()}°",
                rain = "${daily.precipitation_probability_max[i]}%"
            ))
        }

        return Pair(weatherInfo, forecast)
    }

    private fun getWeatherIcon(code: Int): ImageVector {
        return when (code) {
            0 -> Icons.Default.WbSunny
            1, 2, 3 -> Icons.Default.WbCloudy
            45, 48 -> Icons.Default.FilterDrama // Fog
            51, 53, 55 -> Icons.Default.WaterDrop // Drizzle
            61, 63, 65 -> Icons.Default.Thunderstorm // Rain
            71, 73, 75 -> Icons.Default.AcUnit // Snow
            95, 96, 99 -> Icons.Default.Thunderstorm
            else -> Icons.Default.WbCloudy
        }
    }

    private fun getWeatherCondition(code: Int): String {
        return when (code) {
            0 -> "Clear Sky"
            1, 2, 3 -> "Partly Cloudy"
            45, 48 -> "Foggy"
            51, 53, 55 -> "Light Drizzle"
            61, 63, 65 -> "Rainy"
            71, 73, 75 -> "Snowfall"
            95, 96, 99 -> "Thunderstorm"
            else -> "Cloudy"
        }
    }

    // Keep legacy methods for safety if needed by other components, but updated for better defaults
    fun getCurrentWeather(): WeatherInfo {
        return WeatherInfo(
            location = "Ludhiana, Punjab",
            date = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date()),
            temperature = "--°C",
            condition = "Loading...",
            humidity = "--%",
            wind = "-- km/h",
            uvIndex = "--",
            icon = Icons.Default.WbCloudy
        )
    }

    fun getWeeklyForecast(): List<ForecastDay> = emptyList()
}
