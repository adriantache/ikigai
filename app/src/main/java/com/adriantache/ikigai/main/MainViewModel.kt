package com.adriantache.ikigai.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.adriantache.ikigai.data.room.AnswerRepository
import com.adriantache.ikigai.model.AnswerEntity
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AnswerRepository) : ViewModel() {

    val answers = MutableLiveData<List<AnswerEntity>>()

    fun getAnswers() {
        viewModelScope.launch {
            answers.value = repository.getAnswers()
        }
    }

    fun addAnswer(answerEntity: AnswerEntity) = viewModelScope.launch {
        repository.insert(answerEntity)
        getAnswers()
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
