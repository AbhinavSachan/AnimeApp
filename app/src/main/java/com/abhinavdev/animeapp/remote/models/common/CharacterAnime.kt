package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName

data class CharacterAnime(
    @SerializedName("role") val role: String?,
    @SerializedName("anime") val anime: CharacterAnimeData?
) : BaseModel()

data class CharacterAnimeData(
    @SerializedName("title") override val name: String?
) : PersonData()