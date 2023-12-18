package com.lulu.dictionary.service

import com.lulu.dictionary.model.WordItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryService {

    @GET("{word}")
    fun getWord(@Path("word") word: String): Call<List<WordItem>>
}