package com.adriantache.ikigai.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adriantache.ikigai.data.model.AnswerRoomEntity

@Database(entities = [AnswerRoomEntity::class], version = 1, exportSchema = false)
abstract class AnswerDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDao

    companion object {
        @Volatile
        private var INSTANCE: AnswerDatabase? = null

        fun getDatabase(context: Context): AnswerDatabase {
            return INSTANCE ?: getInstance(context)
        }

        private fun getInstance(context: Context): AnswerDatabase {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnswerDatabase::class.java,
                    "answers_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


