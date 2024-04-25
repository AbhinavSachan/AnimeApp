package com.abhinavdev.animeapp.remote.models.users

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.google.gson.annotations.SerializedName

class UserByIdResponse : BaseResponse<UserByIdData>()

data class UserByIdData(
    @SerializedName("url") val url: String?,
    @SerializedName("username") val username: String?
) : BaseModel()
