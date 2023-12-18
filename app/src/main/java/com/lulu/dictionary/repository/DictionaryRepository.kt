package com.lulu.dictionary.repository

import com.lulu.dictionary.service.DictionaryService

class DictionaryRepository(private val service: DictionaryService) {

    fun getWord(word: String) = service.getWord(word)
}