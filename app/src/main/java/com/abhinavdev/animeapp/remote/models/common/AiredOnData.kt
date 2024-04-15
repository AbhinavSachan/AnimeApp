package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName


data class AiredOnData(
    @SerializedName("from") val fromDate: String?,
    @SerializedName("to") val toDate: String?,
    @SerializedName("prop") val prop: Prop?,
) : BaseModel()

data class Prop(
    @SerializedName("from") val from: AiredDate?,
    @SerializedName("to") val to: AiredDate?,
    @SerializedName("string") val parsedDateString: String?,
) : BaseModel()

data class AiredDate(
    @SerializedName("day") val day: Int?,
    @SerializedName("month") val month: Int?,
    @SerializedName("year") val year: Int?,
) : BaseModel()