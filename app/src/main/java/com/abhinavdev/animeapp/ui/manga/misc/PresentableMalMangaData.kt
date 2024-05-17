package com.abhinavdev.animeapp.ui.manga.misc

import android.content.Context
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.remote.models.enums.MalMangaType
import com.abhinavdev.animeapp.remote.models.malmodels.MalMangaData
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.AppTitleType
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.NumExtensions.toStringOrUnknown
import com.abhinavdev.animeapp.util.extension.formatToOneDigitAfterDecimalOrNull
import com.abhinavdev.animeapp.util.extension.getFormattedDateOrNull
import com.abhinavdev.animeapp.util.extension.placeholder

class PresentableMalMangaData(val position: Int, val item: MalMangaData) {

    fun getImage(): String? {
        return item.node?.mainPicture?.large
    }

    fun getChapters(): String? {
        return item.node?.numChapters?.takeIf { it > 0 }?.toString()
    }

    fun getVolumes(): String? {
        return item.node?.numVolumes?.takeIf { it > 0 }?.toString()
    }

    fun getType(): String {
        return MalMangaType.valueOfOrDefault(item.node?.mediaType?.search).showName
    }

    fun getRank(): String? {
        val rank = item.ranking?.rank
        return if (rank != null) "#$rank" else null
    }

    fun getRating(): String? {
        return item.node?.mean?.formatToOneDigitAfterDecimalOrNull()
    }

    fun getTypeWithChapter(context: Context): String {
        val chapters = item.node?.numChapters
        val volumes = item.node?.numVolumes
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
        return item.node?.status?.showName
    }

    fun getDate(context: Context): String {
        val startDate = getFormattedDateOrNull(item.node?.startDate)
        val endDate = getFormattedDateOrNull(item.node?.endDate)

        val result = StringBuilder()
        result.append("${startDate.placeholder()} ${context.getString(R.string.msg_to)} ${endDate.placeholder()}")
        if (startDate.isNullOrBlank() && endDate.isNullOrBlank()) {
            result.clear().append(Const.Other.UNKNOWN_CHAR)
        }
        return result.toString()
    }

    fun getName(): String {
        val userPreferredType = SettingsHelper.getPreferredTitleType()
        return AppTitleType.getTitleFromData(item.node, userPreferredType)
    }
}