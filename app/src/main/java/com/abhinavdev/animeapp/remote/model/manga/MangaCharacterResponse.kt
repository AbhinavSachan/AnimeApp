package com.abhinavdev.animeapp.remote.model.manga

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PersonData
import com.google.gson.annotations.SerializedName

class MangaCharacterResponse : BaseResponse<ArrayList<MangaCharactersData>>()


data class MangaCharactersData(
    @SerializedName("character") val character: MangaCharacterData?,
    @SerializedName("role") val role: String?
) : BaseModel()

class MangaCharacterData : PersonData()
