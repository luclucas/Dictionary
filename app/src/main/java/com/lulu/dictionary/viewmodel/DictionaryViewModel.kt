package com.lulu.dictionary.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lulu.dictionary.model.WordItem
import com.lulu.dictionary.repository.DictionaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DictionaryViewModel(private val repository: DictionaryRepository): ViewModel() {

    val wordLiveData = MutableLiveData<List<WordItem>>()
    val errorMessage = MutableLiveData<String>("")
    val words : MutableState<List<WordItem>> = mutableStateOf(emptyList())
    fun getWord(word: String){

        viewModelScope.launch (Dispatchers.IO) {


            val request = repository.getWord(word)

            request.enqueue(object : Callback<List<WordItem>> {

                override fun onResponse(
                    call: Call<List<WordItem>>,
                    response: Response<List<WordItem>>
                ) {
                    Log.d("lulutag", "response: ${response.body()}")
                    if (response.isSuccessful) {

                        wordLiveData.postValue(response.body())
                        words.value = response.body()!!
                    } else {
                    }
                }


                override fun onFailure(call: Call<List<WordItem>>, t: Throwable) {
                    Log.d("lulutag", "erro: ${t.message}")
                }

            })
        }
    }

}