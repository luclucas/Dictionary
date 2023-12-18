package com.lulu.dictionary.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryInstance {

    companion object {
        private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/"
        fun getDictionaryInstance(): Retrofit{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()
        }
    }
}