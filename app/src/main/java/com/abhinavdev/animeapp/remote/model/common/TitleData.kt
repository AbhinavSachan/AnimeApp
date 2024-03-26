package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName


data class TitleData(
    @SerializedName("type") val type: String?,
    @SerializedName("title") val title: String?,
) : BaseModel()