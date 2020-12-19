package com.vitaz.ricklepick.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersAPI {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") currentPage: Int): Response<CharacterData>

}