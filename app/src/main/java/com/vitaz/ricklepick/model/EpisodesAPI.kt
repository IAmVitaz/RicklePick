package com.vitaz.ricklepick.model

import retrofit2.Response
import retrofit2.http.GET

interface EpisodesAPI {

    @GET("episode")
    suspend fun getAllEpisodes(): Response<EpisodesData>

}