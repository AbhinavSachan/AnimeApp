package com.abhinavdev.animeapp.remote.kit

import androidx.annotation.IntRange
import com.abhinavdev.animeapp.remote.models.malmodels.AnimeListStatus
import com.abhinavdev.animeapp.remote.models.malmodels.MalAnimeNode
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyAnimeListResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalMyMangaListResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MalProfileResponse
import com.abhinavdev.animeapp.remote.models.malmodels.MangaListStatus
import com.abhinavdev.animeapp.util.Const
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface MalApiService {
//    fields="synopsis,my_list_status{priority,comments}"

    @FormUrlEncoded
    @PATCH(Const.Mal.ANIME + "/{anime_id}/" + Const.Mal.MY_LIST_STATUS)
    suspend fun updateAnimeListStatus(
        @Path("anime_id") animeId: Int,
        @Field("status") status: String,
        @Field("is_rewatching") isReWatching: Boolean,
        @Field("score") @IntRange(0, 10) score: Int,
        @Field("num_watched_episodes") numWatchedEpisodes: Int,
        @Field("priority") @IntRange(0, 2) priority: Int,
        @Field("start_date") startDate: String,
        @Field("finish_date") finishDate: String,
        @Field("num_times_rewatched") numTimesReWatched: Int,
        @Field("rewatch_value") @IntRange(0, 5) reWatchValue: Int,
        @Field("tags") tags: ArrayList<String>,
        @Field("comments") comments: String,
    ): Response<AnimeListStatus>

    @DELETE(Const.Mal.ANIME + "/{anime_id}/" + Const.Mal.MY_LIST_STATUS)
    suspend fun deleteAnimeFromList(
        @Path("anime_id") animeId: Int
    ): Response<Any>

    /**
     * fields = "my_list_status{priority,comments},alternative_titles,media_type,mean"
     */
    @GET(Const.Mal.ANIME + "/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") animeId: Int,
        @Query("fields") fields: String,
    ): Response<MalAnimeNode>

    @GET(Const.Mal.USERS + "/@me/" + Const.Mal.ANIME_LIST)
    suspend fun getMyAnimeList(
        @Query("status") status: String,
        @Query("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<MalMyAnimeListResponse>

    @FormUrlEncoded
    @PATCH(Const.Mal.MANGA + "/{manga_id}/" + Const.Mal.MY_LIST_STATUS)
    suspend fun updateMangaListStatus(
        @Path("manga_id") mangaId: Int,
        @Field("status") status: String,
        @Field("is_rereading") isReWatching: Boolean,
        @Field("score") @IntRange(0, 10) score: Int,
        @Field("num_chapters_read") numChaptersRead: Int,
        @Field("num_volumes_read") numVolumesRead: Int,
        @Field("priority") @IntRange(0, 2) priority: Int,
        @Field("start_date") startDate: String,
        @Field("finish_date") finishDate: String,
        @Field("num_times_reread") numTimesReread: Int,
        @Field("reread_value") @IntRange(0, 5) rereadValue: Int,
        @Field("tags") tags: ArrayList<String>,
        @Field("comments") comments: String,
    ): Response<MangaListStatus>

    @DELETE(Const.Mal.MANGA + "/{manga_id}/" + Const.Mal.MY_LIST_STATUS)
    suspend fun deleteMangaFromList(
        @Path("manga_id") mangaId: Int
    ): Response<Any>

    /**
     * fields = "my_list_status{priority,comments},alternative_titles,media_type,mean"
     */
    @GET(Const.Mal.USERS + "/@me/" + Const.Mal.MANGA_LIST)
    suspend fun getMyMangaList(
        @Query("status") status: String,
        @Query("sort") sort: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<MalMyMangaListResponse>

    @GET(Const.Mal.USERS + "/@me")
    suspend fun getProfile(): Response<MalProfileResponse>

    @GET(Const.Mal.ANIME + "/" + Const.Mal.SUGGESTIONS)
    suspend fun getRecommendedAnime(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<MalMyAnimeListResponse>

    @GET(Const.Mal.ANIME + "/" + Const.Mal.RANKING)
    suspend fun getAnimeRanking(
        @Query("ranking_type") rankingType: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<MalMyAnimeListResponse>

    @GET(Const.Mal.MANGA + "/" + Const.Mal.RANKING)
    suspend fun getMangaRanking(
        @Query("ranking_type") rankingType: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("fields") fields: String,
    ): Response<MalMyMangaListResponse>

}