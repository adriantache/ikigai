package com.adriantache.ikigai.data.room

import androidx.annotation.WorkerThread
import com.adriantache.ikigai.data.model.AnswerRoomEntity
import com.adriantache.ikigai.model.AnswerEntity

class AnswerRepository(private val answerDao: AnswerDao) {

    // TODO: 14/04/2021  migrate this to Flow
    suspend fun getAnswers(): List<AnswerEntity> {
        return answerDao.getAll().map { it.toEntity() }
    }

    @WorkerThread
    suspend fun insert(answer: AnswerEntity) {
        val answers = getAnswers()

        if (answers.find { it.answer == answer.answer } == null) {
            answerDao.insertAll(AnswerRoomEntity.fromEntity(answer))
        }
    }

    suspend fun deleteAll() {
        answerDao.deleteAll()
    }
}
