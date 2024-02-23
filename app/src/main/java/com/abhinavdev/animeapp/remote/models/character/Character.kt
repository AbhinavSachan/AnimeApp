package com.abhinavdev.animeapp.remote.models.character

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.MalSubEntityWithRole
import com.abhinavdev.animeapp.remote.models.base.types.VoiceActor

data class Character(
    /**
     * ID associated with MyAnimeList.
     */
    @SerializedName("mal_id")
    val malId: Int,

    /**
     * Character's MyAnimeList link.
     */
    @SerializedName("url")
    val url: String? = null,

    /**
     * Character's MyAnimeList cover/image link.
     */
    @SerializedName("image_url")
    val imageUrl: String? = null,

    /**
     * Character's name.
     */
    @SerializedName("name")
    val name: String? = null,

    /**
     * Character's kanji name.
     */
    @SerializedName("name_kanji")
    val nameKanji: String? = null,

    /**
     * Character's nickname(s).
     */
    @SerializedName("nicknames")
    val nicknames: List<String?>? = null,

    /**
     * Character's detail.
     */
    @SerializedName("about")
    val about: String? = null,

    /**
     * Member favorites count.
     */
    @SerializedName("member_favorites")
    val memberFavorites: Int? = null,

    /**
     * Characters's animeography.
     */
    @SerializedName("animeography")
    val animeography: List<MalSubEntityWithRole?>? = null,

    /**
     * Characters's mangaography.
     */
    @SerializedName("mangaography")
    val mangaography: List<MalSubEntityWithRole?>? = null,

    /**
     * Characters's voice actors.
     */
    @SerializedName("voice_actors")
    val voiceActors: List<VoiceActor?>? = null
) : Entity()