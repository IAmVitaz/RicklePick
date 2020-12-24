package com.vitaz.ricklepick.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Episode
import com.vitaz.ricklepick.model.EpisodesService
import kotlinx.coroutines.*

class EpisodeDetailsViewModel: ViewModel() {

    val episodeService = EpisodesService.getSpecificEpisode()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    var episodeId = 1

    val episode = MutableLiveData<Episode>()
    val episodeLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun showEpisodeDetails() {
        getEpisodeDetails(episodeId)
    }

    private fun getEpisodeDetails(episodeId: Int) {
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


    private fun onError(message: String) {
        episodeLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}