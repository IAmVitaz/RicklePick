package com.vitaz.ricklepick.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EpisodesService {

    private val BASE_URL = "https://rickandmortyapi.com/api/"

    fun getAllEpisodes(): EpisodesAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EpisodesAPI::class.java)
    }
}