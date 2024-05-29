package com.abhinavdev.animeapp.ui.manga.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.manga.MangaData
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull
import com.abhinavdev.animeapp.util.extension.getAiredDate

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
        return item.published.getAiredDate(context)
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return AppTitleType.getTitleFromData(item.titles, userPreferredType)
    }
}