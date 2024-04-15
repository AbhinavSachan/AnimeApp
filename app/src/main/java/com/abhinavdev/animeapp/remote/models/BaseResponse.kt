package com.abhinavdev.animeapp.remote.models

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("status") var status: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("error") var error: String? = null,
    @SerializedName("report_url") var reportUrl: String? = null,
    @SerializedName("data") var `data`: T? = null,
) : BaseModel()
