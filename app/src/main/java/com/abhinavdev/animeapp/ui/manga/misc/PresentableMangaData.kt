package com.abhinavdev.animeapp.ui.manga.misc

import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull

class PresentableMangaData(val position: Int, val item: MangaData) {

    fun getImage(): String? {
        return item.images?.webp?.largeImageUrl
    }

    fun getType(): String {
        return MangaType.valueOfOrDefault(item.type).showName
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