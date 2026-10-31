package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_messages")
data class FavoriteEntity(
    @PrimaryKey
    val messageId: String,
    val title: String,
    val text: String,
    val signOff: String,
    val emoji: String,
    val categoryName: String,
    val savedAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_love_notes")
data class CustomNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val text: String,
    val author: String,
    val openWhenCondition: String = "Para cualquier momento",
    val paperStyle: String = "pergamino",
    val sealEmoji: String = "💌",
    val isOpened: Boolean = false,
    val dateCreatedMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "couple_milestones")
data class CoupleMilestoneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val dateMillis: Long,
    val emoji: String = "💖",
    val note: String = ""
)

@Entity(tableName = "couple_question_answers")
data class CoupleQuestionAnswerEntity(
    @PrimaryKey
    val questionId: String,
    val questionText: String,
    val category: String,
    val herAnswer: String = "",
    val hisAnswer: String = "",
    val answeredAtTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "couple_chat_messages")
data class LoveChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val senderName: String,
    val isSenderMe: Boolean = true,
    val messageText: String,
    val stickerEmoji: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val hasHeartReaction: Boolean = false
)

