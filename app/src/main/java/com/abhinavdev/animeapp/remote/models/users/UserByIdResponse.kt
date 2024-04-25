package com.abhinavdev.animeapp.remote.models.users

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.model.BaseModel

data class UserByIdResponse(
    @SerializedName("data")
    val `data`: Data?
) : BaseModel() {
    data class Data(
        @SerializedName("url")
        val url: String?,
        @SerializedName("username")
        val username: String?
    ) : BaseModel()
}