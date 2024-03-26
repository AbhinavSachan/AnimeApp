package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName


data class OpEdThemeData(
    @SerializedName("openings") val openings: List<String>?,
    @SerializedName("endings") val endings: List<String>?,
) : BaseModel()