package com.abhinavdev.animeapp.remote.models.anime

import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.google.gson.annotations.SerializedName

data class AnimeSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<ArrayList<AnimeData>>()