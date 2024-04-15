package com.abhinavdev.animeapp.remote.models.common

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.google.gson.annotations.SerializedName


data class PaginationData(
    @SerializedName("last_visible_page") val lastVisiblePage: Int?,
    @SerializedName("has_next_page") val hasNextPage: Boolean?,
    @SerializedName("items") val countData: CountData?,
) : BaseModel()

data class CountData(
    @SerializedName("count") val count: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("per_page") val perPage: Int?,
) : BaseModel()