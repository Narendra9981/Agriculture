package com.example.farmers.data

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

object AuthManager {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    // --- MOCK USER SESSION STATE ---
    private var mockUserEmail: String? = null
    private var mockUserName: String? = null
    private var mockUserUid: String? = null

    fun loginMockUser(email: String) {
        mockUserEmail = email
        mockUserName = if (email == "kanamalanarendra1162.sse@saveeth.com") "Narendra" else "Arjun Singh"
        mockUserUid = "google_mock_" + email.replace("[^a-zA-Z0-9]".toRegex(), "_")
        
        // Sync profile data to Firebase Realtime Database
        val profile = mapOf(
            "name" to mockUserName!!,
            "email" to mockUserEmail!!,
            "state" to "Ludhiana, Punjab",
            "location" to "Ludhiana, Punjab",
            "mobile" to if (email == "kanamalanarendra1162.sse@saveeth.com") "+91 98765 43210" else "+91 99887 76655",
            "farmSize" to if (email == "kanamalanarendra1162.sse@saveeth.com") "7.2" else "5.5",
            "soilType" to if (email == "kanamalanarendra1162.sse@saveeth.com") "Alluvial Soil" else "Clay Loam",
            "crops" to if (email == "kanamalanarendra1162.sse@saveeth.com") "Rice, Sugarcane" else "Wheat, Rice"
        )
        FirebaseManager.saveUserProfile(mockUserUid!!, profile)
    }

    fun getDisplayName(): String {
        return mockUserName ?: auth.currentUser?.displayName ?: "Farmer"
    }

    fun getEmail(): String {
        return mockUserEmail ?: auth.currentUser?.email ?: ""
    }

    fun getUid(): String {
        return mockUserUid ?: auth.currentUser?.uid ?: ""
    }

    // --- EMAIL AUTHENTICATION ---

    fun signUpWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun loginWithEmail(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    // --- PHONE AUTHENTICATION ---

    private var verificationId: String? = null

    fun sendOtp(phoneNumber: String, activity: Activity, onCodeSent: () -> Unit, onError: (String) -> Unit) {
        val callbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Auto-retrieval or instant verification
                signInWithPhoneCredential(credential) { success, msg ->
                    if (!success) onError(msg ?: "Verification failed")
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onError(e.message ?: "Verification failed")
            }

            override fun onCodeSent(id: String, token: ForceResendingToken) {
                verificationId = id
                onCodeSent()
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(code: String, onResult: (Boolean, String?) -> Unit) {
        val id = verificationId
        if (id == null) {
            onResult(false, "Verification ID is missing")
            return
        }
        val credential = PhoneAuthProvider.getCredential(id, code)
        signInWithPhoneCredential(credential, onResult)
    }

    private fun signInWithPhoneCredential(credential: PhoneAuthCredential, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun signInWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout() {
        auth.signOut()
        mockUserEmail = null
        mockUserName = null
        mockUserUid = null
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null || mockUserUid != null
    }
}
