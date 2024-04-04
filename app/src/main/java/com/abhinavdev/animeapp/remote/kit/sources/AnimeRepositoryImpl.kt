package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.AnimeRepository
import com.abhinavdev.animeapp.remote.model.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.model.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.model.anime.EpisodeResponse
import com.abhinavdev.animeapp.remote.model.anime.EpisodesResponse
import com.abhinavdev.animeapp.remote.model.anime.ImagesResponse
import com.abhinavdev.animeapp.remote.model.anime.OpEdThemesResponse
import com.abhinavdev.animeapp.remote.model.anime.SchedulesResponse
import com.abhinavdev.animeapp.remote.model.anime.StaffResponse
import com.abhinavdev.animeapp.remote.model.anime.StreamingResponse
import com.abhinavdev.animeapp.remote.model.anime.VideoEpisodesResponse
import com.abhinavdev.animeapp.remote.model.anime.VideosResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.model.common.ExternalResponse
import com.abhinavdev.animeapp.remote.model.common.ForumResponse
import com.abhinavdev.animeapp.remote.model.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.model.common.RecentAnimeRecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RelationsResponse
import com.abhinavdev.animeapp.remote.model.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.model.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.model.enums.AgeRating
import com.abhinavdev.animeapp.remote.model.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.model.enums.AnimeOrderBy
import com.abhinavdev.animeapp.remote.model.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.model.enums.AnimeType
import com.abhinavdev.animeapp.remote.model.enums.DayOfWeek
import com.abhinavdev.animeapp.remote.model.enums.ForumTopicType
import com.abhinavdev.animeapp.remote.model.enums.GenreType
import com.abhinavdev.animeapp.remote.model.enums.SortOrder
import retrofit2.Response

class AnimeRepositoryImpl : AnimeRepository {

    private val apiService = ApiClient.getApiService()

    override suspend fun getFullAnimeById(animeId: Int): Response<AnimeFullResponse> {
        return apiService.getFullAnimeById(animeId)
    }

    override suspend fun getAnimeById(animeId: Int): Response<AnimeFullResponse> {
        return apiService.getAnimeById(animeId)
    }

    override suspend fun getAnimeStaff(animeId: Int): Response<StaffResponse> {
        return apiService.getAnimeStaff(animeId)
    }

    override suspend fun getAnimeEpisodes(animeId: Int, pageNo: Int): Response<EpisodesResponse> {
        return apiService.getAnimeEpisodes(animeId, pageNo)
    }

    override suspend fun getAnimeEpisodeById(
        animeId: Int,
        episodeId: Int
    ): Response<EpisodeResponse> {
        return apiService.getAnimeEpisodeById(animeId, episodeId)
    }

    override suspend fun getAnimeNews(animeId: Int, pageNo: Int): Response<EpisodesResponse> {
        return apiService.getAnimeNews(animeId, pageNo)
    }

    override suspend fun getAnimeForums(
        animeId: Int,
        filter: ForumTopicType
    ): Response<ForumResponse> {
        return apiService.getAnimeForums(animeId, filter.search)
    }

    override suspend fun getAnimeVideos(animeId: Int): Response<VideosResponse> {
        return apiService.getAnimeVideos(animeId)
    }

    override suspend fun getAnimeVideoEpisodes(
        animeId: Int,
        pageNo: Int
    ): Response<VideoEpisodesResponse> {
        return apiService.getAnimeVideoEpisodes(animeId, pageNo)
    }

    override suspend fun getAnimeImages(animeId: Int): Response<ImagesResponse> {
        return apiService.getAnimeImages(animeId)
    }

    override suspend fun getAnimeStatistics(animeId: Int): Response<StatisticsResponse> {
        return apiService.getAnimeStatistics(animeId)
    }

    override suspend fun getAnimeMoreInfo(animeId: Int): Response<MoreInfoResponse> {
        return apiService.getAnimeMoreInfo(animeId)
    }

    override suspend fun getAnimeRecommendations(animeId: Int): Response<RecommendationsResponse> {
        return apiService.getAnimeRecommendations(animeId)
    }

    override suspend fun getAnimeReviews(
        animeId: Int, pageNo: Int, preliminary: Boolean, spoiler: Boolean
    ): Response<ReviewsResponse> {
        return apiService.getAnimeReviews(animeId, pageNo, preliminary, spoiler)
    }

    override suspend fun getAnimeRelations(animeId: Int): Response<RelationsResponse> {
        return apiService.getAnimeRelations(animeId)
    }

    override suspend fun getAnimeOpEdThemes(animeId: Int): Response<OpEdThemesResponse> {
        return apiService.getAnimeOpEdThemes(animeId)
    }

    override suspend fun getAnimeExternal(animeId: Int): Response<ExternalResponse> {
        return apiService.getAnimeExternal(animeId)
    }

    override suspend fun getAnimeStreaming(animeId: Int): Response<StreamingResponse> {
        return apiService.getAnimeStreaming(animeId)
    }

    override suspend fun getAnimeBySearch(
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
        endDate: String
    ): Response<AnimeSearchResponse> {
        return apiService.getAnimeBySearch(
            sfw,
            unapproved,
            page,
            limit,
            q,
            type.search,
            score,
            minScore,
            maxScore,
            status.search,
            rating.search,
            genres,
            genresExclude,
            orderBy.search,
            sort.search,
            letter,
            producers,
            startDate,
            endDate
        )
    }

    override suspend fun getRandomAnime(): Response<AnimeFullResponse> {
        return apiService.getRandomAnime()
    }

    override suspend fun getRecentAnimeRecommendations(page: Int): Response<RecentAnimeRecommendationsResponse> {
        return apiService.getRecentAnimeRecommendations(page)
    }

    override suspend fun getAnimeSchedules(
        filter: DayOfWeek,
        kids: Int,
        sfw: Int,
        unapproved: Boolean,
        page: Int,
        limit: Int
    ): Response<SchedulesResponse> {
        return apiService.getAnimeSchedules(filter.search, kids, sfw, unapproved, page, limit)
    }

    override suspend fun getTopAnime(
        type: AnimeType,
        filter: AnimeFilter,
        rating: AgeRating,
        sfw: Boolean,
        page: Int,
        limit: Int
    ): Response<AnimeSearchResponse> {
        return apiService.getTopAnime(type.search, filter.search, rating.search, sfw, page, limit)
    }

    override suspend fun getAnimeGenres(filter: GenreType): Response<ClubMembersResponse> {
        return apiService.getAnimeGenres(filter.search)
    }
}