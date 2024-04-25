package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.MangaRepository
import com.abhinavdev.animeapp.remote.models.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.models.common.ForumResponse
import com.abhinavdev.animeapp.remote.models.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.models.common.NewsResponse
import com.abhinavdev.animeapp.remote.models.common.RecentAnimeRecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.RelationsResponse
import com.abhinavdev.animeapp.remote.models.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.models.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.models.enums.ForumTopicType
import com.abhinavdev.animeapp.remote.models.enums.GenreType
import com.abhinavdev.animeapp.remote.models.enums.MangaFilter
import com.abhinavdev.animeapp.remote.models.enums.MangaOrderBy
import com.abhinavdev.animeapp.remote.models.enums.MangaStatus
import com.abhinavdev.animeapp.remote.models.enums.MangaType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import com.abhinavdev.animeapp.remote.models.manga.MangaCharacterResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaPicturesResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaSearchResponse
import retrofit2.Response

class MangaRepositoryImpl : MangaRepository {
    private val apiService = ApiClient.getJikanApiService()

    override suspend fun getMangaFullById(mangaId: Int): Response<MangaResponse> {
        return apiService.getMangaFullById(mangaId)
    }

    override suspend fun getMangaById(mangaId: Int): Response<MangaResponse> {
        return apiService.getMangaById(mangaId)
    }

    override suspend fun getMangaCharacterById(mangaId: Int): Response<MangaCharacterResponse> {
        return apiService.getMangaCharacterById(mangaId)
    }

    override suspend fun getMangaNews(mangaId: Int, page: Int): Response<NewsResponse> {
        return apiService.getMangaNews(mangaId, page)
    }

    override suspend fun getMangaForums(
        mangaId: Int, filter: ForumTopicType
    ): Response<ForumResponse> {
        return apiService.getMangaForums(mangaId, filter.search)
    }

    override suspend fun getMangaPictures(mangaId: Int): Response<MangaPicturesResponse> {
        return apiService.getMangaPictures(mangaId)
    }

    override suspend fun getMangaStatistics(mangaId: Int): Response<StatisticsResponse> {
        return apiService.getMangaStatistics(mangaId)
    }

    override suspend fun getMangaMoreInfo(mangaId: Int): Response<MoreInfoResponse> {
        return apiService.getMangaMoreInfo(mangaId)
    }

    override suspend fun getMangaRecommendations(mangaId: Int): Response<RecommendationsResponse> {
        return apiService.getMangaRecommendations(mangaId)
    }

    override suspend fun getMangaReviews(
        mangaId: Int, pageNo: Int, preliminary: Boolean, spoiler: Boolean
    ): Response<ReviewsResponse> {
        return apiService.getMangaReviews(mangaId, pageNo, preliminary, spoiler)
    }

    override suspend fun getMangaRelations(mangaId: Int): Response<RelationsResponse> {
        return apiService.getMangaRelations(mangaId)
    }

    override suspend fun getMangaBySearch(
        unapproved: Boolean,
        page: Int,
        limit: Int,
        query: String,
        type: MangaType,
        score: Int,
        minScore: Int,
        maxScore: Int,
        status: MangaStatus,
        sfw: Boolean,
        genres: Int,
        genresExclude: Int,
        orderBy: MangaOrderBy,
        sort: SortOrder,
        letter: Int,
        magazines: Int,
        startDate: Int,
        endDate: Int
    ): Response<MangaSearchResponse> {
        return apiService.getMangaBySearch(
            unapproved,
            page,
            limit,
            query,
            type.search,
            score,
            minScore,
            maxScore,
            status.search,
            sfw,
            genres,
            genresExclude,
            orderBy.search,
            sort.search,
            letter,
            magazines,
            startDate,
            endDate
        )
    }

    override suspend fun getRandomManga(): Response<MangaResponse> {
        return apiService.getRandomManga()
    }

    override suspend fun getRecentMangaRecommendations(page: Int): Response<RecentAnimeRecommendationsResponse> {
        return apiService.getRecentMangaRecommendations(page)
    }

    override suspend fun getTopManga(
        type: MangaType, filter: MangaFilter, page: Int, limit: Int
    ): Response<MangaSearchResponse> {
        return apiService.getTopManga(type.search, filter.search, page, limit)
    }

    override suspend fun getMangaGenres(filter: GenreType): Response<ClubMembersResponse> {
        return apiService.getMangaGenres(filter.search)
    }
}