package com.vitaz.ricklepick.model

import com.google.gson.annotations.SerializedName

data class CharacterData(

    val info: Info,

    val results: List<Character>

)

data class Info(
    val count: Int,
    val pages: Int
)

data class Character(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("species")
    val species: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("image")
    val image: String?

)