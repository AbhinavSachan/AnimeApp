package com.abhinavdev.animeapp.ui.anime.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull
import com.abhinavdev.animeapp.util.extension.getFormattedDateOrNull
import com.abhinavdev.animeapp.util.extension.placeholder

class PresentableMalAnimeData(val position: Int, val item: MalAnimeData) {

    fun getImage(): String? {
        return item.node?.mainPicture?.large
    }

    fun getEpisode(): String? {
        return item.node?.numEpisodes?.takeIf { it > 0 }?.toString()
    }

    fun getType(): String {
        return MalAnimeType.valueOfOrDefault(item.node?.mediaType?.search).showName
    }

    fun getRank(): String? {
        val rank = item.ranking?.rank
        return if (rank != null) "#$rank" else null
    }

    fun getRating(): String? {
        return item.node?.mean?.formatToOneDigitAfterDecimalOrNull()
    }

    fun getTypeWithEpisode(context: Context): String {
        val episodes = item.node?.numEpisodes
        val result = StringBuilder()
        result.append(getType().uppercase())
        if (episodes != null && episodes > 0){
            result.append(" (${episodes.toStringOrUnknown()} ${context.getString(R.string.msg_episodes)})")
        }
        return result.toString()
    }

    fun getStatus(): String? {
        return item.node?.status?.showName
    }

    fun getDate(): String {
        return "${getFormattedDateOrNull(item.node?.startDate).placeholder()} to ${getFormattedDateOrNull(item.node?.endDate).placeholder()}"
    }

    fun getSeasonWithYear(): String {
        return "${item.node?.startSeason?.season?.showName.placeholder()} (${item.node?.startSeason?.year.placeholder()})"
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return AppTitleType.getTitleFromData(item.node,userPreferredType)
    }
}