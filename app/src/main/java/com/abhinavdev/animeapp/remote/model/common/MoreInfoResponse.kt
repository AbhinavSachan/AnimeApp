package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

class MoreInfoResponse : BaseResponse<MoreInfoData>()

data class MoreInfoData(
    @SerializedName("moreinfo") val moreInfo: String?,
) : BaseModel()