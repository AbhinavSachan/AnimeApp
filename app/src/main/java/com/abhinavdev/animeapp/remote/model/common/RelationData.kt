package com.abhinavdev.animeapp.remote.model.common

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.google.gson.annotations.SerializedName

data class RelationData(
    @SerializedName("relation") val relation: String?,
    @SerializedName("entry") val entry: ArrayList<RelatedEntryData>?,
) : BaseModel()

class RelatedEntryData : MalUrlData()