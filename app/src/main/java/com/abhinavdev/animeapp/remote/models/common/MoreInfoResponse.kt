package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

class MoreInfoResponse : BaseResponse<MoreInfoData>()

data class MoreInfoData(
    @SerializedName("moreinfo") val moreInfo: String?,
) : BaseModel()