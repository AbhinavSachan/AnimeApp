package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.model.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.model.common.ForumResponse
import com.abhinavdev.animeapp.remote.model.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.model.common.NewsResponse
import com.abhinavdev.animeapp.remote.model.common.RecentAnimeRecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RelationsResponse
import com.abhinavdev.animeapp.remote.model.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.model.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.model.enums.ForumTopicType
import com.abhinavdev.animeapp.remote.model.enums.GenreType
import com.abhinavdev.animeapp.remote.model.enums.MangaFilter
import com.abhinavdev.animeapp.remote.model.enums.MangaOrderBy
import com.abhinavdev.animeapp.remote.model.enums.MangaStatus
import com.abhinavdev.animeapp.remote.model.enums.MangaType
import com.abhinavdev.animeapp.remote.model.enums.SortOrder
import com.abhinavdev.animeapp.remote.model.manga.MangaCharacterResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaPicturesResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaSearchResponse
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
        q: String,
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
        page: Int,
        limit: Int,
    ): Response<MangaSearchResponse>

    suspend fun getMangaGenres(
        @Query("filter") filter: GenreType,
    ): Response<ClubMembersResponse>
}