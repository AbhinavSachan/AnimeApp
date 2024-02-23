package com.abhinavdev.animeapp.remote.models.search.enums

enum class AnimeType {
    All,
    TV,
    OVA,
    Movie,
    Special,
    ONA,
    Music
}

enum class MangaType(val queryString: String? = null) {
    All,
    Manga,
    Novel,
    `One-shot`("oneshot"),
    Doujin,
    Manhwa,
    Manhua
}