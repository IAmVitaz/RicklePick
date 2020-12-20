package com.vitaz.ricklepick.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitaz.ricklepick.model.Episode
import com.vitaz.ricklepick.model.EpisodesData
import com.vitaz.ricklepick.model.EpisodesService
import com.vitaz.ricklepick.model.Info
import kotlinx.coroutines.*

class EpisodesViewModel: ViewModel() {

    val episodesService = EpisodesService.getAllEpisodes()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    val episodesData = MutableLiveData<EpisodesData>()
    val apiCallInfo = MutableLiveData<Info>()
    val episodes = MutableLiveData<List<Episode>>()
    val episodesLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    var episodeList = mutableListOf<Episode>()
    var currentPage = 1
    var totalPages = 1

    fun downloadNextPageOfEpisodes() {
        fetchEpisodes(currentPage)
    }

    private fun fetchEpisodes(page: Int) {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = episodesService.getAllEpisodes(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    episodesData.value = response.body()
                    apiCallInfo.value = episodesData.value!!.info
                    totalPages = apiCallInfo.value!!.pages
                    episodeList.addAll(episodesData.value!!.results)
                    episodes.value = episodeList
                    episodesLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
            currentPage++
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