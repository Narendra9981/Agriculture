package com.example.farmers.data

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)

    suspend fun loginWithGoogle(): Boolean {
        try {
            // 1. Configure Google ID Option
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false) // Show all accounts
                .setServerClientId("971960077402-uo3013lpb6966c80pa9ooprhf.apps.googleusercontent.com") // From google-services.json
                .setAutoSelectEnabled(false)
                .build()

            // 2. Create Credential Request
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // 3. Launch the native account chooser
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            return handleSignIn(result)
        } catch (e: GetCredentialException) {
            Log.e("AuthRepository", "Credential Manager error: ${e.message}")
            return false
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login exception: ${e.message}")
            return false
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential
        
        if (credential is GoogleIdTokenCredential) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            
            // 4. Authenticate with Firebase
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()
            val user = authResult.user
            
            if (user != null) {
                // 5. Store user data in Firestore
                val profileData = mapOf(
                    "uid" to user.uid,
                    "name" to (user.displayName ?: "Farmer"),
                    "email" to (user.email ?: ""),
                    "photoUrl" to (user.photoUrl?.toString() ?: ""),
                    "lastLogin" to System.currentTimeMillis()
                )
                FirebaseManager.saveUserProfile(user.uid, profileData)
                return true
            }
        }
        return false
    }

    suspend fun logout() {
        auth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}
