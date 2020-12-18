package com.vitaz.ricklepick.model

import com.google.gson.annotations.SerializedName

data class EpisodesData (
    val info: Info,
    val results: List<Episode>
)

data class Episode(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("air_date")
    val air_date: String?,

    @SerializedName("episode")
    val episode: String?,

    @SerializedName("characters")
    val characters: List<String>
)