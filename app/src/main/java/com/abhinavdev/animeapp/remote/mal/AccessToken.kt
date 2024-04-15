package com.abhinavdev.animeapp.remote.mal

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("token_type") val tokenType: String = "",
    @SerializedName("expires_in") val expiresIn: Int = 0,
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("message") val message: String? = null,
) : BaseModel()