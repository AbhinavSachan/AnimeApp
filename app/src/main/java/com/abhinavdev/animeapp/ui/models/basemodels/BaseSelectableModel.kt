package com.abhinavdev.animeapp.ui.models.basemodels

import com.abhinavdev.animeapp.remote.models.BaseModel

open class BaseSelectableModel(open val isSelected: Boolean = false) : BaseModel()