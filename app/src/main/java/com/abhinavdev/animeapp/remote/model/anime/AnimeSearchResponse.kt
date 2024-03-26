package com.abhinavdev.animeapp.remote.model.anime

import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PaginationData
import com.google.gson.annotations.SerializedName

data class AnimeSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<ArrayList<AnimeData>>()