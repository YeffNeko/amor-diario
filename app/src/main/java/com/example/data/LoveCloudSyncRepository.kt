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

    fun computeCoupleId(myId: String, partnerId: String): String {
        return if (myId <= partnerId) "${myId}_${partnerId}" else "${partnerId}_${myId}"
    }

    suspend fun sendCoupleMessageToCloud(
        coupleId: String,
        senderId: String,
        senderName: String,
        messageText: String,
        stickerEmoji: String?
    ): Result<String> {
        return try {
            val msgData = hashMapOf<String, Any>(
                "senderId" to senderId,
                "senderName" to senderName,
                "messageText" to messageText,
                "stickerEmoji" to (stickerEmoji ?: ""),
                "hasHeartReaction" to false,
                "timestamp" to FieldValue.serverTimestamp()
            )
            val docRef = db.collection("couples")
                .document(coupleId)
                .collection("messages")
                .add(msgData)
                .await()
            Log.d(TAG, "Mensaje enviado a la nube en pareja $coupleId doc: ${docRef.id}")
            Result.success(docRef.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al enviar mensaje a la nube", e)
            Result.failure(e)
        }
    }

    fun listenToCoupleMessages(
        coupleId: String,
        onMessagesReceived: (List<Map<String, Any>>) -> Unit
    ): com.google.firebase.firestore.ListenerRegistration {
        return db.collection("couples")
            .document(coupleId)
            .collection("messages")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .limit(100)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "Error escuchando mensajes de pareja", error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val messages = snapshot.documents.mapNotNull { doc ->
                        doc.data?.plus("cloudId" to doc.id)
                    }
                    onMessagesReceived(messages)
                }
            }
    }
}
