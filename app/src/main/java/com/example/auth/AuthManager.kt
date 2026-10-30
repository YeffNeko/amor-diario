package com.example.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.R
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

private const val TAG = "AuthManager"

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Success(val user: FirebaseUser) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthManager(
    private val context: Context,
    private val auth: FirebaseAuth = Firebase.auth
) {
    private val credentialManager = CredentialManager.create(context)

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val userFlow: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    suspend fun signInWithGoogle(): Result<FirebaseUser> {
        _uiState.value = AuthUiState.Loading

        val serverClientId = context.getString(R.string.default_web_client_id)
        if (serverClientId.isBlank()) {
            val errorMsg = "Web Client ID no configurado"
            _uiState.value = AuthUiState.Error(errorMsg)
            return Result.failure(IllegalStateException(errorMsg))
        }

        val googleIdOption = com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption.Builder(serverClientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(authCredential).await()
                val user = authResult.user

                if (user != null) {
                    _uiState.value = AuthUiState.Success(user)
                    Result.success(user)
                } else {
                    val msg = "Error al obtener usuario de Firebase"
                    _uiState.value = AuthUiState.Error(msg)
                    Result.failure(Exception(msg))
                }
            } else {
                val msg = "Tipo de credencial no reconocido"
                _uiState.value = AuthUiState.Error(msg)
                Result.failure(Exception(msg))
            }
        } catch (e: GetCredentialCancellationException) {
            Log.d(TAG, "Inicio de sesión cancelado por el usuario")
            _uiState.value = AuthUiState.Idle
            Result.failure(e)
        } catch (e: GoogleIdTokenParsingException) {
            Log.e(TAG, "Error parseando ID Token de Google", e)
            val msg = "Token de Google no válido"
            _uiState.value = AuthUiState.Error(msg)
            Result.failure(e)
        } catch (e: GetCredentialException) {
            Log.e(TAG, "Error de CredentialManager: ${e.message}", e)
            val msg = "Error al solicitar cuenta de Google: ${e.message}"
            _uiState.value = AuthUiState.Error(msg)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Error durante inicio de sesión", e)
            val msg = e.localizedMessage ?: "Fallo inesperado al iniciar sesión"
            _uiState.value = AuthUiState.Error(msg)
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
        _uiState.value = AuthUiState.Idle
    }

    fun clearError() {
        if (_uiState.value is AuthUiState.Error) {
            _uiState.value = AuthUiState.Idle
        }
    }
}
