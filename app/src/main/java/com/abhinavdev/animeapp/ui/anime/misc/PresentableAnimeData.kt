package com.abhinavdev.animeapp.ui.anime.misc

import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull

class PresentableAnimeData(val position: Int, val item: AnimeData) {

    fun getImage(): String? {
        return item.images?.webp?.largeImageUrl
    }

    fun getType(): String {
        return AnimeType.valueOfOrDefault(item.type).showName
    }

    fun getRank(): String {
        return "#${position + 1}"
    }

    fun getRating(): String? {
        return item.score?.formatToOneDigitAfterDecimalOrNull()
    }

    fun getName(): String? {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return item.titles?.find { userPreferredType == it.type?.appTitleType }?.title
    }
}