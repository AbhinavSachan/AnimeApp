package com.abhinavdev.animeapp.remote.model.manga

import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PaginationData
import com.google.gson.annotations.SerializedName

data class MangaSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<ArrayList<MangaData>>()