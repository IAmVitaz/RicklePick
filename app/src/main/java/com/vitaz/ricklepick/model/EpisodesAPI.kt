package com.vitaz.ricklepick.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesAPI {

    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") currentPage: Int): Response<EpisodesData>

    @GET("episode/{episodeId}")
    suspend fun getSpecificEpisode(@Path("episodeId") episodeId: Int): Response<Episode>

}