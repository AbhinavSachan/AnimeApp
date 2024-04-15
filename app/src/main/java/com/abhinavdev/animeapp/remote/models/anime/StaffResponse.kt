package com.abhinavdev.animeapp.remote.models.anime

import com.abhinavdev.animeapp.remote.models.BaseModel
import com.abhinavdev.animeapp.remote.models.BaseResponse
import com.abhinavdev.animeapp.remote.models.common.PersonData
import com.google.gson.annotations.SerializedName

class StaffResponse : BaseResponse<List<StaffData>>()

data class StaffData(
    @SerializedName("person") val person: PersonData?,
    @SerializedName("positions") val positions: List<String>?,
) : BaseModel()