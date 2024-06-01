package com.abhinavdev.animeapp.remote.kit.repository

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
import retrofit2.http.Query

interface MangaRepository {
    suspend fun getMangaFullById(mangaId: Int): Response<MangaResponse>

    suspend fun getMangaById(mangaId: Int): Response<MangaResponse>

    suspend fun getMangaCharacterById(mangaId: Int): Response<MangaCharacterResponse>

    suspend fun getMangaNews(
        mangaId: Int,
        page: Int,
    ): Response<NewsResponse>

    suspend fun getMangaForums(
        mangaId: Int,
        filter: ForumTopicType,
    ): Response<ForumResponse>

    suspend fun getMangaPictures(mangaId: Int): Response<MangaPicturesResponse>

    suspend fun getMangaStatistics(mangaId: Int): Response<StatisticsResponse>

    suspend fun getMangaMoreInfo(mangaId: Int): Response<MoreInfoResponse>

    suspend fun getMangaRecommendations(mangaId: Int): Response<RecommendationsResponse>

    suspend fun getMangaReviews(
        mangaId: Int,
        pageNo: Int,
        preliminary: Boolean,
        spoiler: Boolean,
    ): Response<ReviewsResponse>

    suspend fun getMangaRelations(mangaId: Int): Response<RelationsResponse>

    suspend fun getMangaBySearch(
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
        endDate: Int,
    ): Response<MangaSearchResponse>

    suspend fun getRandomManga(): Response<MangaResponse>

    suspend fun getRecentMangaRecommendations(page: Int): Response<RecentAnimeRecommendationsResponse>

    suspend fun getTopManga(
        type: MangaType,
        filter: MangaFilter,
        sfw: Boolean,
        page: Int,
        limit: Int,
    ): Response<MangaSearchResponse>

    suspend fun getMangaGenres(
        @Query("filter") filter: GenreType,
    ): Response<ClubMembersResponse>
}