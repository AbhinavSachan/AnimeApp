package com.abhinavdev.animeapp.ui.anime.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeData
import com.abhinavdev.animeapp.util.Const
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
            val episodeRes = if (episodes == 1){
                R.string.msg_episode
            }else{
                R.string.msg_episodes
            }
            result.append(" (${episodes.toStringOrUnknown()} ${context.getString(episodeRes)})")
        }
        return result.toString()
    }

    fun getStatus(): String? {
        return item.node?.status?.showName
    }

    fun getDate(context: Context): String {
        val startDate = getFormattedDateOrNull(item.node?.startDate)
        val endDate = getFormattedDateOrNull(item.node?.endDate)

        val result = StringBuilder()
        result.append("${startDate.placeholder()} ${context.getString(R.string.msg_to)} ${endDate.placeholder()}")
        if (startDate.isNullOrBlank() && endDate.isNullOrBlank()){
            result.clear().append(Const.Other.UNKNOWN_CHAR)
        }
        return result.toString()
    }

    fun getSeasonWithYear(): String {
        val season = item.node?.startSeason?.season?.showName
        val year = (item.node?.startSeason?.year ?: getFormattedDateOrNull(item.node?.startDate, outFormat = "yyyy"))?.toString()
        val result = StringBuilder()
        result.append(season.placeholder())
        if (!year.isNullOrBlank()){
            result.append(" (${year.placeholder()})")
        }
        return result.toString()
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return AppTitleType.getTitleFromData(item.node,userPreferredType)
    }
}