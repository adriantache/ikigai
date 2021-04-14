package com.adriantache.ikigai.randomizer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.adriantache.ikigai.data.room.AnswerRepository
import com.adriantache.ikigai.model.Category.*
import kotlinx.coroutines.launch

class RandomizerViewModel(private val repository: AnswerRepository) : ViewModel() {
    val loveChoice = MutableLiveData<String>()
    val goodChoice = MutableLiveData<String>()
    val worldChoice = MutableLiveData<String>()

    fun randomize() = viewModelScope.launch {
        val answers = repository.getAnswers()
        loveChoice.value = answers.filter { it.category == LOVE }.randomOrNull()?.answer.orEmpty()
        goodChoice.value = answers.filter { it.category == GOOD }.randomOrNull()?.answer.orEmpty()
        worldChoice.value = answers.filter { it.category == WORLD }.randomOrNull()?.answer.orEmpty()
    }
}

class RandomizerViewModelFactory(private val repository: AnswerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomizerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RandomizerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
