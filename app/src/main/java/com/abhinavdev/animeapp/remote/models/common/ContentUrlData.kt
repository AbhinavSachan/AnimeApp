package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName

open class ContentUrlData(
    @SerializedName("name") open val name: String? = null,
    @SerializedName("url") open val url: String? = null,
) : BaseModel()
