package com.abhinavdev.animeapp.remote.models.malmodels

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.model.BaseModel

data class MalProfileResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
) : BaseModel()