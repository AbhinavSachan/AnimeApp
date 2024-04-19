package com.abhinavdev.animeapp.remote.models.anime

import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

class EpisodeResponse : BaseResponse<EpisodeData>()

data class EpisodeData(
    @SerializedName("url") val url: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
    @SerializedName("title_romanji") val titleRomanji: String?,
    @SerializedName("duration") val duration: Int?,
    @SerializedName("aired") val aired: String?,
    @SerializedName("filler") val filler: Boolean?,
    @SerializedName("recap") val recap: Boolean?,
    @SerializedName("synopsis") val synopsis: String?,
) : BaseModelWithMal()
