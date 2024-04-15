package com.abhinavdev.animeapp.ui.anime.misc

sealed class MultiApiCallType {
    data object TopAiring : MultiApiCallType()
    data object TopPopular : MultiApiCallType()
    data object TopFavourite : MultiApiCallType()
    data object TopUpcoming : MultiApiCallType()
}