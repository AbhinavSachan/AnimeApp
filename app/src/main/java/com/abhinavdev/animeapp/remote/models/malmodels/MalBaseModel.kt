package com.abhinavdev.animeapp.remote.models.malmodels

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName

open class MalBaseModel(
    @SerializedName("error") open val error: String? = null,
    @SerializedName("message") open val message: String? = null,
    @SerializedName("hint") open val hint: String? = null
) : BaseModel()