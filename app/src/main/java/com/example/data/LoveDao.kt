package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LoveDao {

    @Query("SELECT * FROM favorite_messages ORDER BY savedAtTimestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_messages WHERE messageId = :id)")
    fun isFavorite(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_messages WHERE messageId = :id")
    suspend fun deleteFavoriteById(id: String)

    @Query("SELECT * FROM custom_love_notes ORDER BY dateCreatedMillis DESC")
    fun getAllCustomNotes(): Flow<List<CustomNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomNote(note: CustomNoteEntity): Long

    @Query("UPDATE custom_love_notes SET isOpened = 1 WHERE id = :noteId")
    suspend fun markNoteOpened(noteId: Long)

    @Delete
    suspend fun deleteCustomNote(note: CustomNoteEntity)

    // Milestones
    @Query("SELECT * FROM couple_milestones ORDER BY dateMillis ASC")
    fun getAllMilestones(): Flow<List<CoupleMilestoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestone(milestone: CoupleMilestoneEntity)

    @Delete
    suspend fun deleteMilestone(milestone: CoupleMilestoneEntity)

    // Couple Questions Answers
    @Query("SELECT * FROM couple_question_answers ORDER BY answeredAtTimestamp DESC")
    fun getAllQuestionAnswers(): Flow<List<CoupleQuestionAnswerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestionAnswer(answer: CoupleQuestionAnswerEntity)

    @Query("DELETE FROM couple_question_answers WHERE questionId = :questionId")
    suspend fun deleteQuestionAnswer(questionId: String)
}
