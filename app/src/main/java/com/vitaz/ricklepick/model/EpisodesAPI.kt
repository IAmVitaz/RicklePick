package com.vitaz.ricklepick.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesAPI {

    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") currentPage: Int): Response<EpisodesData>

}