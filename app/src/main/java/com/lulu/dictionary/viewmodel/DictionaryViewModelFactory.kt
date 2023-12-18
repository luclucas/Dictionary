package com.lulu.dictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lulu.dictionary.repository.DictionaryRepository

class DictionaryViewModelFactory(private val repository: DictionaryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DictionaryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(repository) as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL")
    }
}