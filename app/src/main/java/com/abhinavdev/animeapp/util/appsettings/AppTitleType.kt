package com.abhinavdev.animeapp.util.appsettings

import com.abhinavdev.animeapp.remote.models.common.TitleData
import com.abhinavdev.animeapp.remote.models.enums.TitleType
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeNode
import com.abhinavdev.animeapp.remote.models.malmodels.MalMangaNode
import com.abhinavdev.animeapp.util.extension.placeholder

enum class AppTitleType(val search: String, val showName: String) {
    ROMAJI("romaji", "Romaji"),

    JAPANESE("japanese", "Japanese"),

    ENGLISH("english", "English");

    companion object {
        fun valueOfOrDefault(value: String?) = entries.find { it.search == value } ?: ROMAJI

        fun getTitleFromData(titles: ArrayList<TitleData>?, userPreferredType: AppTitleType) =
            titles?.find { userPreferredType == it.type?.appTitleType }?.title
                ?: titles?.find { ROMAJI == it.type?.appTitleType }?.title.placeholder()

        fun getSynonymTitles(titles: ArrayList<TitleData>?): List<String> {
            val result = arrayListOf<String>()
            titles?.forEach { title ->
                title.takeIf { it.type == TitleType.SYNONYM }?.title?.let { result.add(it) }
            }
            return result
        }

        fun getTitleFromData(node: MalAnimeNode?, userPreferredType: AppTitleType) =
            when (userPreferredType) {
                ROMAJI -> node?.title
                JAPANESE -> node?.alternativeTitles?.ja ?: node?.title
                ENGLISH -> node?.alternativeTitles?.en ?: node?.title
            }.placeholder()

        fun getTitleFromData(node: MalMangaNode?, userPreferredType: AppTitleType) =
            when (userPreferredType) {
                ROMAJI -> node?.title
                JAPANESE -> node?.alternativeTitles?.ja ?: node?.title
                ENGLISH -> node?.alternativeTitles?.en ?: node?.title
            }.placeholder()

        val list = entries
    }
}
