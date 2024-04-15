package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<ReviewData>>()

data class ReviewData(
    @SerializedName("user") val user: AnimeUserData?,
    @SerializedName("url") val url: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("reactions") val reactions: Reactions?,
    @SerializedName("date") val date: String?,
    @SerializedName("review") val review: String?,
    @SerializedName("score") val score: Int?,
    @SerializedName("tags") val tags: ArrayList<String>?,
    @SerializedName("is_spoiler") val isSpoiler: Boolean?,
    @SerializedName("is_preliminary") val isPreliminary: Boolean?,
    @SerializedName("episodes_watched") val episodesWatched: Int?
) : BaseModelWithMal()

data class Reactions(
    @SerializedName("overall") val overall: Int?,
    @SerializedName("nice") val nice: Int?,
    @SerializedName("love_it") val loveIt: Int?,
    @SerializedName("funny") val funny: Int?,
    @SerializedName("confusing") val confusing: Int?,
    @SerializedName("informative") val informative: Int?,
    @SerializedName("well_written") val wellWritten: Int?,
    @SerializedName("creative") val creative: Int?
) : BaseModel()
