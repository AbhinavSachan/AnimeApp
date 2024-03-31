package com.abhinavdev.animeapp.remote.kit

import com.abhinavdev.animeapp.remote.model.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.model.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.model.anime.CharacterResponse
import com.abhinavdev.animeapp.remote.model.anime.EpisodeResponse
import com.abhinavdev.animeapp.remote.model.anime.EpisodesResponse
import com.abhinavdev.animeapp.remote.model.anime.ImagesResponse
import com.abhinavdev.animeapp.remote.model.anime.OpEdThemesResponse
import com.abhinavdev.animeapp.remote.model.anime.SchedulesResponse
import com.abhinavdev.animeapp.remote.model.anime.StaffResponse
import com.abhinavdev.animeapp.remote.model.anime.StreamingResponse
import com.abhinavdev.animeapp.remote.model.anime.VideoEpisodesResponse
import com.abhinavdev.animeapp.remote.model.anime.VideosResponse
import com.abhinavdev.animeapp.remote.model.characters.CharacterAnimeResponse
import com.abhinavdev.animeapp.remote.model.characters.CharacterFullResponse
import com.abhinavdev.animeapp.remote.model.characters.CharacterMangaResponse
import com.abhinavdev.animeapp.remote.model.characters.CharacterPicturesResponse
import com.abhinavdev.animeapp.remote.model.characters.CharacterSearchResponse
import com.abhinavdev.animeapp.remote.model.characters.CharacterVoiceActorsResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubRelationsResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubSearchResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubStaffResponse
import com.abhinavdev.animeapp.remote.model.common.ExternalResponse
import com.abhinavdev.animeapp.remote.model.common.ForumResponse
import com.abhinavdev.animeapp.remote.model.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.model.common.NewsResponse
import com.abhinavdev.animeapp.remote.model.common.RecentAnimeRecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.model.common.RelationsResponse
import com.abhinavdev.animeapp.remote.model.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.model.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.model.enums.AgeRating
import com.abhinavdev.animeapp.remote.model.enums.AnimeFilter
import com.abhinavdev.animeapp.remote.model.enums.AnimeType
import com.abhinavdev.animeapp.remote.model.enums.CharacterOrderBy
import com.abhinavdev.animeapp.remote.model.enums.ClubCategory
import com.abhinavdev.animeapp.remote.model.enums.ClubOrderBy
import com.abhinavdev.animeapp.remote.model.enums.ClubType
import com.abhinavdev.animeapp.remote.model.enums.DayOfWeek
import com.abhinavdev.animeapp.remote.model.enums.GenreType
import com.abhinavdev.animeapp.remote.model.enums.MagazineOrderBy
import com.abhinavdev.animeapp.remote.model.enums.MangaFilter
import com.abhinavdev.animeapp.remote.model.enums.MangaOrderBy
import com.abhinavdev.animeapp.remote.model.enums.MangaStatus
import com.abhinavdev.animeapp.remote.model.enums.MangaType
import com.abhinavdev.animeapp.remote.model.enums.SortOrder
import com.abhinavdev.animeapp.remote.model.magazines.MagazinesResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaCharacterResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaPicturesResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaResponse
import com.abhinavdev.animeapp.remote.model.manga.MangaSearchResponse
import com.abhinavdev.animeapp.util.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * All api calls are defined in this interface
 *
 * https://docs.api.jikan.moe/
 */
interface ApiService {

    //region Anime

    /**
     * To get full anime data including
     *
     * relations: This field provides information about relations or connections to other anime entries.
     * theme: Contains information about the theme songs (openings and endings) of the anime.
     * external: Includes external links related to the anime.
     * streaming: Provides information about streaming platforms where the anime is available.
     */
    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.FULL)
    suspend fun getFullAnimeById(
        @Path("id") animeId: Int,
    ): Response<AnimeFullResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}")
    suspend fun getAnimeById(
        @Path("id") animeId: Int,
    ): Response<AnimeFullResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.STAFF)
    suspend fun getAnimeStaff(
        @Path("id") animeId: Int,
    ): Response<StaffResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.EPISODES)
    suspend fun getAnimeEpisodes(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
    ): Response<EpisodesResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.EPISODES + "/{episode}")
    suspend fun getAnimeEpisodeById(
        @Path("id") animeId: Int,
        @Path("episode") episodeId: Int,
    ): Response<EpisodeResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.NEWS)
    suspend fun getAnimeNews(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
    ): Response<EpisodesResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.FORUM)
    suspend fun getAnimeForums(
        @Path("id") animeId: Int,
        @Query("filter") forumFilterType: String,
    ): Response<ForumResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.VIDEOS)
    suspend fun getAnimeVideos(
        @Path("id") animeId: Int,
    ): Response<VideosResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.VIDEOS + "/" + Const.ApiKeywords.EPISODES)
    suspend fun getAnimeVideoEpisodes(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
    ): Response<VideoEpisodesResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.PICTURES)
    suspend fun getAnimeImages(
        @Path("id") animeId: Int,
    ): Response<ImagesResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.STATISTICS)
    suspend fun getAnimeStatistics(
        @Path("id") animeId: Int,
    ): Response<StatisticsResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.MORE_INFO)
    suspend fun getAnimeMoreInfo(
        @Path("id") animeId: Int,
    ): Response<MoreInfoResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.RECOMMENDATIONS)
    suspend fun getAnimeRecommendations(
        @Path("id") animeId: Int,
    ): Response<RecommendationsResponse>

    /**
     * @param preliminary Any reviews left during an ongoing anime/manga, those reviews are tagged as preliminary. NOTE: Preliminary reviews are not returned by default so if the entry is airing/publishing you need to add this otherwise you will get an empty list
     * @param spoiler Any reviews that are tagged as a spoiler. Spoiler reviews are not returned by default.
     */
    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.REVIEWS)
    suspend fun getAnimeReviews(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
        @Query("preliminary") preliminary: Boolean,
        @Query("spoiler") spoiler: Boolean,
    ): Response<ReviewsResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.RELATIONS)
    suspend fun getAnimeRelations(
        @Path("id") animeId: Int,
    ): Response<RelationsResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.THEMES)
    suspend fun getAnimeOpEdThemes(
        @Path("id") animeId: Int,
    ): Response<OpEdThemesResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.EXTERNAL)
    suspend fun getAnimeExternal(
        @Path("id") animeId: Int,
    ): Response<ExternalResponse>

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.STREAMING)
    suspend fun getAnimeStreaming(
        @Path("id") animeId: Int,
    ): Response<StreamingResponse>

    /**
     * @param sfw
     * 'Safe For Work'. This is a flag. When supplied it will filter out entries according to the SFW Policy. You do not need to pass a value to it. e.g usage: ?sfw
     *
     * @param unapproved
     * This is a flag. When supplied it will include entries which are unapproved. Unapproved entries on MyAnimeList are those that are user submitted and have not yet been approved by MAL to show up on other pages. They will have their own specifc pages and are often removed resulting in a 404 error. You do not need to pass a value to it. e.g usage: ?unapproved
     *
     * @param page Which page
     * @param limit Item limit per page
     * @param q Query
     * @param animeType
     * Enum: "tv" "movie" "ova" "special" "ona" "music" "cm" "pv" "tv_special"
     * Available AnimeTypes
     *
     * @param score
     * @param minScore
     * Set a minimum score for results.
     *
     * @param maxScore
     * Set a maximum score for results
     *
     * @param animeStatus
     * Enum: "airing" "complete" "upcoming"
     * Available Anime statuses
     *
     * @param ageRating
     * Enum: "g" "pg" "pg13" "r17" "r" "rx"
     * Available Anime audience ratings
     *
     * Ratings
     * G - All Ages
     * PG - Children
     * PG-13 - Teens 13 or older
     * R - 17+ (violence & profanity)
     * R+ - Mild Nudity
     * Rx - Hentai
     *
     * @param genres
     * Filter by genre(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
     *
     * @param genresExclude
     * Exclude genre(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
     *
     * @param animeOrderBy
     * Enum: "mal_id" "title" "start_date" "end_date" "episodes" "score" "scored_by" "rank" "popularity" "members" "favorites"
     * Available Anime order_by properties
     *
     * @param sortOrder
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter Return entries starting with the given letter
     *
     * @param producers Filter by producer(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
     *
     * @param startDate Filter by starting date. Format: YYYY-MM-DD. e.g 2022, 2005-05, 2005-01-01
     *
     * @param endDate Filter by ending date. Format: YYYY-MM-DD. e.g 2022, 2005-05, 2005-01-01
     */
    @GET(Const.ApiKeywords.ANIME)
    suspend fun getAnimeBySearch(
        @Query("sfw") sfw: Boolean,
        @Query("unapproved") unapproved: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") q: String,
        @Query("type") animeType: String,
        @Query("score") score: Int,
        @Query("min_score") minScore: Int,
        @Query("max_score") maxScore: Int,
        @Query("status") animeStatus: String,
        @Query("rating") ageRating: String,
        @Query("genres") genres: String,
        @Query("genres_exclude") genresExclude: String,
        @Query("order_by") animeOrderBy: String,
        @Query("sort") sortOrder: String,
        @Query("letter") letter: String,
        @Query("producers") producers: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): Response<AnimeSearchResponse>

    //endregion

    //region Character

    /**
     * includes additional fields for anime, manga, and voice acting roles
     */
    @GET(Const.ApiKeywords.CHARACTERS + "/{id}/" + Const.ApiKeywords.FULL)
    suspend fun getCharacterFullById(
        @Path("id") characterId: Int,
    ): Response<CharacterFullResponse>

    @GET(Const.ApiKeywords.CHARACTERS + "/{id}")
    suspend fun getCharacterById(
        @Path("id") characterId: Int,
    ): Response<CharacterFullResponse>

    @GET(Const.ApiKeywords.CHARACTERS + "/{id}/" + Const.ApiKeywords.ANIME)
    suspend fun getCharacterAnime(
        @Path("id") characterId: Int,
    ): Response<CharacterAnimeResponse>

    @GET(Const.ApiKeywords.CHARACTERS + "/{id}/" + Const.ApiKeywords.MANGA)
    suspend fun getCharacterManga(
        @Path("id") characterId: Int,
    ): Response<CharacterMangaResponse>

    @GET(Const.ApiKeywords.CHARACTERS + "/{id}/" + Const.ApiKeywords.VOICES)
    suspend fun getCharacterVoiceActors(
        @Path("id") characterId: Int,
    ): Response<CharacterVoiceActorsResponse>

    @GET(Const.ApiKeywords.CHARACTERS + "/{id}/" + Const.ApiKeywords.PICTURES)
    suspend fun getCharacterPictures(
        @Path("id") characterId: Int,
    ): Response<CharacterPicturesResponse>

    /**
     * @param page Page number
     * @param limit Limit per page
     * @param q Query
     * @param orderBy
     * Enum: "mal_id" "name" "favorites"
     * Available Character order_by properties
     *
     * @param sort
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter
     * Return entries starting with the given letter
     */
    @GET(Const.ApiKeywords.CHARACTERS)
    suspend fun getCharactersSearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") q: String,
        @Query("order_by") orderBy: CharacterOrderBy,
        @Query("sort") sort: SortOrder,
        @Query("letter") letter: String,
    ): Response<CharacterSearchResponse>

    //endregion

    //region Club

    @GET(Const.ApiKeywords.CLUBS + "/{id}")
    suspend fun getClubsById(
        @Path("id") clubId: Int,
    ): Response<ClubResponse>

    @GET(Const.ApiKeywords.CLUBS + "/{id}/" + Const.ApiKeywords.MEMBERS)
    suspend fun getClubMembers(
        @Path("id") clubId: Int,
        @Query("page") page: Int,
    ): Response<ClubMembersResponse>

    @GET(Const.ApiKeywords.CLUBS + "/{id}/" + Const.ApiKeywords.STAFF)
    suspend fun getClubStaff(
        @Path("id") clubId: Int,
    ): Response<ClubStaffResponse>

    @GET(Const.ApiKeywords.CLUBS + "/{id}/" + Const.ApiKeywords.RELATIONS)
    suspend fun getClubRelations(
        @Path("id") clubId: Int,
    ): Response<ClubRelationsResponse>

    /**
     * @param page page number]
     * @param limit Limit per page
     * @param q Query
     * @param type
     * Enum: "public" "private" "secret"
     * Club Search Query Type
     *
     * @param category
     * Enum: "anime" "manga" "actors_and_artists" "characters" "cities_and_neighborhoods" "companies" "conventions" "games" "japan" "music" "other" "schools"
     * Club Search Query Category
     *
     * @param orderBy
     * Enum: "mal_id" "name" "members_count" "created"
     * Club Search Query OrderBy
     *
     * @param sort
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter
     * Return entries starting with the given letter
     */
    @GET(Const.ApiKeywords.CLUBS)
    suspend fun getClubSearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") q: String,
        @Query("type") type: ClubType,
        @Query("category") category: ClubCategory,
        @Query("order_by") orderBy: ClubOrderBy,
        @Query("sort") sort: SortOrder,
        @Query("letter") letter: String,
    ): Response<ClubSearchResponse>

    //endregion

    //region Genre

    @GET(Const.ApiKeywords.GENRES + "/" + Const.ApiKeywords.ANIME)
    suspend fun getAnimeGenres(
        @Query("filter") filter: GenreType,
    ): Response<ClubMembersResponse>

    @GET(Const.ApiKeywords.GENRES + "/" + Const.ApiKeywords.MANGA)
    suspend fun getMangaGenres(
        @Query("filter") filter: GenreType,
    ): Response<ClubMembersResponse>

    //endregion

    //region Magazine
    /**
     * @param page
     * @param limit
     * @param q
     * @param orderBy
     * Enum: "mal_id" "name" "count"
     * Order by magazine data
     *
     * @param sort
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter
     * Return entries starting with the given letter
     */
    @GET(Const.ApiKeywords.MAGAZINES)
    suspend fun getMagazinesBySearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") q: String,
        @Query("order_by") orderBy: MagazineOrderBy,
        @Query("sort") sort: SortOrder,
        @Query("letter") letter: String,
    ): Response<MagazinesResponse>

    //endregion

    //region Manga
    /**
     * including
     * "relations": An array containing objects describing relations to other entries, such as related manga or anime.
     * "external": An array containing objects with external links related to the manga.
     */
    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.FULL)
    suspend fun getMangaFullById(
        @Path("id") mangaId: Int,
    ): Response<MangaResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}")
    suspend fun getMangaById(
        @Path("id") mangaId: Int,
    ): Response<MangaResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.CHARACTERS)
    suspend fun getMangaCharacterById(
        @Path("id") mangaId: Int,
    ): Response<MangaCharacterResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.NEWS)
    suspend fun getMangaNews(
        @Path("id") mangaId: Int,
        @Query("page") page: Int,
    ): Response<NewsResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.FORUM)
    suspend fun getMangaTopics(
        @Path("id") mangaId: Int,
        @Query("filter") forumFilterType: String,
    ): Response<ForumResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.PICTURES)
    suspend fun getMangaPictures(
        @Path("id") mangaId: Int,
    ): Response<MangaPicturesResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.STATISTICS)
    suspend fun getMangaStatistics(
        @Path("id") mangaId: Int,
    ): Response<StatisticsResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.MORE_INFO)
    suspend fun getMangaMoreInfo(
        @Path("id") mangaId: Int,
    ): Response<MoreInfoResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.RECOMMENDATIONS)
    suspend fun getMangaRecommendations(
        @Path("id") mangaId: Int,
    ): Response<RecommendationsResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.REVIEWS)
    suspend fun getMangaReviews(
        @Path("id") mangaId: Int,
        @Query("page") pageNo: Int,
        @Query("preliminary") preliminary: Boolean,
        @Query("spoiler") spoiler: Boolean,
    ): Response<ReviewsResponse>

    @GET(Const.ApiKeywords.MANGA + "/{id}/" + Const.ApiKeywords.RECOMMENDATIONS)
    suspend fun getMangaRelations(
        @Path("id") mangaId: Int,
    ): Response<RelationsResponse>

    /**
     *
     * @param unapproved
     * This is a flag. When supplied it will include entries which are unapproved. Unapproved entries on MyAnimeList are those that are user submitted and have not yet been approved by MAL to show up on other pages. They will have their own specifc pages and are often removed resulting in a 404 error. You do not need to pass a value to it. e.g usage: ?unapproved
     *
     * @param page
     * @param limit
     * @param q
     * @param type
     * Enum: "manga" "novel" "lightnovel" "oneshot" "doujin" "manhwa" "manhua"
     * Available Manga types
     *
     * @param score
     * @param minScore
     * Set a minimum score for results.
     *
     * @param maxScore
     * Set a maximum score for results
     *
     * @param status
     * Enum: "publishing" "complete" "hiatus" "discontinued" "upcoming"
     * Available Manga statuses
     *
     * @param sfw
     * Filter out Adult entries
     *
     * @param genres
     * Filter by genre(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
     *
     * @param genresExclude
     * Exclude genre(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
     *
     * @param orderBy
     * Enum: "mal_id" "title" "start_date" "end_date" "chapters" "volumes" "score" "scored_by" "rank" "popularity" "members" "favorites"
     * Available Manga order_by properties
     *
     * @param sort
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter
     * Return entries starting with the given letter
     *
     * @param magazines
     * Filter by magazine(s) IDs. Can pass multiple with a comma as a delimiter. e.g 1,2,3
     *
     * @param startDate
     * Filter by starting date. Format: YYYY-MM-DD. e.g 2022, 2005-05, 2005-01-01
     *
     * @param endDate
     * Filter by ending date. Format: YYYY-MM-DD. e.g 2022, 2005-05, 2005-01-01
     */
    @GET(Const.ApiKeywords.MANGA)
    suspend fun getMangaBySearch(
        @Path("unapproved") unapproved: Boolean,
        @Path("page") page: Int,
        @Path("limit") limit: Int,
        @Path("q") q: String,
        @Path("type") type: MangaType,
        @Path("score") score: Int,
        @Path("min_score") minScore: Int,
        @Path("max_score") maxScore: Int,
        @Path("status") status: MangaStatus,
        @Path("sfw") sfw: Boolean,
        @Path("genres") genres: Int,
        @Path("genres_exclude") genresExclude: Int,
        @Path("order_by") orderBy: MangaOrderBy,
        @Path("sort") sort: SortOrder,
        @Path("letter") letter: Int,
        @Path("magazines") magazines: Int,
        @Path("start_date") startDate: Int,
        @Path("end_date") endDate: Int,
    ): Response<MangaSearchResponse>

    //endregion

    //region Random

    @GET(Const.ApiKeywords.RANDOM + "/" + Const.ApiKeywords.ANIME)
    suspend fun getRandomAnime(): Response<AnimeFullResponse>

    @GET(Const.ApiKeywords.RANDOM + "/" + Const.ApiKeywords.MANGA)
    suspend fun getRandomManga(): Response<MangaResponse>

    @GET(Const.ApiKeywords.RANDOM + "/" + Const.ApiKeywords.CHARACTERS)
    suspend fun getRandomCharacters(): Response<CharacterResponse>

    //endregion

    //region Recommendations

    @GET(Const.ApiKeywords.RECOMMENDATIONS + "/" + Const.ApiKeywords.ANIME)
    suspend fun getRecentAnimeRecommendations(
        @Query("page") page: Int,
    ): Response<RecentAnimeRecommendationsResponse>

    @GET(Const.ApiKeywords.RECOMMENDATIONS + "/" + Const.ApiKeywords.MANGA)
    suspend fun getRecentMangaRecommendations(
        @Query("page") page: Int,
    ): Response<RecentAnimeRecommendationsResponse>

    //endregion

    /**
     * @param filter
     * Enum: "monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday" "unknown" "other"
     * Filter by day
     *
     * @param kids
     * Enum: "true" "false"
     * When supplied, it will filter entries with the Kids Genre Demographic. When supplied as kids=true, it will return only Kid entries and when supplied as kids=false, it will filter out any Kid entries. Defaults to false.
     *
     * @param sfw
     * Enum: "true" "false"
     * 'Safe For Work'. When supplied, it will filter entries with the Hentai Genre. When supplied as sfw=true, it will return only SFW entries and when supplied as sfw=false, it will filter out any Hentai entries. Defaults to false.
     *
     * @param unapproved
     * This is a flag. When supplied it will include entries which are unapproved. Unapproved entries on MyAnimeList are those that are user submitted and have not yet been approved by MAL to show up on other pages. They will have their own specifc pages and are often removed resulting in a 404 error. You do not need to pass a value to it. e.g usage: ?unapproved
     *
     * @param page
     * @param limit
     */
    @GET(Const.ApiKeywords.SCHEDULES)
    suspend fun getAnimeSchedules(
        @Query("filter") filter: DayOfWeek,
        @Query("kids") kids: Int,
        @Query("sfw") sfw: Int,
        @Query("unapproved") unapproved: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<SchedulesResponse>

    //region Top

    /**
     * @param type
     * Enum: "tv" "movie" "ova" "special" "ona" "music" "cm" "pv" "tv_special"
     * Available Anime types
     *
     * @param filter
     * Enum: "airing" "upcoming" "bypopularity" "favorite"
     * Top items filter types
     *
     * @param rating
     * Enum: "g" "pg" "pg13" "r17" "r" "rx"
     * Available Anime audience ratings
     *
     * Ratings
     *
     * G - All Ages
     * PG - Children
     * PG-13 - Teens 13 or older
     * R - 17+ (violence & profanity)
     * R+ - Mild Nudity
     * Rx - Hentai
     * @param sfw
     * Filter out Adult entries
     *
     * @param page
     * @param limit
     */
    @GET(Const.ApiKeywords.TOP + "/" + Const.ApiKeywords.ANIME)
    suspend fun getTopAnime(
        @Query("type") type: AnimeType,
        @Query("filter") filter: AnimeFilter,
        @Query("rating") rating: AgeRating,
        @Query("sfw") sfw: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<AnimeSearchResponse>

    /**
     * @param type
     * Enum: "tv" "movie" "ova" "special" "ona" "music" "cm" "pv" "tv_special"
     * Available Anime types
     *
     * @param filter
     * Enum: "publishing" "upcoming" "bypopularity" "favorite"
     * Top items filter types
     *
     * @param rating
     * Enum: "g" "pg" "pg13" "r17" "r" "rx"
     * Available Anime audience ratings
     *
     * Ratings
     *
     * G - All Ages
     * PG - Children
     * PG-13 - Teens 13 or older
     * R - 17+ (violence & profanity)
     * R+ - Mild Nudity
     * Rx - Hentai
     * @param sfw
     * Filter out Adult entries
     *
     * @param page
     * @param limit
     */
    @GET(Const.ApiKeywords.TOP + "/" + Const.ApiKeywords.ANIME)
    suspend fun getTopManga(
        @Query("type") type: MangaType,
        @Query("filter") filter: MangaFilter,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<MangaSearchResponse>

    //endregion

    //region Watch

    //endregion
}