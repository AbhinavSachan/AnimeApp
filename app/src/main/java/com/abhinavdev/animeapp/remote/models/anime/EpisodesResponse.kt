package com.abhinavdev.animeapp.remote.models.anime

import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.google.gson.annotations.SerializedName

data class EpisodesResponse(
    @SerializedName("pagination") val pagination: PaginationData?,
) : BaseResponse<List<EpisodesData>>()

data class EpisodesData(
    @SerializedName("url") val url: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
    @SerializedName("title_romanji") val titleRomanji: String?,
    @SerializedName("duration") val duration: Int?,
    @SerializedName("aired") val aired: String?,
    @SerializedName("filler") val filler: Boolean?,
    @SerializedName("recap") val recap: Boolean?,
    @SerializedName("forum_url") val forumUrl: String?,
) : BaseModelWithMal()
