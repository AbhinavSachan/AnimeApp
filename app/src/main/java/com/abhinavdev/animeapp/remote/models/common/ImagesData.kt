package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName

data class ImagesData(
    @SerializedName("jpg") val jpg: ImageData?,
    @SerializedName("webp") val webp: ImageData?,
) :BaseModel()