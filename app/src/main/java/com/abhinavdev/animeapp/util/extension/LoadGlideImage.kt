package com.abhinavdev.animeapp.util.extension

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadImage(url: Any?, placeHolder: Int = 0) {
    Glide.with(this)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}
