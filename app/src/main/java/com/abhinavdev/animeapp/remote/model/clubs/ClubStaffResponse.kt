package com.abhinavdev.animeapp.remote.model.clubs

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

class ClubStaffResponse : BaseResponse<List<ClubStaffData>>()

data class ClubStaffData(
    @SerializedName("username") val name: String?,
    @SerializedName("url") val url: String?,
) : BaseModel()
