package com.adriantache.ikigai

import android.app.Application
import com.adriantache.ikigai.data.room.AnswerDatabase
import com.adriantache.ikigai.data.room.AnswerRepository

class IkigaiApplication : Application() {
    private val database by lazy { AnswerDatabase.getDatabase(this) }
    val repository by lazy { AnswerRepository(database.answerDao()) }
}
