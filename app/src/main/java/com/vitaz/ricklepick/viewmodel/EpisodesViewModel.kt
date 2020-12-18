package com.vitaz.ricklepick.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Episode
import com.vitaz.ricklepick.model.EpisodesData
import com.vitaz.ricklepick.model.EpisodesService
import kotlinx.coroutines.*

class EpisodesViewModel: ViewModel() {

    val episodesService = EpisodesService.getAllEpisodes()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    val episodesData = MutableLiveData<EpisodesData>()
    val episodes = MutableLiveData<List<Episode>>()
    val episodesLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchEpisodes()
    }

    private fun fetchEpisodes() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = episodesService.getAllEpisodes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    episodesData.value = response.body()
                    episodes.value = episodesData.value!!.results
                    episodesLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        episodesLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}