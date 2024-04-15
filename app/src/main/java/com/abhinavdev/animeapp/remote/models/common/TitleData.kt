package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.enums.TitleType
import com.google.gson.annotations.SerializedName


data class TitleData(
    @SerializedName("type") val type: TitleType?,
    @SerializedName("title") val title: String?,
) : BaseModel()