package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

class RecommendationsResponse : BaseResponse<ArrayList<RecommendationsData>>()

data class RecommendationsData(
    @SerializedName("entry") val entry: RecommendationsEntry?
) : BaseModel()

data class RecommendationsEntry(
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("title") val title: String?
) : BaseModelWithMal()
