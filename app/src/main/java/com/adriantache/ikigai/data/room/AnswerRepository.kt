package com.adriantache.ikigai.data.room

import androidx.annotation.WorkerThread
import com.adriantache.ikigai.data.model.AnswerRoomEntity
import com.adriantache.ikigai.model.AnswerEntity

class AnswerRepository(private val answerDao: AnswerDao) {

    suspend fun getAnswers(): List<AnswerEntity> {
        return answerDao.getAll().map { it.toEntity() }
    }

    @WorkerThread
    suspend fun insert(answer: AnswerEntity) {
        answerDao.insertAll(AnswerRoomEntity.fromEntity(answer))
    }
}
