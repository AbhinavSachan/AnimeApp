package com.abhinavdev.animeapp.ui.anime.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.anime.AnimeData
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.formatTo
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull
import com.abhinavdev.animeapp.util.extension.placeholder
import java.util.Calendar

class PresentableAnimeData(val position: Int, val item: AnimeData) {

    fun getImage(): String? {
        return item.images?.webp?.largeImageUrl
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
            result.append(" (${episodes.toStringOrUnknown()} ${context.getString(R.string.msg_episodes)})")
        }
        return result.toString()
    }

    fun getStatus(): String? {
        return item.status?.showName
    }

    fun getDate(): String {
        val from = item.airedOn?.prop?.from
        val to = item.airedOn?.prop?.to
        var startDate = ""
        var endDate = ""
        if (from != null) {
            val day = from.day
            val month = from.month
            val year = from.year
            val cal = Calendar.getInstance()
            if (day != null && month != null && year != null){
                cal.set(year, month, day)
                startDate = cal.time.formatTo().placeholder()
            }
        }
        if (to != null) {
            val day = to.day
            val month = to.month
            val year = to.year
            val cal = Calendar.getInstance()
            if (day != null && month != null && year != null){
                cal.set(year, month, day)
                endDate = cal.time.formatTo().placeholder()
            }
        }
        return "$startDate to $endDate"
    }

    fun getSeasonWithYear(): String {
        return "${item.season?.showName.placeholder()} (${item.year?.toString().placeholder()})"
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return item.titles?.find { userPreferredType == it.type?.appTitleType }?.title ?: item.titles?.find { AppTitleType.ROMAJI == it.type?.appTitleType }?.title ?: Const.Other.UNKNOWN_CHAR
    }
}