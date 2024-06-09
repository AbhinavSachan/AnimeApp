package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.anime.EpisodeResponse
import com.abhinavdev.animeapp.remote.models.anime.EpisodesResponse
import com.abhinavdev.animeapp.remote.models.anime.ImagesResponse
import com.abhinavdev.animeapp.remote.models.anime.OpEdThemesResponse
import com.abhinavdev.animeapp.remote.models.anime.SchedulesResponse
import com.abhinavdev.animeapp.remote.models.anime.StaffResponse
import com.abhinavdev.animeapp.remote.models.anime.StreamingResponse
import com.abhinavdev.animeapp.remote.models.anime.VideoEpisodesResponse
import com.abhinavdev.animeapp.remote.models.anime.VideosResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.models.common.ExternalResponse
import com.abhinavdev.animeapp.remote.models.common.ForumResponse
import com.abhinavdev.animeapp.remote.models.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.models.common.RecentAnimeRecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.RelationsResponse
import com.abhinavdev.animeapp.remote.models.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.models.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.models.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.models.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.DayOfWeek
import com.abhinavdev.animeapp.remote.models.enums.ForumTopicType
import com.abhinavdev.animeapp.remote.models.enums.GenreType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
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
        query: String,
        type: AnimeType,
        score: Int?,
        minScore: Int?,
        maxScore: Int?,
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

    suspend fun getRandomAnime(): Response<AnimeFullResponse>

    suspend fun getRecentAnimeRecommendations(page: Int): Response<RecentAnimeRecommendationsResponse>

    suspend fun getAnimeSchedules(
        filter: DayOfWeek,
        kids: Int,
        sfw: Int,
        unapproved: Boolean,
        page: Int,
        limit: Int,
    ): Response<SchedulesResponse>

    suspend fun getTopAnime(
        type: AnimeType,
        filter: AnimeFilter,
        rating: AgeRating,
        sfw: Boolean,
        page: Int,
        limit: Int,
    ): Response<AnimeSearchResponse>

    suspend fun getAnimeGenres(
        filter: GenreType,
    ): Response<ClubMembersResponse>
}