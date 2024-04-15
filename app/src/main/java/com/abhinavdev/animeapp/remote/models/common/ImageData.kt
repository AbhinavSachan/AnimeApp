package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("small_image_url") val smallImageUrl: String?,
    @SerializedName("medium_image_url") val mediumImageUrl: String?,
    @SerializedName("large_image_url") val largeImageUrl: String?,
    @SerializedName("maximum_image_url") val maximumImageUrl: String?,
) : BaseModel()
