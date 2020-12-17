package com.vitaz.ricklepick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.model.CharacterData
import com.vitaz.ricklepick.model.CharacterService
import kotlinx.coroutines.*

class CharactersViewModel: ViewModel() {

    val characterService = CharacterService.getAllCharacters()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }


    val charactersData = MutableLiveData<CharacterData>()
    val characters = MutableLiveData<List<Character>>()
    val characterLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO ).launch {
            val response = characterService.getAllCharacters()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    charactersData.value = response.body()
                    characters.value = charactersData.value!!.results
                    Log.i("INFO:", charactersData.value!!.results[1].name.toString())
                    Log.i("INFO:", characters.value!![1].name.toString())
                    characterLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }

    }

    private fun onError(message: String) {
        characterLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}