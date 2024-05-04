package com.abhinavdev.animeapp.ui.manga.misc

import com.abhinavdev.animeapp.remote.models.enums.MalMangaType
import com.abhinavdev.animeapp.remote.models.malmodels.MalMangaData
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull

class PresentableMalMangaData(val position: Int, val item: MalMangaData) {

    fun getImage(): String? {
        return item.node?.mainPicture?.large
    }

    fun getType(): String {
        return MalMangaType.valueOfOrDefault(item.node?.mediaType).showName
    }

    fun getRank(): String {
        return "#${position + 1}"
    }

    fun getRating(): String? {
        return item.node?.mean?.formatToOneDigitAfterDecimalOrNull()
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