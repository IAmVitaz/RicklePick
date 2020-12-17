package com.vitaz.ricklepick.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CharacterData(

    val info: Info,
    val results: List<Character>

)

data class Info(
    val count: Int,
    val pages: Int
)

@Parcelize
data class Character(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("species")
    val species: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("origin")
    val origin: Location,

    @SerializedName("location")
    val location: Location,

    @SerializedName("image")
    val image: String?,

    @SerializedName("episode")
    val episode: List<String>

): Parcelable

@Parcelize
data class Location(
    val name: String?,
    val url: String?
): Parcelable