package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName


data class OpEdThemeData(
    @SerializedName("openings") val openings: List<String>?,
    @SerializedName("endings") val endings: List<String>?,
) : BaseModel()