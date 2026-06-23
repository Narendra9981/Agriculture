package com.example.farmers.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class PredictionRequest(
    val n: Int,
    val p: Int,
    val k: Int,
    val ph: Double,
    val temp: Double,
    val humidity: Double,
    val rainfall: Double
)

data class PredictionResponse(
    val prediction: String,
    val confidence: Double? = null
)

interface CropApiService {
    @POST("predict")
    suspend fun predictCrop(@Body request: PredictionRequest): PredictionResponse

    companion object {
        // Placeholder for a real ML Backend URL (e.g. FastAPI/Flask)
        private const val BASE_URL = "https://agribot-ml-api.onrender.com/"

        fun create(): CropApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CropApiService::class.java)
        }
    }
}
