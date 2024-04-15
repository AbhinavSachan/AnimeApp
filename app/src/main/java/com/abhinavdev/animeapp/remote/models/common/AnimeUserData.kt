package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName


data class AnimeUserData(
    @SerializedName("username") val username: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?
) : BaseModel()