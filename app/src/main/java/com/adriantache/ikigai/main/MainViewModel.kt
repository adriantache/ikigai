package com.adriantache.ikigai.main

import androidx.lifecycle.ViewModel
import com.adriantache.ikigai.model.AnswerEntity

class MainViewModel : ViewModel() {
    // TODO: 14/04/2021  move this to database
    var answers = mutableSetOf<AnswerEntity>()

    fun addAnswer(answer: AnswerEntity) {
        answers.add(answer)
    }
}
