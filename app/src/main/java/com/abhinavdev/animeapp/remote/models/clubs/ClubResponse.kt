package com.abhinavdev.animeapp.remote.models.clubs

import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.ImagesData
import com.abhinavdev.animeapp.remote.models.enums.ClubCategory
import com.abhinavdev.animeapp.remote.models.enums.ClubType
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
