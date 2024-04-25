package com.abhinavdev.animeapp.ui.anime.misc

import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import retrofit2.Response

sealed class MultiApiCallType {
    data object TopAiring : MultiApiCallType()
    data object TopPopular : MultiApiCallType()
    data object TopFavourite : MultiApiCallType()
    data object TopUpcoming : MultiApiCallType()
    data object TopRecommended : MultiApiCallType()
    data object TopRanked : MultiApiCallType()
}

sealed class ApiResponse {
    data class AnimeSearch(val response: Response<AnimeSearchResponse>) : ApiResponse()
    data class MalSearch(val response: Response<MalMyAnimeListResponse>) : ApiResponse()
}
