package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.R

enum class AppMediaType(val search: String, val showNameRes: Int) {
    ANIME("anime", R.string.msg_anime), MANGA("manga", R.string.msg_manga);

    companion object {
        fun valueOfOrDefault(isoTag: String?) = entries.find { it.search == isoTag } ?: ANIME
    }

}