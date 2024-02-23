package com.abhinavdev.animeapp.remote.connection

import com.abhinavdev.animeapp.remote.models.anime.Anime
import com.abhinavdev.animeapp.remote.models.anime.AnimeCharactersStaff
import com.abhinavdev.animeapp.remote.models.anime.AnimeEpisodes
import com.abhinavdev.animeapp.remote.models.anime.AnimeForum
import com.abhinavdev.animeapp.remote.models.anime.AnimeMoreInfo
import com.abhinavdev.animeapp.remote.models.anime.AnimeNews
import com.abhinavdev.animeapp.remote.models.anime.AnimePictures
import com.abhinavdev.animeapp.remote.models.anime.AnimeRecommendations
import com.abhinavdev.animeapp.remote.models.anime.AnimeReviews
import com.abhinavdev.animeapp.remote.models.anime.AnimeStats
import com.abhinavdev.animeapp.remote.models.anime.AnimeUserUpdates
import com.abhinavdev.animeapp.remote.models.anime.AnimeVideos
import com.abhinavdev.animeapp.remote.models.anime.MangaCharacters
import com.abhinavdev.animeapp.remote.models.character.Character
import com.abhinavdev.animeapp.remote.models.character.CharacterPictures
import com.abhinavdev.animeapp.remote.models.manga.Manga
import com.abhinavdev.animeapp.remote.models.manga.MangaForum
import com.abhinavdev.animeapp.remote.models.manga.MangaMoreInfo
import com.abhinavdev.animeapp.remote.models.manga.MangaNews
import com.abhinavdev.animeapp.remote.models.manga.MangaPictures
import com.abhinavdev.animeapp.remote.models.manga.MangaRecommendations
import com.abhinavdev.animeapp.remote.models.manga.MangaReviews
import com.abhinavdev.animeapp.remote.models.manga.MangaStats
import com.abhinavdev.animeapp.remote.models.manga.MangaUserUpdates
import com.abhinavdev.animeapp.remote.models.person.Person
import com.abhinavdev.animeapp.remote.models.person.PersonPictures
import com.abhinavdev.animeapp.remote.models.schedule.Schedule
import com.abhinavdev.animeapp.remote.models.search.AnimeSearchResult
import com.abhinavdev.animeapp.remote.models.search.CharacterSearchResult
import com.abhinavdev.animeapp.remote.models.search.MangaSearchResult
import com.abhinavdev.animeapp.remote.models.search.PeopleSearchResult
import com.abhinavdev.animeapp.remote.models.season.Season
import com.abhinavdev.animeapp.remote.models.top.TopAnime
import com.abhinavdev.animeapp.remote.models.top.TopCharacters
import com.abhinavdev.animeapp.remote.models.top.TopManga
import com.abhinavdev.animeapp.remote.models.top.TopPeople
import com.abhinavdev.animeapp.remote.models.user.User
import com.abhinavdev.animeapp.remote.models.user.UserAnimeList
import com.abhinavdev.animeapp.remote.models.user.UserFriends
import com.abhinavdev.animeapp.remote.models.user.UserHistory
import com.abhinavdev.animeapp.remote.models.user.UserMangaList
import moe.ganen.jikankt.models.season.SeasonArchives
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * All api calls are defined in this interface
 */
interface ApiService {

    //region Anime

    /**
     * Retrieves details of an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return Anime with the given MyAnimeList id.
     */
    @GET("anime/{id}")
    suspend fun getAnime(@Path("id") id: Int): Response<Anime>

    /**
     * Retrieves characters and staff information for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return List of characters and staff of the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/characters_staff")
    suspend fun getAnimeCharactersStaff(@Path("id") id: Int): Response<AnimeCharactersStaff>

    /**
     * Retrieves episodes for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @param page Optional, default is 1. Index of the page; each page contains 50 episode entities.
     * @return List of episodes of the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/episodes/{page}")
    suspend fun getAnimeEpisodes(@Path("id") id: Int, @Path("page") page: Int? = 1): Response<AnimeEpisodes>

    /**
     * Retrieves news articles related to an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return List of articles related to the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/news")
    suspend fun getAnimeNews(@Path("id") id: Int): Response<AnimeNews>

    /**
     * Retrieves pictures for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return List of pictures related to the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/pictures")
    suspend fun getAnimePictures(@Path("id") id: Int): Response<AnimePictures>

    /**
     * Retrieves videos for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return List of videos related to the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/videos")
    suspend fun getAnimeVideos(@Path("id") id: Int): Response<AnimeVideos>

    /**
     * Retrieves statistics for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return Statistics related to the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/stats")
    suspend fun getAnimeStats(@Path("id") id: Int): Response<AnimeStats>

    /**
     * Retrieves forum topics for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return List of topics related to the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/forum")
    suspend fun getAnimeForum(@Path("id") id: Int): Response<AnimeForum>

    /**
     * Retrieves additional information for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return Additional information about the anime in string format, if any.
     */
    @GET("anime/{id}/moreinfo")
    suspend fun getAnimeMoreInfo(@Path("id") id: Int): Response<AnimeMoreInfo>

    /**
     * Retrieves reviews for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @param page Optional, default is 1. Index of the page; each page contains 20 items.
     * @return List of reviews of the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/reviews/{page}")
    suspend fun getAnimeReviews(@Path("id") id: Int, @Path("page") page: Int? = 1): Response<AnimeReviews>

    /**
     * Retrieves recommendations for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @return List of recommendations for the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/recommendations")
    suspend fun getAnimeRecommendations(@Path("id") id: Int): Response<AnimeRecommendations>

    /**
     * Retrieves user updates for an anime by its MyAnimeList id.
     * @param id MyAnimeList id of the anime.
     * @param page Optional, default is 1. Index of the page; each page contains 75 items.
     * @return List of user updates for the anime with the given MyAnimeList id.
     */
    @GET("anime/{id}/userupdates/{page}")
    suspend fun getAnimeUserUpdates(
        @Path("id") id: Int,
        @Path("page") page: Int? = 1
    ): Response<AnimeUserUpdates>

    //endregion

    //region Manga

    /**
     * Retrieves details of a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return Manga with the given MyAnimeList id.
     */
    @GET("manga/{id}")
    suspend fun getManga(@Path("id") id: Int): Response<Manga>

    /**
     * Retrieves characters for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return List of characters and staff of the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/characters")
    suspend fun getMangaCharacters(@Path("id") id: Int): Response<MangaCharacters>

    /**
     * Retrieves news articles related to a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return List of articles related to the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/news")
    suspend fun getMangaNews(@Path("id") id: Int): Response<MangaNews>

    /**
     * Retrieves pictures for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return List of pictures related to the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/pictures")
    suspend fun getMangaPictures(@Path("id") id: Int): Response<MangaPictures>

    /**
     * Retrieves statistics for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return Statistics related to the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/stats")
    suspend fun getMangaStats(@Path("id") id: Int): Response<MangaStats>

    /**
     * Retrieves forum topics for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return List of topics related to the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/forum")
    suspend fun getMangaForum(@Path("id") id: Int): Response<MangaForum>

    /**
     * Retrieves additional information for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return Additional information about the manga in string format, if any.
     */
    @GET("manga/{id}/moreinfo")
    suspend fun getMangaMoreInfo(@Path("id") id: Int): Response<MangaMoreInfo>

    /**
     * Retrieves reviews for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @param page Optional, default is 1. Index of the page; each page contains 20 items.
     * @return List of reviews of the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/reviews/{page}")
    suspend fun getMangaReviews(@Path("id") id: Int, @Path("page") page: Int? = 1): Response<MangaReviews>

    /**
     * Retrieves recommendations for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @return List of recommendations for the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/recommendations")
    suspend fun getMangaRecommendations(@Path("id") id: Int): Response<MangaRecommendations>

    /**
     * Retrieves user updates for a manga by its MyAnimeList id.
     * @param id MyAnimeList id of the manga.
     * @param page Optional, default is 1. Index of the page; each page contains 75 items.
     * @return List of user updates for the manga with the given MyAnimeList id.
     */
    @GET("manga/{id}/userupdates/{page}")
    suspend fun getMangaUserUpdates(
        @Path("id") id: Int,
        @Path("page") page: Int? = 1
    ): Response<MangaUserUpdates>

    //endregion

    //region People

    /**
     * Retrieves details of a person by their MyAnimeList id.
     * @param id MyAnimeList id of the person.
     * @return Person with the given MyAnimeList id.
     */
    @GET("person/{id}")
    suspend fun getPerson(@Path("id") id: Int): Response<Person>

    /**
     * Retrieves pictures of a person by their MyAnimeList id.
     * @param id MyAnimeList id of the person.
     * @return List of pictures of the person with the given MyAnimeList id.
     */
    @GET("person/{id}/pictures")
    suspend fun getPersonPictures(@Path("id") id: Int): Response<PersonPictures>

    //endregion

    //region Characters

    /**
     * Retrieves details of a character by their MyAnimeList id.
     * @param id MyAnimeList id of the character.
     * @return Character with the given MyAnimeList id.
     */
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

    /**
     * Retrieves pictures of a character by their MyAnimeList id.
     * @param id MyAnimeList id of the character.
     * @return List of pictures of the character with the given MyAnimeList id.
     */
    @GET("character/{id}/pictures")
    suspend fun getCharacterPictures(@Path("id") id: Int): Response<CharacterPictures>

    //endregion

    //region Season

    /**
     * Retrieves a list of anime airing in a specific season on MyAnimeList.
     * @param year Year of the season.
     * @param season Season type (winter, spring, etc).
     * @return List of anime airing in the specified season.
     */
    @GET("season")
    suspend fun getSeason(@Query("year") year: Int?, @Query("season") season: String?): Response<Season>

    /**
     * Retrieves the archive of all seasons on MyAnimeList.
     * @return List of archived seasons.
     */
    @GET("season/archive")
    suspend fun getSeasonArchive(): Response<SeasonArchives>

    /**
     * Retrieves a list of anime airing in the next season on MyAnimeList.
     * @return List of anime airing in the next season.
     */
    @GET("season/later")
    suspend fun getSeasonLater(): Response<Season>

    //endregion

    //region Schedule

    /**
     * Retrieves the schedule of all anime in the current season on MyAnimeList.
     * @return List of days consisting of anime lists airing on each day.
     */
    @GET("schedule")
    suspend fun getSchedule(): Response<Schedule>

    /**
     * Retrieves the schedule of anime on a specific day in the current season on MyAnimeList.
     * @param day Day of the week.
     * @return List of anime airing on the specified day.
     */
    @GET("schedule/{day}")
    suspend fun getSchedule(@Path("day") day: String): Response<Schedule>

    //endregion

    //region Top

    /**
     * Retrieves the top anime on MyAnimeList.
     * @param page Optional, default is 1. Index of the page; each page contains 50 items.
     * @param subtype Optional, subtype type (upcoming, airing, etc).
     * @return List of top anime on MyAnimeList.
     */
    @GET("top/anime/{page}/{subtype}")
    suspend fun getTopAnime(@Path("page") page: Int?, @Path("subtype") subtype: String?): Response<TopAnime>

    /**
     * Retrieves the top manga on MyAnimeList.
     * @param page Optional, default is 1. Index of the page; each page contains 50 items.
     * @param subtype Optional, subtype type (novel, manga, etc).
     * @return List of top manga on MyAnimeList.
     */
    @GET("top/manga/{page}/{subtype}")
    suspend fun getTopManga(@Path("page") page: Int?, @Path("subtype") subtype: String?): Response<TopManga>

    /**
     * Retrieves the top characters on MyAnimeList.
     * @param page Optional, default is 1. Index of the page; each page contains 50 items.
     * @return List of top characters on MyAnimeList.
     */
    @GET("top/characters/{page}")
    suspend fun getTopCharacters(@Path("page") page: Int?): Response<TopCharacters>

    /**
     * Retrieves the top people on MyAnimeList.
     * @param page Optional, default is 1. Index of the page; each page contains 50 items.
     * @return List of top people on MyAnimeList.
     */
    @GET("top/people/{page}")
    suspend fun getTopPeople(@Path("page") page: Int?): Response<TopPeople>

    //endregion

    //region Search

    //region Anime

    /**
     * Search results for the query.
     * NOTE: MyAnimeList only processes queries with a minimum of 3 letters.
     * @param query String that will be the query. For UTF8 characters, percentage encoded and queries including back slashes
     * @param page Optional, default is 1. Index of the page.
     * @param additionalQuery Optional, additional query.
     * @return List of anime that satisfy all the queries.
     */
    @GET("search/anime/{page}")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Path("page") page: Int? = 1,
        @Query("additionalQuery") additionalQuery: String? = null
    ): Response<AnimeSearchResult>

    /**
     * Search results for the query.
     * @param page Optional, default is 1. Index of the page.
     * @param additionalQuery Optional, additional query.
     * @return List of anime that satisfy all the queries.
     */
    @GET("search/anime/{page}")
    suspend fun searchAnime(
        @Path("page") page: Int? = 1,
        @Query("additionalQuery") additionalQuery: String? = null
    ): Response<AnimeSearchResult>

    //endregion

    //region Manga

    /**
     * Search results for the query.
     * NOTE: MyAnimeList only processes queries with a minimum of 3 letters.
     * @param query String that will be the query. For UTF8 characters, percentage encoded and queries including backslashes
     * @param page Optional, default is 1. Index of the page.
     * @param additionalQuery Optional, additional query.
     * @return List of manga that satisfy all the queries.
     */
    @GET("search/manga/{page}")
    suspend fun searchManga(
        @Query("q") query: String,
        @Path("page") page: Int? = 1,
        @Query("additionalQuery") additionalQuery: String? = null
    ): Response<MangaSearchResult>

    /**
     * Search results for the query.
     * @param page Optional, default is 1. Index of the page.
     * @param additionalQuery Optional, additional query.
     * @return List of manga that satisfy all the queries.
     */
    @GET("search/manga/{page}")
    suspend fun searchManga(
        @Path("page") page: Int? = 1,
        @Query("additionalQuery") additionalQuery: String? = null
    ): Response<MangaSearchResult>

    //endregion

    //region Characters

    /**
     * Search results for the query.
     * NOTE: MyAnimeList only processes queries with a minimum of 3 letters.
     * @param query String that will be the query. For UTF8 characters, percentage encoded and queries including backslashes
     * @param page Optional, default is 1. Index of the page.
     * @return List of characters that satisfy all the queries.
     */
    @GET("search/character/{page}")
    suspend fun searchCharacter(
        @Query("q") query: String,
        @Path("page") page: Int? = 1
    ): Response<CharacterSearchResult>

    //endregion

    //region People

    /**
     * Search results for the query.
     * NOTE: MyAnimeList only processes queries with a minimum of 3 letters.
     * @param query String that will be the query. For UTF8 characters, percentage encoded and queries including backslashes
     * @param page Optional, default is 1. Index of the page.
     * @return List of people that satisfy all the queries.
     */
    @GET("search/people/{page}")
    suspend fun searchPeople(
        @Query("q") query: String,
        @Path("page") page: Int? = 1
    ): Response<PeopleSearchResult>

    //endregion
    //endregion

    //region User

    /**
     * Fetches MyAnimeList user-related data.
     * Note: "About" is returned in HTML, as MyAnimeList allows custom "about" sections for users that can consist of images,
     * formatting, etc.
     * @param username User's username on MyAnimeList.
     * @return User's profile data.
     */
    @GET("user/{username}/")
    suspend fun getUser(@Path("username") username: String): Response<User>

    /**
     * Fetches MyAnimeList user's history.
     * @param username User's username on MyAnimeList.
     * @param type History type (Anime, manga).
     * @return User's history data.
     */
    @GET("user/{username}/history/")
    suspend fun getUserHistory(
        @Path("username") username: String,
        @Query("type") type: String = "all"
    ): Response<UserHistory>

    /**
     * Fetches MyAnimeList user's friends.
     * @param username User's username on MyAnimeList.
     * @param page Optional, default is 1. Index of page.
     * @return User's friend list.
     */
    @GET("user/{username}/friends/")
    suspend fun getUserFriends(
        @Path("username") username: String,
        @Query("page") page: Int = 1
    ): Response<UserFriends>

    /**
     * Fetches MyAnimeList user's anime list.
     * @param username User's username on MyAnimeList.
     * @param filter Optional, filter list.
     * @param page Optional, default is 1. Index of page.
     * @return User's anime list.
     */
    @GET("user/{username}/animelist/")
    suspend fun getUserAnimeList(
        @Path("username") username: String,
        @Query("status") filter: String = "all",
        @Query("page") page: Int = 1
    ): Response<UserAnimeList>

    /**
     * Fetches MyAnimeList user's manga list.
     * @param username User's username on MyAnimeList.
     * @param filter Optional, filter list.
     * @param page Optional, default is 1. Index of page.
     * @return User's manga list.
     */
    @GET("user/{username}/mangalist/")
    suspend fun getUserMangaList(
        @Path("username") username: String,
        @Query("status") filter: String = "all",
        @Query("page") page: Int = 1
    ): Response<UserMangaList>

    /**
     * Fetches MyAnimeList user's anime list with search query.
     * @param username User's username on MyAnimeList.
     * @param query Query to filter by.
     * @param additionalQuery Optional, additional query.
     * @param page Optional, default is 1. Index of page.
     * @return User's anime list.
     */
    @GET("user/{username}/animelist/")
    suspend fun getUserAnimeListWithQuery(
        @Path("username") username: String,
        @Query("q") query: String,
        @Query("page") page: Int? = 1,
        @Query("additionalQuery") additionalQuery: String? = null
    ): Response<UserAnimeList>

    /**
     * Fetches MyAnimeList user's manga list with search query.
     * @param username User's username on MyAnimeList.
     * @param query Query to filter by.
     * @param additionalQuery Optional, additional query.
     * @param page Optional, default is 1. Index of page.
     * @return User's manga list.
     */
    @GET("user/{username}/mangalist/")
    suspend fun getUserMangaListWithQuery(
        @Path("username") username: String,
        @Query("q") query: String,
        @Query("page") page: Int? = 1,
        @Query("additionalQuery") additionalQuery: String? = null
    ): Response<UserMangaList>

    //endregion
}