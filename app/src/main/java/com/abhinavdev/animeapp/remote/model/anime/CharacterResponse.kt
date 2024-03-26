package com.abhinavdev.animeapp.remote.model.anime

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PersonData
import com.google.gson.annotations.SerializedName

class CharacterResponse : BaseResponse<ArrayList<AnimeCharacterData>>()

data class AnimeCharacterData(
    @SerializedName("character") val character: Character?,
    @SerializedName("role") val role: String?,
    @SerializedName("voice_actors") val voiceActors: ArrayList<VoiceActor>?,
) : BaseModel()

data class VoiceActor(
    @SerializedName("person") val person: PersonData?,
    @SerializedName("language") val language: String?,
) : BaseModel()

class Character : PersonData()
