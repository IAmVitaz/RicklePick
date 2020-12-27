package com.vitaz.ricklepick.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Character
import com.vitaz.ricklepick.model.CharacterService
import com.vitaz.ricklepick.model.Episode
import com.vitaz.ricklepick.model.EpisodesService
import kotlinx.coroutines.*

class EpisodeDetailsViewModel: ViewModel() {

    val episodeService = EpisodesService.getSpecificEpisode()
    val characterService = CharacterService.getAllCharacters()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var episodeId = 1

    val episode = MutableLiveData<Episode>()
    val episodeLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    var totalCharacters = 0
    var loadedCharacters = 0
    val isCharacterLoadingFinished = MutableLiveData<Boolean>()

    val charactersList = mutableListOf<Character>()

    fun showEpisodeDetails() {
        getEpisodeDetails(episodeId)
    }

    fun showCharactersList() {
        getCharacters(episode.value!!.characters)
    }

    private fun getEpisodeDetails(characterId: Int) {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = episodeService.getSpecificEpisode(episodeId)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    episode.value = response.body()
                    episodeLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun getCharacters(characters: List<String>) {
        loading.value = true
        charactersList.clear()
        totalCharacters = characters.size
        loadedCharacters = 0

        for (characterUrl in characters) {
            val characterId = characterUrl.drop(42).toInt()

            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = characterService.getSpecificCharacter(characterId)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        charactersList.add(response.body()!!)
                        loadedCharacters ++
                        checkIfCharacterLoadingComplete()
                        Log.i("INFO", "charactersList size: ${charactersList.size}")
                        episodeLoadError.value = null
                    } else {
                        onError("Error: ${response.message()}")
                    }
                }
            }
        }
        loading.value = false
    }

    private fun checkIfCharacterLoadingComplete() {
        if (totalCharacters == loadedCharacters) isCharacterLoadingFinished.value = true
    }


    private fun onError(message: String) {
        episodeLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}