package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName


data class CharacterManga(
    @SerializedName("role") val role: String?,
    @SerializedName("manga") val manga: CharacterMangaData?
) : BaseModel()

data class CharacterMangaData(
    @SerializedName("title") override val name: String?
) : PersonData()
