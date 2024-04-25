package com.abhinavdev.animeapp.remote.models.malmodels

import com.google.gson.annotations.SerializedName

data class MalProfileResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
) : MalBaseModel()