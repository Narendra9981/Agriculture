package com.example.farmers.data

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

object FirebaseManager {
    private const val DATABASE_URL = "https://farmers-acc82-default-rtdb.firebaseio.com/"
    
    private var database: FirebaseDatabase? = null
    private var firestore: FirebaseFirestore? = null

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
            firestore = FirebaseFirestore.getInstance()
            Log.d("FirebaseManager", "Database and Firestore instances acquired")
        } catch (e: Exception) {
            Log.e("FirebaseManager", "Critical error during initialization: ${e.message}")
        }
    }

    fun saveUserProfile(userId: String, profileData: Map<String, Any>) {
        try {
            // Save to Realtime Database (Legacy)
            if (database != null) {
                database?.getReference("users")?.child(userId)?.updateChildren(profileData)
                    ?.addOnFailureListener { Log.e("FirebaseManager", "RTDB sync failure: ${it.message}") }
            }
            
            // Save to Firestore (New requirement)
            if (firestore != null) {
                firestore?.collection("users")?.document(userId)
                    ?.set(profileData, SetOptions.merge())
                    ?.addOnSuccessListener { Log.d("FirebaseManager", "Firestore profile saved for $userId") }
                    ?.addOnFailureListener { Log.e("FirebaseManager", "Firestore sync failure: ${it.message}") }
            }

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
