package com.vitaz.ricklepick.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterService {

    private val BASE_URL = "https://rickandmortyapi.com/api/"

    fun getAllCharacters(): CharactersAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersAPI::class.java)
    }

}