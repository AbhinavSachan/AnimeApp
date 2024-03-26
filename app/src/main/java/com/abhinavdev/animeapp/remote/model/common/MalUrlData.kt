package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.google.gson.annotations.SerializedName

open class MalUrlData(
    @SerializedName("type") open val type: String? = null,
    @SerializedName("name") open val name: String? = null,
    @SerializedName("url") open val url: String? = null,
) : BaseModelWithMal()