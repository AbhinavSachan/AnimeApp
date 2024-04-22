package com.abhinavdev.animeapp.remote.models

import com.google.gson.annotations.SerializedName

open class BaseModelWithMal(
    @SerializedName("mal_id") open val malId: Int = 0,
) : BaseModel()
