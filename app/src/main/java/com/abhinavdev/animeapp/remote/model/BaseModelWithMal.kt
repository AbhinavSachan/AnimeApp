package com.abhinavdev.animeapp.remote.model

import com.google.gson.annotations.SerializedName

open class BaseModelWithMal(
    @SerializedName("mal_id") open val malId: Int? = null,
) : BaseModel()
