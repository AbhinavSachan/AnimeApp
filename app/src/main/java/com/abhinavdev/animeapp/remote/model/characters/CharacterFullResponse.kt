package com.abhinavdev.animeapp.remote.model.characters

import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.anime.VoiceActor
import com.abhinavdev.animeapp.remote.model.common.CharacterAnime
import com.abhinavdev.animeapp.remote.model.common.CharacterManga
import com.abhinavdev.animeapp.remote.model.common.ImagesData
import com.google.gson.annotations.SerializedName

class CharacterFullResponse : BaseResponse<CharacterData>()

data class CharacterData(
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("name") val name: String?,
    @SerializedName("name_kanji") val nameKanji: String?,
    @SerializedName("nicknames") val nicknames: List<String?>?,
    @SerializedName("favorites") val favorites: Int?,
    @SerializedName("about") val about: String?,
    @SerializedName("anime") val anime: List<CharacterAnime>?,
    @SerializedName("manga") val manga: List<CharacterManga>?,
    @SerializedName("voices") val voices: List<VoiceActor>?
) : BaseModelWithMal()
