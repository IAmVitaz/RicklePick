package com.vitaz.ricklepick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.model.CharacterData
import com.vitaz.ricklepick.model.CharacterService
import com.vitaz.ricklepick.model.Info
import kotlinx.coroutines.*

class CharactersViewModel: ViewModel() {

    val characterService = CharacterService.getAllCharacters()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }


    val charactersData = MutableLiveData<CharacterData>()
    val apiCallInfo = MutableLiveData<Info>()
    val characters = MutableLiveData<List<Character>>()
    val characterLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    var charactersList = mutableListOf<Character>()
    var currentPage = 1
    var totalPages = 1

    fun downloadNextPageOfCharacters() {
        fetchCharacters(currentPage)

    }

    private fun fetchCharacters(page: Int) {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = characterService.getAllCharacters(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    charactersData.value = response.body()
                    apiCallInfo.value = charactersData.value!!.info
                    totalPages = apiCallInfo.value!!.pages
                    charactersList.addAll(charactersData.value!!.results)
                    characters.value = charactersList
                    characterLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
            currentPage++
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