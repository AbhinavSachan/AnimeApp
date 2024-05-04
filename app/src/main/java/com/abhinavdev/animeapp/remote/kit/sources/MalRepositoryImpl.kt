package com.abhinavdev.animeapp.remote.kit.sources

import androidx.annotation.IntRange
import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.MalRepository
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

class MalRepositoryImpl : MalRepository {
    private val apiService = ApiClient.getMalApiService()

    private val fields = "my_list_status{priority,comments},alternative_titles,media_type,mean,nsfw,num_episodes,start_season,num_chapters,start_date,num_volumes,end_date,status"

    override suspend fun updateAnimeListStatus(
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
        comments: String
    ): Response<AnimeListStatus> {
        return apiService.updateAnimeListStatus(
            animeId,
            status.search,
            isReWatching,
            score,
            numWatchedEpisodes,
            priority,
            startDate,
            finishDate,
            numTimesReWatched,
            reWatchValue,
            tags,
            comments
        )
    }

    override suspend fun deleteAnimeFromList(animeId: Int): Response<Any> {
        return apiService.deleteAnimeFromList(animeId)
    }

    override suspend fun getMyAnimeList(
        status: MalAnimeStatus, sort: MalSortType, limit: Int, offset: Int
    ): Response<MalMyAnimeListResponse> {
        return apiService.getMyAnimeList(status.search, sort.search, limit, offset, fields)
    }

    override suspend fun updateMangaListStatus(
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
        comments: String
    ): Response<MangaListStatus> {
        return apiService.updateMangaListStatus(
            mangaId,
            status.search,
            isReWatching,
            score,
            numChaptersRead,
            numVolumesRead,
            priority,
            startDate,
            finishDate,
            numTimesReread,
            rereadValue,
            tags,
            comments
        )
    }

    override suspend fun deleteMangaFromList(mangaId: Int): Response<Any> {
        return apiService.deleteMangaFromList(mangaId)
    }

    override suspend fun getMyMangaList(
        status: MalMangaStatus, sort: MalSortType, limit: Int, offset: Int
    ): Response<MalMyMangaListResponse> {
        return apiService.getMyMangaList(status.search, sort.search, limit, offset, fields)
    }

    override suspend fun getProfile(): Response<MalProfileResponse> {
        return apiService.getProfile()
    }

    override suspend fun getRecommendedAnime(
        limit: Int, offset: Int
    ): Response<MalMyAnimeListResponse> {
        return apiService.getRecommendedAnime(limit, offset, fields)
    }

    override suspend fun getAnimeRanking(
        rankingType: MalAnimeType, limit: Int, offset: Int
    ): Response<MalMyAnimeListResponse> {
        return apiService.getAnimeRanking(rankingType.search, limit, offset, fields)
    }

    override suspend fun getMangaRanking(
        rankingType: MalMangaType, limit: Int, offset: Int
    ): Response<MalMyMangaListResponse> {
        return apiService.getMangaRanking(rankingType.search, limit, offset, fields)
    }
}