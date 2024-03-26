package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName

data class ImagesData(
    @SerializedName("jpg") val jpg: ImageData?,
    @SerializedName("webp") val webp: ImageData?,
) :BaseModel()