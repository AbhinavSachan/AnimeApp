package com.abhinavdev.animeapp.remote.model.anime

import com.abhinavdev.animeapp.remote.model.BaseModel
import com.abhinavdev.animeapp.remote.model.BaseResponse
import com.abhinavdev.animeapp.remote.model.common.PersonData
import com.google.gson.annotations.SerializedName

class StaffResponse : BaseResponse<List<StaffData>>()

data class StaffData(
    @SerializedName("person") val person: PersonData?,
    @SerializedName("positions") val positions: List<String>?,
) : BaseModel()