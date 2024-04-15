package com.abhinavdev.animeapp.remote.models.clubs

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

class ClubStaffResponse : BaseResponse<List<ClubStaffData>>()

data class ClubStaffData(
    @SerializedName("username") val name: String?,
    @SerializedName("url") val url: String?,
) : BaseModel()
