package com.abhinavdev.animeapp.ui.anime.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull
import com.abhinavdev.animeapp.util.extension.getAiredDate
import com.abhinavdev.animeapp.util.extension.placeholder

class PresentableAnimeData(val position: Int, val item: AnimeData) {

    fun getImage(): String? {
        return item.images?.webp?.largeImageUrl
    }

    fun getEpisode(): String? {
        return item.episodes?.takeIf { it > 0 }?.toString()
    }

    fun getType(): String {
        return AnimeType.valueOfOrDefault(item.type?.search).showName
    }

    fun getRank(): String {
        return "#${position + 1}"
    }

    fun getRating(): String? {
        return item.score?.formatToOneDigitAfterDecimalOrNull()
    }

    fun getTypeWithEpisode(context: Context): String {
        val episodes = item.episodes
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
        return item.status?.showName
    }

    fun getDate(context: Context): String {
        return item.airedOn.getAiredDate(context)
    }

    fun getSeasonWithYear(): String {
        val season = item.season?.showName
        val year = (item.year  ?: item.airedOn?.prop?.from?.year)?.toString()
        val result = StringBuilder()
        result.append(season.placeholder())
        if (!year.isNullOrBlank()){
            result.append(" (${year.placeholder()})")
        }
        return result.toString()
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return AppTitleType.getTitleFromData(item.titles,userPreferredType)
    }
}