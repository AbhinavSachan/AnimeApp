package com.abhinavdev.animeapp.ui.anime.misc

sealed class MultiContentAdapterType {
    data object TopAiring : MultiContentAdapterType()
    data object TopPopular : MultiContentAdapterType()
    data object TopFavourite : MultiContentAdapterType()
    data object TopUpcoming : MultiContentAdapterType()
    data object TopRecommended : MultiContentAdapterType()
    data object TopRanked : MultiContentAdapterType()
}