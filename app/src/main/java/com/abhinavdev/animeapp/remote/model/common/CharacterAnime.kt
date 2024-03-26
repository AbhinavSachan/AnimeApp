package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName

data class CharacterAnime(
    @SerializedName("role") val role: String?,
    @SerializedName("anime") val anime: CharacterAnimeData?
) : BaseModel()

data class CharacterAnimeData(
    @SerializedName("title") override val name: String?
) : PersonData()