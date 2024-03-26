package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.google.gson.annotations.SerializedName


open class PersonData(
    @SerializedName("url") open val url: String? = null,
    @SerializedName("images") open val images: ImagesData? = null,
    @SerializedName("name") open val name: String? = null,
) : BaseModelWithMal()
