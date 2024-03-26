package com.abhinavdev.animeapp.remote.model.anime


import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.AiredOnData
import com.abhinavdev.animeapp.remote.model.common.BroadcastData
import com.abhinavdev.animeapp.remote.model.common.DemographicData
import com.abhinavdev.animeapp.remote.model.common.ExplicitGenreData
import com.abhinavdev.animeapp.remote.model.common.ExternalData
import com.abhinavdev.animeapp.remote.model.common.GenreData
import com.abhinavdev.animeapp.remote.model.common.ImagesData
import com.abhinavdev.animeapp.remote.model.common.LicensorData
import com.abhinavdev.animeapp.remote.model.common.OpEdThemeData
import com.abhinavdev.animeapp.remote.model.common.ProducerData
import com.abhinavdev.animeapp.remote.model.common.RelationData
import com.abhinavdev.animeapp.remote.model.common.StreamingData
import com.abhinavdev.animeapp.remote.model.common.StudioData
import com.abhinavdev.animeapp.remote.model.common.ThemeData
import com.abhinavdev.animeapp.remote.model.common.TitleData
import com.abhinavdev.animeapp.remote.model.common.TrailerData
import com.abhinavdev.animeapp.remote.model.enums.AgeRating
import com.abhinavdev.animeapp.remote.model.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.model.enums.AnimeType
import com.abhinavdev.animeapp.remote.model.enums.Season
import com.google.gson.annotations.SerializedName

class AnimeFullResponse : BaseResponse<AnimeData>()

data class AnimeData(
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("trailer") val trailer: TrailerData?,
    @SerializedName("approved") val approved: Boolean?,
    @SerializedName("titles") val titles: ArrayList<TitleData>?,
    @SerializedName("type") val type: AnimeType?,
    @SerializedName("source") val source: String?,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("status") val status: AnimeStatus?,
    @SerializedName("airing") val airing: Boolean?,
    @SerializedName("aired") val airedOn: AiredOnData?,
    @SerializedName("duration") val duration: String?,
    @SerializedName("rating") val rating: AgeRating?,
    @SerializedName("score") val score: Float?,
    @SerializedName("scored_by") val scoredBy: Int?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("popularity") val popularity: Int?,
    @SerializedName("members") val members: Int?,
    @SerializedName("favorites") val favorites: Int?,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("background") val background: String?,
    @SerializedName("season") val season: Season?,
    @SerializedName("year") val year: Int?,
    @SerializedName("broadcast") val broadcast: BroadcastData?,
    @SerializedName("producers") val producers: ArrayList<ProducerData>?,
    @SerializedName("licensors") val licensors: ArrayList<LicensorData>?,
    @SerializedName("studios") val studios: ArrayList<StudioData>?,
    @SerializedName("genres") val genres: ArrayList<GenreData>?,
    @SerializedName("explicit_genres") val explicitGenres: ArrayList<ExplicitGenreData>?,
    @SerializedName("themes") val themes: ArrayList<ThemeData>?,
    @SerializedName("demographics") val demographics: ArrayList<DemographicData>?,
    @SerializedName("relations") val relations: ArrayList<RelationData>?,
    @SerializedName("theme") val opEdTheme: OpEdThemeData?,
    @SerializedName("external") val externalLinks: ArrayList<ExternalData>?,
    @SerializedName("streaming") val streamingLinks: ArrayList<StreamingData>?,
) : BaseModelWithMal()
