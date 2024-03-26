package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class RecentAnimeRecommendationsResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<ArrayList<AnimeRecommendationData>>()

data class AnimeRecommendationData(
    @SerializedName("entry") val entry: ArrayList<RecommendationsEntry>?,
    @SerializedName("content") val content: String?,
    @SerializedName("user") val user: User?
) : BaseModelWithMal()

data class User(
    @SerializedName("url") val url: String?, @SerializedName("username") val username: String?
) : BaseModel()
