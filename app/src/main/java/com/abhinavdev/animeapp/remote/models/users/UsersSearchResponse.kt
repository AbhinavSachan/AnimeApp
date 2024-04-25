package com.abhinavdev.animeapp.remote.models.users

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.ImagesData
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.google.gson.annotations.SerializedName

data class UsersSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<ArrayList<UserSearchData>>()

data class UserSearchData(
    @SerializedName("url") val url: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("last_online") val lastOnline: String?
) : BaseModel()