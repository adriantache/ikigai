package com.adriantache.ikigai.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.adriantache.ikigai.data.model.AnswerRoomEntity

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answers")
    suspend fun getAll(): List<AnswerRoomEntity>

    @Insert
    suspend fun insertAll(vararg answers: AnswerRoomEntity)

    @Delete
    suspend fun delete(answer: AnswerRoomEntity)

    @Query("DELETE FROM answers")
    suspend fun deleteAll()
}
