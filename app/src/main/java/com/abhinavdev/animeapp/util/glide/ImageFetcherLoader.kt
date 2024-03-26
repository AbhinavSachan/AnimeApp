package com.abhinavdev.animeapp.util.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import java.io.InputStream

class ImageFetcherLoader : ModelLoader<ImageModel, InputStream> {
    override fun buildLoadData(
        imageModel: ImageModel,
        width: Int,
        height: Int,
        options: Options
    ): LoadData<InputStream> {
        return LoadData(ObjectKey(imageModel.url), ImageFetcher(imageModel))
    }

    override fun handles(imageModel: ImageModel): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<ImageModel, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<ImageModel, InputStream> {
            return ImageFetcherLoader()
        }

        override fun teardown() {}
    }
}