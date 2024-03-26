package com.abhinavdev.animeapp.util.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

@GlideModule
class ImageGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(
            ImageModel::class.java,
            InputStream::class.java,
            ImageFetcherLoader.Factory()
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}