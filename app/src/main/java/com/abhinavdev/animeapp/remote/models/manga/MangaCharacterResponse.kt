package com.abhinavdev.animeapp.remote.models.manga

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PersonData
import com.google.gson.annotations.SerializedName

class MangaCharacterResponse : BaseResponse<ArrayList<MangaCharactersData>>()


data class MangaCharactersData(
    @SerializedName("character") val character: MangaCharacterData?,
    @SerializedName("role") val role: String?
) : BaseModel()

class MangaCharacterData : PersonData()
