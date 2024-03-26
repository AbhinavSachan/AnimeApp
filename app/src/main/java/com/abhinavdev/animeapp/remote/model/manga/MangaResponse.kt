package com.abhinavdev.animeapp.remote.model.manga

import com.abhinavdev.animeapp.remote.model.BaseModelWithMal
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.AiredOnData
import com.abhinavdev.animeapp.remote.model.common.AuthorData
import com.abhinavdev.animeapp.remote.model.common.DemographicData
import com.abhinavdev.animeapp.remote.model.common.ExplicitGenreData
import com.abhinavdev.animeapp.remote.model.common.ExternalData
import com.abhinavdev.animeapp.remote.model.common.GenreData
import com.abhinavdev.animeapp.remote.model.common.ImagesData
import com.abhinavdev.animeapp.remote.model.common.RelationData
import com.abhinavdev.animeapp.remote.model.common.SerializationData
import com.abhinavdev.animeapp.remote.model.common.ThemeData
import com.abhinavdev.animeapp.remote.model.common.TitleData
import com.abhinavdev.animeapp.remote.model.enums.MangaStatus
import com.abhinavdev.animeapp.remote.model.enums.MangaType
import com.google.gson.annotations.SerializedName

class MangaResponse : BaseResponse<MangaData>()

data class MangaData(
    @SerializedName("url") val url: String?,
    @SerializedName("images") val images: ImagesData?,
    @SerializedName("approved") val approved: Boolean?,
    @SerializedName("titles") val titles: ArrayList<TitleData>?,
    @SerializedName("type") val type: MangaType?,
    @SerializedName("chapters") val chapters: Int?,
    @SerializedName("volumes") val volumes: Int?,
    @SerializedName("status") val status: MangaStatus?,
    @SerializedName("publishing") val publishing: Boolean?,
    @SerializedName("published") val published: AiredOnData?,
    @SerializedName("score") val score: Float?,
    @SerializedName("scored_by") val scoredBy: Int?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("popularity") val popularity: Int?,
    @SerializedName("members") val members: Int?,
    @SerializedName("favorites") val favorites: Int?,
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("background") val background: String?,
    @SerializedName("authors") val authors: ArrayList<AuthorData>?,
    @SerializedName("serializations") val serializations: ArrayList<SerializationData>?,
    @SerializedName("genres") val genres: ArrayList<GenreData>?,
    @SerializedName("explicit_genres") val explicitGenres: ArrayList<ExplicitGenreData>?,
    @SerializedName("themes") val themes: ArrayList<ThemeData>?,
    @SerializedName("demographics") val demographics: ArrayList<DemographicData>?,
    @SerializedName("relations") val relations: ArrayList<RelationData>?,
    @SerializedName("external") val `external`: ArrayList<ExternalData>?
) : BaseModelWithMal()