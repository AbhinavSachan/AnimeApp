package com.abhinavdev.animeapp.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.abhinavdev.animeapp.util.glide.GlideApp
import com.abhinavdev.animeapp.util.glide.ImageModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget

fun AppCompatImageView.loadImage(url: Any?, placeHolder: Int = 0) {
    Glide.with(this)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}

fun AppCompatImageView.loadImage(url: String?, placeHolder: Int = 0) {
    GlideApp.with(this)
        .load(url?.let { ImageModel(it) })
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}

fun CustomTarget<Drawable>.loadBlurImage(context: Context, image: Int = 0) {
    Glide.with(context)
        .load(image)
        .override(3, 3)
        .into(this)
}

fun AppCompatImageView.loadImageWithAnime(
    path: String?,
    placeholderImage: Int,
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
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(false)
        .transition(drawableTransitionOptions)
        .into(this)
}
