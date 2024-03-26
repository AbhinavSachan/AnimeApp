package com.abhinavdev.animeapp.remote.model.clubs

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.ImagesData
import com.google.gson.annotations.SerializedName

class ClubMembersResponse : BaseResponse<List<ClubMembersData>>()

data class ClubMembersData(
    @SerializedName("username") val name: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
) : BaseModel()
