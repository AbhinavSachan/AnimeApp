package com.abhinavdev.animeapp.util.glide


class ImageModel(val url: String) {
    override fun hashCode(): Int {
        return url.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ImageModel) {
            other.url == url
        } else false
    }
}