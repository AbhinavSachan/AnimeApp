package com.abhinavdev.animeapp.ui.anime.misc

import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimal

class PresentableMalAnimeData(val position: Int, val item: MalAnimeData) {

    fun getImage(): String? {
        return item.node?.mainPicture?.large
    }

    fun getType(): String {
        return MalAnimeType.valueOfOrDefault(item.node?.mediaType).showName
    }

    fun getRank(): String {
        return (position + 1).toString()
    }

    fun getRating(): String? {
        return item.node?.mean?.formatToOneDigitAfterDecimal()
    }

    fun getName(): String? {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return when (userPreferredType) {
            AppTitleType.ROMAJI -> item.node?.title
            AppTitleType.JAPANESE -> item.node?.alternativeTitles?.ja
            AppTitleType.ENGLISH -> item.node?.alternativeTitles?.en
        }
    }
}