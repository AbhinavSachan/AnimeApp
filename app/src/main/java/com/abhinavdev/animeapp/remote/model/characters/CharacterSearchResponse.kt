package com.abhinavdev.animeapp.remote.model.characters

import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PaginationData
import com.google.gson.annotations.SerializedName

data class CharacterSearchResponse(
    @SerializedName("pagination") val pagination: PaginationData?
) : BaseResponse<List<CharacterData>>()