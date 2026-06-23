package com.example.farmers.data

import kotlin.math.abs

data class RecommendedCrop(
    val name: String,
    val score: Double,
    val yield: String,
    val water: String,
    val season: String,
    val reason: String
)

object CropRecommendationManager {

    private data class CropIdeal(
        val name: String,
        val n: Int, val p: Int, val k: Int,
        val ph: Double,
        val temp: Int,
        val hum: Int,
        val rain: Int,
        val yield: String,
        val water: String,
        val season: String
    )

    private val cropDatabase = listOf(
        CropIdeal("Rice (Basmati)", 80, 40, 40, 6.0, 27, 80, 200, "3.5 t/ha", "High", "Kharif"),
        CropIdeal("Wheat (Malwa Shakti)", 50, 40, 30, 6.5, 20, 40, 50, "4.5 t/ha", "Medium", "Rabi"),
        CropIdeal("Cotton (BT)", 70, 40, 60, 7.0, 25, 30, 70, "2.8 t/ha", "Low", "Kharif"),
        CropIdeal("Sugarcane", 90, 50, 50, 6.5, 28, 60, 150, "70 t/ha", "Very High", "Year-round"),
        CropIdeal("Maize", 60, 45, 35, 6.2, 24, 65, 80, "5.0 t/ha", "Moderate", "Kharif")
    )

    fun recommendCrop(
        nStr: String, pStr: String, kStr: String,
        phStr: String, tempStr: String, humStr: String, rainStr: String
    ): RecommendedCrop {
        val n = nStr.toIntOrNull() ?: 50
        val p = pStr.toIntOrNull() ?: 40
        val k = kStr.toIntOrNull() ?: 40
        val ph = phStr.toDoubleOrNull() ?: 6.5
        val temp = tempStr.toIntOrNull() ?: 25
        val hum = humStr.toIntOrNull() ?: 60
        val rain = rainStr.toIntOrNull() ?: 100

        var bestMatch = cropDatabase[1] // Default to Wheat
        var lowestError = Double.MAX_VALUE

        for (crop in cropDatabase) {
            // Normalized Error Calculation (Simplistic AI simulation)
            val error = abs(crop.n - n) / 100.0 +
                        abs(crop.p - p) / 100.0 +
                        abs(crop.k - k) / 100.0 +
                        abs(crop.ph - ph) / 7.0 +
                        abs(crop.temp - temp) / 40.0 +
                        abs(crop.hum - hum) / 100.0 +
                        abs(crop.rain - rain) / 200.0
            
            if (error < lowestError) {
                lowestError = error
                bestMatch = crop
            }
        }

        val suitability = (100.0 - (lowestError * 15)).coerceIn(65.0, 99.5)
        
        val reason = "AI Analysis: Your soil pH (${ph}) and Nutrients (N:$n, P:$p, K:$k) show the strongest compatibility with ${bestMatch.name}. " +
                     "The current environment (Temp:${temp}°C, Humidity:${hum}%) will optimize growth for the ${bestMatch.season} season."

        return RecommendedCrop(
            name = bestMatch.name,
            score = Math.round(suitability * 10.0) / 10.0,
            yield = bestMatch.yield,
            water = bestMatch.water,
            season = bestMatch.season,
            reason = reason
        )
    }
}
