package com.vitaz.ricklepick.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersAPI {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") currentPage: Int): Response<CharacterData>

    @GET("character/{characterId}")
    suspend fun getSpecificCharacter(@Path("characterId") characterId: Int): Response<Character>

}