package com.abhinavdev.animeapp.remote.models.clubs

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.MalUrlData
import com.google.gson.annotations.SerializedName

class ClubRelationsResponse : BaseResponse<List<ClubStaffData>>()

data class ClubRelationsData(
    @SerializedName("anime") val anime: List<ClubAnime>?,
    @SerializedName("manga") val manga: List<ClubManga>?,
    @SerializedName("characters") val characters: List<ClubCharacter>?,
) : BaseModel()

class ClubAnime : MalUrlData()
class ClubManga : MalUrlData()
class ClubCharacter : MalUrlData()