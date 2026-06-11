package com.example.farmers.data

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

object FirebaseManager {
    private const val DATABASE_URL = "https://farmers-acc82-default-rtdb.firebaseio.com/"
    
    private var database: FirebaseDatabase? = null

    /**
     * Initializes Firebase manually if google-services.json is missing.
     * This is useful for prototypes.
     */
    fun init(context: Context) {
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                val options = FirebaseOptions.Builder()
                    .setApplicationId("com.example.farmers") // Match your package name
                    .setDatabaseUrl(DATABASE_URL)
                    .setProjectId("farmers-acc82")
                    .build()
                FirebaseApp.initializeApp(context, options)
                Log.d("FirebaseManager", "Firebase manually initialized")
            }
            database = FirebaseDatabase.getInstance(DATABASE_URL)
            Log.d("FirebaseManager", "Database instance acquired")
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Critical error during initialization: ${e.message}")
        }
    }

    fun saveUserProfile(userId: String, profileData: Map<String, Any>) {
        try {
            if (database == null) {
                Log.e("FirebaseManager", "Cannot save profile: Database not initialized")
                return
            }
            database?.getReference("users")?.child(userId)?.updateChildren(profileData)
                ?.addOnFailureListener { Log.e("FirebaseManager", "Sync failure: ${it.message}") }
            Log.d("FirebaseManager", "Profile push attempted for $userId")
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Exception during save profile: ${e.message}")
        }
    }

    fun getUserProfile(userId: String, onResult: (Map<String, Any>?) -> Unit) {
        try {
            if (database == null) {
                onResult(null)
                return
            }
            database?.getReference("users")?.child(userId)?.get()
                ?.addOnSuccessListener { snapshot ->
                    val value = snapshot.value as? Map<String, Any>
                    onResult(value)
                }
                ?.addOnFailureListener {
                    onResult(null)
                }
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error getting user profile: ${e.message}")
            onResult(null)
        }
    }

    fun saveSoilData(userId: String, soilData: Map<String, Any>) {
        try {
            if (database == null) return
            database?.getReference("soil_data")?.child(userId)?.push()?.setValue(soilData)
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Error saving soil data: ${e.message}")
        }
    }

    fun saveSchemeApplication(userId: String, applicationData: Map<String, Any>) {
        try {
            if (database == null) {
                Log.e("FirebaseManager", "Cannot save application: Database not initialized")
                return
            }
            database?.getReference("applications")?.child(userId)?.push()?.setValue(applicationData)
                ?.addOnFailureListener { Log.e("FirebaseManager", "Application save failure: ${it.message}") }
            Log.d("FirebaseManager", "Application push attempted for $userId")
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Exception during save application: ${e.message}")
        }
    }
}
