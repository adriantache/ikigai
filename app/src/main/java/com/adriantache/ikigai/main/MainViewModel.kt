package com.adriantache.ikigai.main

import androidx.lifecycle.*
import com.adriantache.ikigai.data.room.AnswerRepository
import com.adriantache.ikigai.model.AnswerEntity
import com.adriantache.ikigai.model.Category
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AnswerRepository) : ViewModel() {

    private val answers = MutableLiveData<List<AnswerEntity>>()

    var currentCategory: Category = Category.LOVE

    val answersList = answers.map { answers ->
        answers.joinToString("\n") { it.answer }
    }

    fun refreshAnswers() {
        viewModelScope.launch {
            answers.value = repository.getAnswers()
        }
    }

    fun addAnswer(input: String) = viewModelScope.launch {
        val answer = AnswerEntity(currentCategory, input)
        repository.insert(answer)
        refreshAnswers()
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
        refreshAnswers()
    }
}

class WordViewModelFactory(private val repository: AnswerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
