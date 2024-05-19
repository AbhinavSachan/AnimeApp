package com.abhinavdev.animeapp.remote.models.anime


import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseModelWithMal
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.AiredOnData
import com.abhinavdev.animeapp.remote.models.common.BroadcastData
import com.abhinavdev.animeapp.remote.models.common.DemographicData
import com.abhinavdev.animeapp.remote.models.common.ExplicitGenreData
import com.abhinavdev.animeapp.remote.models.common.ExternalData
import com.abhinavdev.animeapp.remote.models.common.GenreData
import com.abhinavdev.animeapp.remote.models.common.ImagesData
import com.abhinavdev.animeapp.remote.models.common.LicensorData
import com.abhinavdev.animeapp.remote.models.common.OpEdThemeData
import com.abhinavdev.animeapp.remote.models.common.ProducerData
import com.abhinavdev.animeapp.remote.models.common.RelationData
import com.abhinavdev.animeapp.remote.models.common.StreamingData
import com.abhinavdev.animeapp.remote.models.common.StudioData
import com.abhinavdev.animeapp.remote.models.common.ThemeData
import com.abhinavdev.animeapp.remote.models.common.TitleData
import com.abhinavdev.animeapp.remote.models.common.TrailerData
import com.abhinavdev.animeapp.remote.models.enums.AgeRating
import com.abhinavdev.animeapp.remote.models.enums.AnimeStatus
import com.abhinavdev.animeapp.remote.models.enums.AnimeType
import com.abhinavdev.animeapp.remote.models.enums.Season
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
    @SerializedName("duration") private val ogDuration: String?,
    @SerializedName("rating") val rating: AgeRating?,
    @SerializedName("score") val score: Float?,
    @SerializedName("scored_by") val scoredBy: Int?,
    @SerializedName("rank") val rank: Int?,
    @SerializedName("popularity") val popularity: Int?,
    @SerializedName("members") val members: Int?,
    @SerializedName("favorites") val favorites: Int?,
    @SerializedName("synopsis") private val ogSynopsis: String?,
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
) : BaseModelWithMal() {
    val duration get() = ogDuration?.let { parseDuration(it) }
    val synopsis get() = ogSynopsis?.let { removeWrittenByMalRewrite(it) }
    companion object {
        fun parseDuration(input: String): EpisodeDuration? {
            if (input.equals("Unknown", ignoreCase = true)) {
                return null
            }

            val isPerEpisode = input.contains("per ep")
            val cleanedInput = input.replace(" per ep", "").trim()
            val durationParts = cleanedInput.split(",").map { it.trim() }

            val formattedParts = durationParts.mapNotNull { durationPart ->
                when {
                    durationPart.contains("hr") && durationPart.contains("min") -> {
                        val parts = durationPart.split("hr", "min").map { it.trim() }
                        if (parts.size == 3 && parts[0].isNotEmpty() && parts[1].isNotEmpty()) {
                            "${parts[0]}h ${parts[1]}m"
                        } else null
                    }

                    durationPart.contains("hr") -> {
                        val parts = durationPart.split("hr").map { it.trim() }
                        if (parts.size == 2 && parts[0].isNotEmpty()) {
                            "${parts[0]}h"
                        } else null
                    }

                    durationPart.contains("min") -> {
                        val parts = durationPart.split("min").map { it.trim() }
                        if (parts.size == 2 && parts[0].isNotEmpty()) {
                            "${parts[0]}m"
                        } else null
                    }

                    else -> null
                }
            }

            if (formattedParts.isEmpty()) {
                return null
            }

            val duration = formattedParts.joinToString(", ")

            return EpisodeDuration(duration, isPerEpisode)
        }

        fun removeWrittenByMalRewrite(text: String): String {
            return text.replace("\n\n[Written by MAL Rewrite]", "",true)
        }
    }
}

data class EpisodeDuration(val duration: String, val isPerEpisode: Boolean) : BaseModel()

