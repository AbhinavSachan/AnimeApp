package com.abhinavdev.animeapp.ui.manga.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.formatTo
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull
import com.abhinavdev.animeapp.util.extension.placeholder
import java.util.Calendar

class PresentableMangaData(val position: Int, val item: MangaData) {

    fun getImage(): String? {
        return item.images?.webp?.largeImageUrl
    }

    fun getChapters(): String? {
        return item.chapters?.takeIf { it > 0 }?.toString()
    }

    fun getVolumes(): String? {
        return item.volumes?.takeIf { it > 0 }?.toString()
    }

    fun getType(): String {
        return MangaType.valueOfOrDefault(item.type?.search).showName
    }

    fun getRating(): String? {
        return item.score?.formatToOneDigitAfterDecimalOrNull()
    }

    fun getTypeWithChapter(context: Context): String {
        val chapters = item.chapters
        val volumes = item.volumes
        val result = StringBuilder()
        result.append(getType())
        if (chapters != null && chapters > 0) {
            val chapterRes = if (chapters == 1){
                R.string.msg_chapter
            }else{
                R.string.msg_chapters
            }
            result.append(" (${chapters.toStringOrUnknown()} ${context.getString(chapterRes)})")
        }
        if (volumes != null && volumes > 0) {
            val volumeRes = if (volumes == 1){
                R.string.msg_volume
            }else{
                R.string.msg_volumes
            }
            result.append(" (${volumes.toStringOrUnknown()} ${context.getString(volumeRes)})")
        }
        return result.toString()
    }

    fun getStatus(): String? {
        return item.status?.showName
    }

    fun getDate(context: Context): String {
        val from = item.published?.prop?.from
        val to = item.published?.prop?.to
        var startDate = ""
        var endDate = ""
        if (from != null) {
            val day = from.day
            val month = from.month
            val year = from.year
            val cal = Calendar.getInstance()
            if (day != null && month != null && year != null) {
                cal.set(year, month, day)
                startDate = cal.time.formatTo().placeholder()
            }
        }
        if (to != null) {
            val day = to.day
            val month = to.month
            val year = to.year
            val cal = Calendar.getInstance()
            if (day != null && month != null && year != null) {
                cal.set(year, month, day)
                endDate = cal.time.formatTo().placeholder()
            }
        }
        val result = StringBuilder()
        result.append("${startDate.placeholder()} ${context.getString(R.string.msg_to)} ${endDate.placeholder()}")
        if (startDate.isBlank() && endDate.isBlank()){
            result.clear().append(Const.Other.UNKNOWN_CHAR)
        }
        return result.toString()
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return AppTitleType.getTitleFromData(item.titles, userPreferredType)
    }
}