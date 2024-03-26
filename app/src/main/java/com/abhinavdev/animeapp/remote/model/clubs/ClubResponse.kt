package com.abhinavdev.animeapp.remote.model.clubs

import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.ImagesData
import com.abhinavdev.animeapp.remote.model.enums.ClubCategory
import com.abhinavdev.animeapp.remote.model.enums.ClubType
import com.google.gson.annotations.SerializedName

class ClubResponse : BaseResponse<ClubData>()

data class ClubData(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("members") val members: Int?,
    @SerializedName("category") val category: ClubCategory?,
    @SerializedName("created") val created: String?,
    @SerializedName("access") val access: ClubType?
) : BaseModelWithMal()
