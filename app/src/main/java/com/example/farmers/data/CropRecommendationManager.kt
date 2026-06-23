package com.example.farmers.data

import kotlin.math.abs
import android.util.Log

data class RecommendedCrop(
    val name: String,
    val score: Double,
    val yield: String,
    val water: String,
    val season: String,
    val reason: String
)

object CropRecommendationManager {

    private val apiService = CropApiService.create()

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
        CropIdeal("Rice", 80, 40, 40, 6.0, 27, 80, 200, "3.5 t/ha", "High", "Kharif"),
        CropIdeal("Wheat", 50, 40, 30, 6.5, 20, 40, 50, "4.5 t/ha", "Medium", "Rabi"),
        CropIdeal("Cotton", 70, 40, 60, 7.0, 25, 30, 70, "2.8 t/ha", "Low", "Kharif"),
        CropIdeal("Sugarcane", 90, 50, 50, 6.5, 28, 60, 150, "70 t/ha", "Very High", "Year-round"),
        CropIdeal("Maize", 60, 45, 35, 6.2, 24, 65, 80, "5.0 t/ha", "Moderate", "Kharif")
    )

    suspend fun fetchRealTimePrediction(
        nStr: String, pStr: String, kStr: String,
        phStr: String, tempStr: String, humStr: String, rainStr: String
    ): RecommendedCrop {
        val n = nStr.toIntOrNull() ?: 50
        val p = pStr.toIntOrNull() ?: 40
        val k = kStr.toIntOrNull() ?: 40
        val ph = phStr.toDoubleOrNull() ?: 6.5
        val temp = tempStr.toDoubleOrNull() ?: 25.0
        val hum = humStr.toDoubleOrNull() ?: 60.0
        val rain = rainStr.toDoubleOrNull() ?: 100.0

        return try {
            // 1. Call Real-Time ML API
            val response = apiService.predictCrop(
                PredictionRequest(n, p, k, ph, temp, hum, rain)
            )
            
            // 2. Map Prediction to UI Model
            val cropName = response.prediction
            val confidence = response.confidence ?: 98.0
            
            // Find metadata from local database or use defaults
            val metadata = cropDatabase.find { it.name.contains(cropName, ignoreCase = true) }
            
            RecommendedCrop(
                name = cropName,
                score = confidence,
                yield = metadata?.yield ?: "TBD",
                water = metadata?.water ?: "Moderate",
                season = metadata?.season ?: "Varies",
                reason = "Real-Time AI Analysis: The ML model identified ${cropName} as the most optimal crop for your current NPK levels and environment (${temp}°C, ${hum}% humidity)."
            )
        } catch (e: Exception) {
            Log.e("CropManager", "Real-time API failed, using local model: ${e.message}")
            // Fallback to local model if API is down
            recommendCropLocal(nStr, pStr, kStr, phStr, tempStr, humStr, rainStr)
        }
    }

    private fun recommendCropLocal(
        nStr: String, pStr: String, kStr: String,
        phStr: String, tempStr: String, humStr: String, rainStr: String
    ): RecommendedCrop {
        val n = nStr.toIntOrNull() ?: 50
        val p = pStr.toIntOrNull() ?: 40
        val k = kStr.toIntOrNull() ?: 40
        val ph = phStr.toDoubleOrNull() ?: 6.5
        val temp = tempStr.toDoubleOrNull() ?: 25.0
        val hum = humStr.toDoubleOrNull() ?: 60.0
        val rain = rainStr.toDoubleOrNull() ?: 100.0

        var bestMatch = cropDatabase[1] // Default to Wheat
        var lowestError = Double.MAX_VALUE

        for (crop in cropDatabase) {
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
        
        return RecommendedCrop(
            name = bestMatch.name,
            score = Math.round(suitability * 10.0) / 10.0,
            yield = bestMatch.yield,
            water = bestMatch.water,
            season = bestMatch.season,
            reason = "Local AI Analysis: Your soil pH (${ph}) and nutrients show strongest compatibility with ${bestMatch.name}."
        )
    }

    // Keep legacy for backward compatibility during migration
    fun recommendCrop(n: String, p: String, k: String, ph: String, t: String, h: String, r: String): RecommendedCrop {
        return recommendCropLocal(n, p, k, ph, t, h, r)
    }
}
