package com.abhinavdev.animeapp.ui.common.adapters

import com.abhinavdev.animeapp.remote.models.BaseModel

data class SelectableItemData(
    val id: String?,
    val name: String?,
    var isSelected: Boolean = false
) : BaseModel()