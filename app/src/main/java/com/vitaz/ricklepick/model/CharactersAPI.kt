package com.vitaz.ricklepick.model

import retrofit2.Response
import retrofit2.http.GET

interface CharactersAPI {

    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterData>

}