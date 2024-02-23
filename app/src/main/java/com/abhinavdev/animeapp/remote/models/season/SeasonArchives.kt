package moe.ganen.jikankt.models.season

import com.google.gson.annotations.SerializedName
import com.abhinavdev.animeapp.remote.models.base.Entity
import com.abhinavdev.animeapp.remote.models.base.types.SeasonArchive

data class SeasonArchives(
    /**
     * List of archived season
     */
    @SerializedName("archive")
    val archive: List<SeasonArchive?>? = null
) : Entity()