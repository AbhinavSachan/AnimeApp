package com.abhinavdev.animeapp.ui.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    var image: Int?,
) : Parcelable