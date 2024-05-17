package com.abhinavdev.animeapp.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.abhinavdev.animeapp.R
import com.abhinavdev.animeapp.util.Const
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget

fun CustomTarget<Drawable>.loadBlurImage(context: Context, image: String?) {
    Glide.with(context).load(image).override(5, 5).into(this)
}

fun AppCompatImageView.loadBlurImage(image: String?) {
    Glide.with(this).load(image)
        .override(5, 5)
        .transition(DrawableTransitionOptions.withCrossFade(300))
        .into(this)
}

fun AppCompatImageView.loadImage(
    path: String?,
) {

    //if received mal default image url then show our own placeholder
    val image: Any? =
        if (path == Const.Other.MAL_IMAGE_PLACEHOLDER) R.drawable.bg_placeholder else path
    Glide.with(this.context).load(image)
        .placeholder(R.color.image_placeholder)
        .error(R.drawable.bg_error_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .skipMemoryCache(false)
        .transition(DrawableTransitionOptions.withCrossFade(300))
        .into(this)
}

fun CustomTarget<Drawable>.loadImage(
    context: Context,
    path: String?,
) {
    //if received mal default image url then show our own placeholder
    val image: Any? =
        if (path == Const.Other.MAL_IMAGE_PLACEHOLDER) R.drawable.bg_placeholder else path
    Glide.with(context).load(image)
        .placeholder(R.color.image_placeholder)
        .error(R.drawable.bg_error_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .skipMemoryCache(false)
        .into(this)
}

fun Context.pauseGlideRequest() {
    if (!Glide.with(this).isPaused) {
        Glide.with(this).pauseRequests()
    }
}

fun Context.resumeGlideRequest() {
    if (Glide.with(this).isPaused) {
        Glide.with(this).resumeRequests()
    }
}