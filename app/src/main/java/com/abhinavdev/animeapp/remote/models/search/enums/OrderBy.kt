package com.abhinavdev.animeapp.remote.models.search.enums

enum class AnimeOrderBy(val queryString: String? = null) {
    None,
    Title,
    StartDate("start_date"),
    EndDate("end_date"),
    Score,
    Type,
    Members,
    ID,
    Episodes,
    Rating
}

enum class MangaOrderBy(val queryString: String? = null) {
    None,
    Title,
    StartDate("start_date"),
    EndDate("end_date"),
    Score,
    Type,
    Members,
    ID,
    Chapters,
    Volumes
}