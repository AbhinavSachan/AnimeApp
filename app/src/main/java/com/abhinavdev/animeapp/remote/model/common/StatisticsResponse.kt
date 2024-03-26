package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

class StatisticsResponse : BaseResponse<StatisticsData>()

data class StatisticsData(
    @SerializedName("watching", alternate = ["reading"]) val viewers: Int?,
    @SerializedName("completed") val completed: Int?,
    @SerializedName("on_hold") val onHold: Int?,
    @SerializedName("dropped") val dropped: Int?,
    @SerializedName("plan_to_watch", alternate = ["plan_to_read"]) val planToView: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("scores") val scores: List<Score>?
) : BaseModel()

data class Score(
    @SerializedName("score") val score: Int?,
    @SerializedName("votes") val votes: Int?,
    @SerializedName("percentage") val percentage: Int?
) : BaseModel()
