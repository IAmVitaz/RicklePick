package com.vitaz.ricklepick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.model.CharacterService
import kotlinx.coroutines.*

class CharacterDetailsViewModel: ViewModel() {

    val characterService = CharacterService.getSpecificCharacter()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var characterId = 1

    val character = MutableLiveData<Character>()
    val characterLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun showCharacterDetails() {
        getCharacterDetails(characterId)

    }

    private fun getCharacterDetails(characterId: Int) {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = characterService.getSpecificCharacter(characterId)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    character.value = response.body()
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