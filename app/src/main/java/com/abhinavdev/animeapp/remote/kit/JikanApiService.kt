package com.abhinavdev.animeapp.remote.kit

import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.remote.models.anime.AnimeSearchResponse
import com.abhinavdev.animeapp.remote.models.anime.CharacterResponse
import com.abhinavdev.animeapp.remote.models.anime.EpisodeResponse
import com.abhinavdev.animeapp.remote.models.anime.EpisodesResponse
import com.abhinavdev.animeapp.remote.models.anime.ImagesResponse
import com.abhinavdev.animeapp.remote.models.anime.OpEdThemesResponse
import com.abhinavdev.animeapp.remote.models.anime.SchedulesResponse
import com.abhinavdev.animeapp.remote.models.anime.StaffResponse
import com.abhinavdev.animeapp.remote.models.anime.StreamingResponse
import com.abhinavdev.animeapp.remote.models.anime.VideoEpisodesResponse
import com.abhinavdev.animeapp.remote.models.anime.VideosResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterAnimeResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterFullResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterMangaResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterPicturesResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterSearchResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterVoiceActorsResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubRelationsResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubSearchResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubStaffResponse
import com.abhinavdev.animeapp.remote.models.common.ExternalResponse
import com.abhinavdev.animeapp.remote.models.common.ForumResponse
import com.abhinavdev.animeapp.remote.models.common.MoreInfoResponse
import com.abhinavdev.animeapp.remote.models.common.NewsResponse
import com.abhinavdev.animeapp.remote.models.common.RecentAnimeRecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.RecommendationsResponse
import com.abhinavdev.animeapp.remote.models.common.RelationsResponse
import com.abhinavdev.animeapp.remote.models.common.ReviewsResponse
import com.abhinavdev.animeapp.remote.models.common.StatisticsResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaCharacterResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaPicturesResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaResponse
import com.abhinavdev.animeapp.remote.models.manga.MangaSearchResponse
import com.abhinavdev.animeapp.remote.models.users.UserByIdResponse
import com.abhinavdev.animeapp.remote.models.users.UserFullProfileResponse
import com.abhinavdev.animeapp.remote.models.users.UsersSearchResponse
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
interface JikanApiService {

    //region Anime

    /**
     * To get full anime data including
     *
     * relations: This field provides information about relations or connections to other anime entries.
     * theme: Contains information about the theme songs (openings and endings) of the anime.
     * external: Includes external links related to the anime.
     * streaming: Provides information about streaming platforms where the anime is available.
     */
    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.FULL)
    suspend fun getFullAnimeById(
        @Path("id") animeId: Int,
    ): Response<AnimeFullResponse>

    @GET(Const.Jikan.ANIME + "/{id}")
    suspend fun getAnimeById(
        @Path("id") animeId: Int,
    ): Response<AnimeFullResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.STAFF)
    suspend fun getAnimeStaff(
        @Path("id") animeId: Int,
    ): Response<StaffResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.EPISODES)
    suspend fun getAnimeEpisodes(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
    ): Response<EpisodesResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.EPISODES + "/{episode}")
    suspend fun getAnimeEpisodeById(
        @Path("id") animeId: Int,
        @Path("episode") episodeId: Int,
    ): Response<EpisodeResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.NEWS)
    suspend fun getAnimeNews(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
    ): Response<EpisodesResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.FORUM)
    suspend fun getAnimeForums(
        @Path("id") animeId: Int,
        @Query("filter") forumFilterType: String,
    ): Response<ForumResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.VIDEOS)
    suspend fun getAnimeVideos(
        @Path("id") animeId: Int,
    ): Response<VideosResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.VIDEOS + "/" + Const.Jikan.EPISODES)
    suspend fun getAnimeVideoEpisodes(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
    ): Response<VideoEpisodesResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.PICTURES)
    suspend fun getAnimeImages(
        @Path("id") animeId: Int,
    ): Response<ImagesResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.STATISTICS)
    suspend fun getAnimeStatistics(
        @Path("id") animeId: Int,
    ): Response<StatisticsResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.MORE_INFO)
    suspend fun getAnimeMoreInfo(
        @Path("id") animeId: Int,
    ): Response<MoreInfoResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.RECOMMENDATIONS)
    suspend fun getAnimeRecommendations(
        @Path("id") animeId: Int,
    ): Response<RecommendationsResponse>

    /**
     * @param preliminary Any reviews left during an ongoing anime/manga, those reviews are tagged as preliminary. NOTE: Preliminary reviews are not returned by default so if the entry is airing/publishing you need to add this otherwise you will get an empty list
     * @param spoiler Any reviews that are tagged as a spoiler. Spoiler reviews are not returned by default.
     */
    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.REVIEWS)
    suspend fun getAnimeReviews(
        @Path("id") animeId: Int,
        @Query("page") pageNo: Int,
        @Query("preliminary") preliminary: Boolean,
        @Query("spoiler") spoiler: Boolean,
    ): Response<ReviewsResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.RELATIONS)
    suspend fun getAnimeRelations(
        @Path("id") animeId: Int,
    ): Response<RelationsResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.THEMES)
    suspend fun getAnimeOpEdThemes(
        @Path("id") animeId: Int,
    ): Response<OpEdThemesResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.EXTERNAL)
    suspend fun getAnimeExternal(
        @Path("id") animeId: Int,
    ): Response<ExternalResponse>

    @GET(Const.Jikan.ANIME + "/{id}/" + Const.Jikan.STREAMING)
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
     * @param query Query
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
    @GET(Const.Jikan.ANIME)
    suspend fun getAnimeBySearch(
        @Query("sfw") sfw: Boolean,
        @Query("unapproved") unapproved: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") query: String,
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
    @GET(Const.Jikan.CHARACTERS + "/{id}/" + Const.Jikan.FULL)
    suspend fun getCharacterFullById(
        @Path("id") characterId: Int,
    ): Response<CharacterFullResponse>

    @GET(Const.Jikan.CHARACTERS + "/{id}")
    suspend fun getCharacterById(
        @Path("id") characterId: Int,
    ): Response<CharacterFullResponse>

    @GET(Const.Jikan.CHARACTERS + "/{id}/" + Const.Jikan.ANIME)
    suspend fun getCharacterAnime(
        @Path("id") characterId: Int,
    ): Response<CharacterAnimeResponse>

    @GET(Const.Jikan.CHARACTERS + "/{id}/" + Const.Jikan.MANGA)
    suspend fun getCharacterManga(
        @Path("id") characterId: Int,
    ): Response<CharacterMangaResponse>

    @GET(Const.Jikan.CHARACTERS + "/{id}/" + Const.Jikan.VOICES)
    suspend fun getCharacterVoiceActors(
        @Path("id") characterId: Int,
    ): Response<CharacterVoiceActorsResponse>

    @GET(Const.Jikan.CHARACTERS + "/{id}/" + Const.Jikan.PICTURES)
    suspend fun getCharacterPictures(
        @Path("id") characterId: Int,
    ): Response<CharacterPicturesResponse>

    /**
     * @param page Page number
     * @param limit Limit per page
     * @param query Query
     * @param characterOrderBy
     * Enum: "mal_id" "name" "favorites"
     * Available Character order_by properties
     *
     * @param sortOrder
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter
     * Return entries starting with the given letter
     */
    @GET(Const.Jikan.CHARACTERS)
    suspend fun getCharactersSearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") query: String,
        @Query("order_by") characterOrderBy: String,
        @Query("sort") sortOrder: String,
        @Query("letter") letter: String,
    ): Response<CharacterSearchResponse>

    //endregion

    //region Club

    @GET(Const.Jikan.CLUBS + "/{id}")
    suspend fun getClubsById(
        @Path("id") clubId: Int,
    ): Response<ClubResponse>

    @GET(Const.Jikan.CLUBS + "/{id}/" + Const.Jikan.MEMBERS)
    suspend fun getClubMembers(
        @Path("id") clubId: Int,
        @Query("page") page: Int,
    ): Response<ClubMembersResponse>

    @GET(Const.Jikan.CLUBS + "/{id}/" + Const.Jikan.STAFF)
    suspend fun getClubStaff(
        @Path("id") clubId: Int,
    ): Response<ClubStaffResponse>

    @GET(Const.Jikan.CLUBS + "/{id}/" + Const.Jikan.RELATIONS)
    suspend fun getClubRelations(
        @Path("id") clubId: Int,
    ): Response<ClubRelationsResponse>

    /**
     * @param page page number]
     * @param limit Limit per page
     * @param query Query
     * @param clubType
     * Enum: "public" "private" "secret"
     * Club Search Query Type
     *
     * @param clubCategory
     * Enum: "anime" "manga" "actors_and_artists" "characters" "cities_and_neighborhoods" "companies" "conventions" "games" "japan" "music" "other" "schools"
     * Club Search Query Category
     *
     * @param clubOrderBy
     * Enum: "mal_id" "name" "members_count" "created"
     * Club Search Query OrderBy
     *
     * @param sortOrder
     * Enum: "desc" "asc"
     * Search query sort direction
     *
     * @param letter
     * Return entries starting with the given letter
     */
    @GET(Const.Jikan.CLUBS)
    suspend fun getClubSearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") query: String,
        @Query("type") clubType: String,
        @Query("category") clubCategory: String,
        @Query("order_by") clubOrderBy: String,
        @Query("sort") sortOrder: String,
        @Query("letter") letter: String,
    ): Response<ClubSearchResponse>

    //endregion

    //region Genre

    @GET(Const.Jikan.GENRES + "/" + Const.Jikan.ANIME)
    suspend fun getAnimeGenres(
        @Query("filter") genreType: String,
    ): Response<ClubMembersResponse>

    @GET(Const.Jikan.GENRES + "/" + Const.Jikan.MANGA)
    suspend fun getMangaGenres(
        @Query("filter") genreType: String,
    ): Response<ClubMembersResponse>

    //endregion

    //region Manga
    /**
     * including
     * "relations": An array containing objects describing relations to other entries, such as related manga or anime.
     * "external": An array containing objects with external links related to the manga.
     */
    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.FULL)
    suspend fun getMangaFullById(
        @Path("id") mangaId: Int,
    ): Response<MangaResponse>

    @GET(Const.Jikan.MANGA + "/{id}")
    suspend fun getMangaById(
        @Path("id") mangaId: Int,
    ): Response<MangaResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.CHARACTERS)
    suspend fun getMangaCharacterById(
        @Path("id") mangaId: Int,
    ): Response<MangaCharacterResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.NEWS)
    suspend fun getMangaNews(
        @Path("id") mangaId: Int,
        @Query("page") page: Int,
    ): Response<NewsResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.FORUM)
    suspend fun getMangaForums(
        @Path("id") mangaId: Int,
        @Query("filter") forumFilterType: String,
    ): Response<ForumResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.PICTURES)
    suspend fun getMangaPictures(
        @Path("id") mangaId: Int,
    ): Response<MangaPicturesResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.STATISTICS)
    suspend fun getMangaStatistics(
        @Path("id") mangaId: Int,
    ): Response<StatisticsResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.MORE_INFO)
    suspend fun getMangaMoreInfo(
        @Path("id") mangaId: Int,
    ): Response<MoreInfoResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.RECOMMENDATIONS)
    suspend fun getMangaRecommendations(
        @Path("id") mangaId: Int,
    ): Response<RecommendationsResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.REVIEWS)
    suspend fun getMangaReviews(
        @Path("id") mangaId: Int,
        @Query("page") pageNo: Int,
        @Query("preliminary") preliminary: Boolean,
        @Query("spoiler") spoiler: Boolean,
    ): Response<ReviewsResponse>

    @GET(Const.Jikan.MANGA + "/{id}/" + Const.Jikan.RECOMMENDATIONS)
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
     * @param query
     * @param mangaType
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
     * @param mangaStatus
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
     * @param mangaOrderBy
     * Enum: "mal_id" "title" "start_date" "end_date" "chapters" "volumes" "score" "scored_by" "rank" "popularity" "members" "favorites"
     * Available Manga order_by properties
     *
     * @param sortOrder
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
    @GET(Const.Jikan.MANGA)
    suspend fun getMangaBySearch(
        @Path("unapproved") unapproved: Boolean,
        @Path("page") page: Int,
        @Path("limit") limit: Int,
        @Path("q") query: String,
        @Path("type") mangaType: String,
        @Path("score") score: Int,
        @Path("min_score") minScore: Int,
        @Path("max_score") maxScore: Int,
        @Path("status") mangaStatus: String,
        @Path("sfw") sfw: Boolean,
        @Path("genres") genres: Int,
        @Path("genres_exclude") genresExclude: Int,
        @Path("order_by") mangaOrderBy: String,
        @Path("sort") sortOrder: String,
        @Path("letter") letter: Int,
        @Path("magazines") magazines: Int,
        @Path("start_date") startDate: Int,
        @Path("end_date") endDate: Int,
    ): Response<MangaSearchResponse>

    //endregion

    //region Random

    @GET(Const.Jikan.RANDOM + "/" + Const.Jikan.ANIME)
    suspend fun getRandomAnime(): Response<AnimeFullResponse>

    @GET(Const.Jikan.RANDOM + "/" + Const.Jikan.MANGA)
    suspend fun getRandomManga(): Response<MangaResponse>

    @GET(Const.Jikan.RANDOM + "/" + Const.Jikan.CHARACTERS)
    suspend fun getRandomCharacters(): Response<CharacterResponse>

    //endregion

    //region Recommendations

    @GET(Const.Jikan.RECOMMENDATIONS + "/" + Const.Jikan.ANIME)
    suspend fun getRecentAnimeRecommendations(
        @Query("page") page: Int,
    ): Response<RecentAnimeRecommendationsResponse>

    @GET(Const.Jikan.RECOMMENDATIONS + "/" + Const.Jikan.MANGA)
    suspend fun getRecentMangaRecommendations(
        @Query("page") page: Int,
    ): Response<RecentAnimeRecommendationsResponse>

    //endregion

    /**
     * @param dayOfWeek
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
    @GET(Const.Jikan.SCHEDULES)
    suspend fun getAnimeSchedules(
        @Query("filter") dayOfWeek: String,
        @Query("kids") kids: Int,
        @Query("sfw") sfw: Int,
        @Query("unapproved") unapproved: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<SchedulesResponse>

    //region Top

    /**
     * @param animeType
     * Enum: "tv" "movie" "ova" "special" "ona" "music" "cm" "pv" "tv_special"
     * Available Anime types
     *
     * @param animeFilter
     * Enum: "airing" "upcoming" "bypopularity" "favorite"
     * Top items filter types
     *
     * @param ageRating
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
    @GET(Const.Jikan.TOP + "/" + Const.Jikan.ANIME)
    suspend fun getTopAnime(
        @Query("type") animeType: String,
        @Query("filter") animeFilter: String,
        @Query("rating") ageRating: String,
        @Query("sfw") sfw: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<AnimeSearchResponse>

    /**
     * @param mangaType
     * Enum: "manga" "novel" "lightnovel" "oneshot" "doujin" "manhwa" "manhua"
     * Available Manga types
     *
     * @param mangaFilter
     * Enum: "publishing" "upcoming" "bypopularity" "favorite"
     * Top items filter types
     *
     * @param page
     * @param limit
     */
    @GET(Const.Jikan.TOP + "/" + Const.Jikan.MANGA)
    suspend fun getTopManga(
        @Query("type") mangaType: String,
        @Query("filter") mangaFilter: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<MangaSearchResponse>

    //endregion

    //region Users

    @GET(Const.Jikan.USERS + "/" + Const.Jikan.USER_BY_ID + "/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int,
    ): Response<UserByIdResponse>

    @GET(Const.Jikan.USERS + "/{username}/" + Const.Jikan.FULL)
    suspend fun getUserFullProfile(
        @Path("username") userName: String,
    ): Response<UserFullProfileResponse>

    /**
     * @param page
     * @param limit
     * @param query
     * @param gender
     * Enum: "any" "male" "female" "nonbinary" (UserGender)
     * Users Search Query Gender.
     *
     * @param location
     * @param maxAge
     * @param minAge
     */
    @GET(Const.Jikan.USERS)
    suspend fun getUsersSearch(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("q") query: String,
        @Query("gender") gender: String,
        @Query("location") location: String,
        @Query("maxAge") maxAge: Int,
        @Query("minAge") minAge: Int,
    ): Response<UsersSearchResponse>

    //endregion
}