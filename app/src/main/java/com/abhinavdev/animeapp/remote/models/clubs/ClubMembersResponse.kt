package com.abhinavdev.animeapp.remote.models.clubs

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.ImagesData
import com.google.gson.annotations.SerializedName

class ClubMembersResponse : BaseResponse<List<ClubMembersData>>()

data class ClubMembersData(
    @SerializedName("username") val name: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
) : BaseModel()
