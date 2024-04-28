package com.abhinavdev.animeapp.remote.kit.repository

import androidx.annotation.IntRange
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.MalAnimeType
import com.abhinavdev.animeapp.remote.models.enums.MalMangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MalMangaType
import com.abhinavdev.animeapp.remote.models.enums.MalSortType
import com.abhinavdev.animeapp.remote.models.malmodels.AnimeListStatus
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyMangaListResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalProfileResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MangaListStatus
import retrofit2.Response

interface MalRepository {
    suspend fun updateAnimeListStatus(
        animeId: Int,
        status: MalAnimeStatus,
        isReWatching: Boolean,
        @IntRange(0, 10) score: Int,
        numWatchedEpisodes: Int,
        @IntRange(0, 2) priority: Int,
        startDate: String,
        finishDate: String,
        numTimesReWatched: Int,
        @IntRange(0, 5) reWatchValue: Int,
        tags: ArrayList<String>,
        comments: String,
    ): Response<AnimeListStatus>

    suspend fun deleteAnimeFromList(
        animeId: Int
    ): Response<Any>

    suspend fun getMyAnimeList(
        status: MalAnimeStatus,
        sort: MalSortType,
        @IntRange(0, 1000) limit: Int,
        offset: Int,
    ): Response<MalMyAnimeListResponse>

    suspend fun updateMangaListStatus(
        mangaId: Int,
        status: MalMangaStatus,
        isReWatching: Boolean,
        @IntRange(0, 10) score: Int,
        numChaptersRead: Int,
        numVolumesRead: Int,
        @IntRange(0, 2) priority: Int,
        startDate: String,
        finishDate: String,
        numTimesReread: Int,
        @IntRange(0, 5) rereadValue: Int,
        tags: ArrayList<String>,
        comments: String,
    ): Response<MangaListStatus>

    suspend fun deleteMangaFromList(
        mangaId: Int
    ): Response<Any>

    suspend fun getMyMangaList(
        status: MalMangaStatus,
        sort: MalSortType,
        @IntRange(0, 1000) limit: Int,
        offset: Int,
    ): Response<MalMyMangaListResponse>

    suspend fun getProfile(): Response<MalProfileResponse>


    suspend fun getRecommendedAnime(
        @IntRange(0, 100) limit: Int,
        offset: Int,
    ): Response<MalMyAnimeListResponse>

    suspend fun getAnimeRanking(
        rankingType: MalAnimeType,
        @IntRange(0, 500) limit: Int,
        offset: Int,
    ): Response<MalMyAnimeListResponse>

    suspend fun getMangaRanking(
        rankingType: MalMangaType,
        @IntRange(0, 500) limit: Int,
        offset: Int,
    ): Response<MalMyMangaListResponse>

}