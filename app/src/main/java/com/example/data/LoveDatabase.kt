package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteEntity::class,
        CustomNoteEntity::class,
        CoupleMilestoneEntity::class,
        CoupleQuestionAnswerEntity::class,
        LoveChatMessageEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class LoveDatabase : RoomDatabase() {
    abstract fun loveDao(): LoveDao

    companion object {
        @Volatile
        private var INSTANCE: LoveDatabase? = null

        fun getDatabase(context: Context): LoveDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LoveDatabase::class.java,
                    "love_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
