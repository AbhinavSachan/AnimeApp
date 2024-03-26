package com.abhinavdev.animeapp.remote.model.clubs

import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PaginationData
import com.google.gson.annotations.SerializedName

data class ClubSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<ClubData>>()