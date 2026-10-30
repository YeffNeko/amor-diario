package com.example.data

import android.content.Context
import android.util.Log
import com.example.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

private const val TAG = "LoveCloudSync"

class LoveCloudSyncRepository(
    private val db: FirebaseFirestore
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    suspend fun syncProfileToCloud(profile: LoveProfile): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(IllegalStateException("No hay sesión activa"))
        val userId = user.uid

        val profileData = mutableMapOf<String, Any>(
            "email" to (user.email ?: ""),
            "displayName" to (user.displayName ?: ""),
            "girlfriendName" to profile.girlfriendName,
            "girlfriendNickname" to profile.girlfriendNickname,
            "boyfriendName" to profile.boyfriendName,
            "anniversaryTimestamp" to profile.anniversaryTimestamp,
            "updatedAt" to FieldValue.serverTimestamp()
        )

        user.photoUrl?.let {
            profileData["photoUrl"] = it.toString()
        }

        return try {
            val userDocRef = db.collection("users").document(userId)
            val snapshot = userDocRef.get().await()
            if (!snapshot.exists()) {
                profileData["createdAt"] = FieldValue.serverTimestamp()
            }
            userDocRef.set(profileData, SetOptions.merge()).await()
            Log.d(TAG, "Perfil de amor sincronizado en Firestore exitosamente para $userId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error al sincronizar perfil en Firestore", e)
            Result.failure(e)
        }
    }

    suspend fun saveNoteToCloud(note: CustomNoteEntity): Result<Unit> {
        val user = auth.currentUser ?: return Result.failure(IllegalStateException("No hay sesión activa"))
        val userId = user.uid

        val noteData = mapOf(
            "title" to note.title,
            "content" to note.text,
            "category" to note.openWhenCondition,
            "author" to note.author,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp()
        )

        return try {
            val noteId = if (note.id != 0L) note.id.toString() else "note_${System.currentTimeMillis()}"
            db.collection("users")
                .document(userId)
                .collection("notes")
                .document(noteId)
                .set(noteData)
                .await()
            Log.d(TAG, "Nota romántica sincronizada en la nube: $noteId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error al sincronizar nota en la nube", e)
            Result.failure(e)
        }
    }
}
