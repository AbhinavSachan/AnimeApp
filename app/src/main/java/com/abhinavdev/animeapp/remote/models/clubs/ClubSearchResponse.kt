package com.abhinavdev.animeapp.remote.models.clubs

import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.google.gson.annotations.SerializedName

data class ClubSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<ClubData>>()