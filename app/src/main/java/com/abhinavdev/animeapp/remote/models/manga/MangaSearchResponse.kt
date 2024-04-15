package com.abhinavdev.animeapp.remote.models.manga

import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.google.gson.annotations.SerializedName

data class MangaSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<ArrayList<MangaData>>()