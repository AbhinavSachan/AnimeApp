package com.abhinavdev.animeapp.remote.models.malmodels

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("token_type") val tokenType: String?,
    @SerializedName("expires_in") val expiresIn: Int?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("refresh_token") val refreshToken: String?,
) : MalBaseModel()