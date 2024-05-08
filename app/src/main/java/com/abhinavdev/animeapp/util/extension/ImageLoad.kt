package com.abhinavdev.animeapp.util.extension

import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

fun AppCompatImageView.loadImage(url: Int, placeHolder: Int = 0) {
    Picasso.get().load(url).placeholder(placeHolder).error(placeHolder).into(this)
}

fun Target.loadBlurImage(image: Int = 0) {
    Picasso.get().load(image).resize(3, 3).into(this)
}

fun Target.loadBlurImage(image: String?) {
    Picasso.get().load(image).resize(3, 3).into(this)
}

fun AppCompatImageView.loadImage(
    path: String?,
    placeholderImage: Int = 0,
) {
    Picasso.get().load(path).placeholder(placeholderImage).error(placeholderImage).into(this)
}
