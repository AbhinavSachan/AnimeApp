package com.abhinavdev.animeapp.remote.models.person

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.AnimeStaffPosition
import com.abhinavdev.animeapp.remote.models.base.types.PublishedManga
import com.abhinavdev.animeapp.remote.models.base.types.VoiceActingRole
import java.util.*

data class Person(
    /**
     * ID associated with MyAnimeList.
     */
    @SerializedName("mal_id")
    val malId: Int,

    /**
     * Person's MyAnimeList link.
     */
    @SerializedName("url")
    val url: String? = null,

    /**
     * Person's MyAnimeList cover/image link.
     */
    @SerializedName("image_url")
    val imageUrl: String? = null,

    /**
     * Person's MyAnimeList website link.
     */
    @SerializedName("website_url")
    val websiteUrl: String? = null,

    /**
     * Person's name.
     */
    @SerializedName("name")
    val name: String? = null,

    /**
     * Person's given name.
     */
    @SerializedName("given_name")
    val givenName: String? = null,

    /**
     * Person's family name.
     */
    @SerializedName("family_name")
    val familyName: String? = null,

    /**
     * Person's alternative name(s).
     */
    @SerializedName("alternative_names")
    val alternativeNames: List<String?>? = null,

    /**
     * Person's birthday.
     */
    @SerializedName("birthday")
    val birthday: Date? = null,

    /**
     * Member favorites count.
     */
    @SerializedName("member_favorites")
    val memberFavorites: Int? = null,

    /**
     * Person's detail.
     */
    @SerializedName("about")
    val about: String? = null,

    /**
     * Person's voice acting roles.
     */
    @SerializedName("voice_acting_roles")
    val voiceActingRoles: List<VoiceActingRole?>? = null,

    /**
     * Person's anime staff position.
     */
    @SerializedName("anime_staff_positions")
    val animeStaffPosition: List<AnimeStaffPosition?>? = null,

    /**
     * Person's published manga.
     */
    @SerializedName("published_manga")
    val publishedManga: List<PublishedManga?>? = null

) : Entity()