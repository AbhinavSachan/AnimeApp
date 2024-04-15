package com.abhinavdev.animeapp.remote.models.characters

import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PaginationData
import com.google.gson.annotations.SerializedName

data class CharacterSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<CharacterData>>()