package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.model.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.model.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.model.anime.EpisodeResponse
import com.abhinavdev.animeapp.remote.model.anime.EpisodesResponse
import com.abhinavdev.animeapp.remote.model.anime.ImagesResponse
import com.abhinavdev.animeapp.remote.model.anime.OpEdThemesResponse
import com.abhinavdev.animeapp.remote.model.anime.StaffResponse
import com.abhinavdev.animeapp.remote.model.anime.StreamingResponse
import com.abhinavdev.animeapp.remote.model.anime.VideoEpisodesResponse
import com.abhinavdev.animeapp.remote.model.anime.VideosResponse
import com.abhinavdev.animeapp.remote.model.common.ExternalResponse
import com.abhinavdev.animeapp.remote.model.common.ForumResponse
import com.abhinavdev.animeapp.remote.model.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.model.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RelationsResponse
import com.abhinavdev.animeapp.remote.model.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.model.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.model.enums.AgeRating
import com.abhinavdev.animeapp.remote.model.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.model.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.model.enums.AnimeType
import com.abhinavdev.animeapp.remote.model.enums.ForumTopicType
import com.abhinavdev.animeapp.remote.model.enums.SortOrder
import retrofit2.Response

interface AnimeRepository {
    suspend fun getFullAnimeById(
        animeId: Int,
    ): Response<AnimeFullResponse>

    suspend fun getAnimeById(
        animeId: Int,
    ): Response<AnimeFullResponse>

    suspend fun getAnimeStaff(
        animeId: Int,
    ): Response<StaffResponse>

    suspend fun getAnimeEpisodes(
        animeId: Int,
        pageNo: Int,
    ): Response<EpisodesResponse>

    suspend fun getAnimeEpisodeById(
        animeId: Int,
        episodeId: Int,
    ): Response<EpisodeResponse>

    suspend fun getAnimeNews(
        animeId: Int,
        pageNo: Int,
    ): Response<EpisodesResponse>

    suspend fun getAnimeForums(
        animeId: Int,
        filter: ForumTopicType,
    ): Response<ForumResponse>

    suspend fun getAnimeVideos(
        animeId: Int,
    ): Response<VideosResponse>

    suspend fun getAnimeVideoEpisodes(
        animeId: Int,
        pageNo: Int,
    ): Response<VideoEpisodesResponse>

    suspend fun getAnimeImages(
        animeId: Int,
    ): Response<ImagesResponse>

    suspend fun getAnimeStatistics(
        animeId: Int,
    ): Response<StatisticsResponse>

    suspend fun getAnimeMoreInfo(
        animeId: Int,
    ): Response<MoreInfoResponse>

    suspend fun getAnimeRecommendations(
        animeId: Int,
    ): Response<RecommendationsResponse>

    suspend fun getAnimeReviews(
        animeId: Int,
        pageNo: Int,
        preliminary: Boolean,
        spoiler: Boolean,
    ): Response<ReviewsResponse>

    suspend fun getAnimeRelations(
        animeId: Int,
    ): Response<RelationsResponse>

    suspend fun getAnimeOpEdThemes(
        animeId: Int,
    ): Response<OpEdThemesResponse>

    suspend fun getAnimeExternal(
        animeId: Int,
    ): Response<ExternalResponse>

    suspend fun getAnimeStreaming(
        animeId: Int,
    ): Response<StreamingResponse>

    suspend fun getAnimeBySearch(
        sfw: Boolean,
        unapproved: Boolean,
        page: Int,
        limit: Int,
        q: String,
        type: AnimeType,
        score: Int,
        minScore: Int,
        maxScore: Int,
        status: AnimeStatus,
        rating: AgeRating,
        genres: String,
        genresExclude: String,
        orderBy: AnimeOrderBy,
        sort: SortOrder,
        letter: String,
        producers: String,
        startDate: String,
        endDate: String,
    ): Response<AnimeSearchResponse>

}