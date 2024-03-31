package com.abhinavdev.animeapp.util.extension

import androidx.appcompat.widget.AppCompatImageView
import com.abhinavdev.animeapp.util.glide.GlideApp
import com.abhinavdev.animeapp.util.glide.ImageModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun AppCompatImageView.loadImage(url: Any?, placeHolder: Int = 0) {
    GlideApp.with(this)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}

fun AppCompatImageView.loadImage(
    path: String?,
    placeholderImage: Int,
    image_measure: Int,
    animate: Boolean
) {

    val drawableTransitionOptions: DrawableTransitionOptions = if (animate) {
        DrawableTransitionOptions.withCrossFade(300)
    } else {
        DrawableTransitionOptions().dontTransition()
    }
    GlideApp.with(context).load(path?.let { ImageModel(it) }).apply(
        RequestOptions().placeholder(
            placeholderImage
        ).error(placeholderImage)
    )
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .skipMemoryCache(true)
        .transition(drawableTransitionOptions)
        .override(image_measure, image_measure)
        .into(this)
}
