package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.models.anime.CharacterResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterAnimeResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterFullResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterMangaResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterPicturesResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterSearchResponse
import com.abhinavdev.animeapp.remote.models.characters.CharacterVoiceActorsResponse
import com.abhinavdev.animeapp.remote.models.enums.CharacterOrderBy
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import retrofit2.Response

interface CharacterRepository {
    suspend fun getCharacterFullById(
        characterId: Int,
    ): Response<CharacterFullResponse>

    suspend fun getCharacterById(
        characterId: Int,
    ): Response<CharacterFullResponse>

    suspend fun getCharacterAnime(
        characterId: Int,
    ): Response<CharacterAnimeResponse>

    suspend fun getCharacterManga(
        characterId: Int,
    ): Response<CharacterMangaResponse>

    suspend fun getCharacterVoiceActors(
        characterId: Int,
    ): Response<CharacterVoiceActorsResponse>

    suspend fun getCharacterPictures(
        characterId: Int,
    ): Response<CharacterPicturesResponse>

    suspend fun getCharactersSearch(
        page: Int,
        limit: Int,
        query: String,
        orderBy: CharacterOrderBy,
        sort: SortOrder,
        letter: String,
    ): Response<CharacterSearchResponse>

    suspend fun getRandomCharacters(): Response<CharacterResponse>

}