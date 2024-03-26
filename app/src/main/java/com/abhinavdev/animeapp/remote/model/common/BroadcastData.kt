package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName

data class BroadcastData(
    @SerializedName("day") val day: String?,
    @SerializedName("time") val time: String?,
    @SerializedName("timezone") val timezone: String?,
    @SerializedName("string") val string: String?,
) : BaseModel()