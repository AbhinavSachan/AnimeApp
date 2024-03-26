package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.google.gson.annotations.SerializedName


data class VideoEpisodeData(
    @SerializedName("title") val title: String?,
    @SerializedName("episode") val episode: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?
) : BaseModelWithMal()
