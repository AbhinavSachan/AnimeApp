package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.CharacterRepository
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

class CharacterRepositoryImpl : CharacterRepository {
    private val apiService = ApiClient.getJikanApiService()

    override suspend fun getCharacterFullById(characterId: Int): Response<CharacterFullResponse> {
        return apiService.getCharacterFullById(characterId)
    }

    override suspend fun getCharacterById(characterId: Int): Response<CharacterFullResponse> {
        return apiService.getCharacterById(characterId)
    }

    override suspend fun getCharacterAnime(characterId: Int): Response<CharacterAnimeResponse> {
        return apiService.getCharacterAnime(characterId)
    }

    override suspend fun getCharacterManga(characterId: Int): Response<CharacterMangaResponse> {
        return apiService.getCharacterManga(characterId)
    }

    override suspend fun getCharacterVoiceActors(characterId: Int): Response<CharacterVoiceActorsResponse> {
        return apiService.getCharacterVoiceActors(characterId)
    }

    override suspend fun getCharacterPictures(characterId: Int): Response<CharacterPicturesResponse> {
        return apiService.getCharacterPictures(characterId)
    }

    override suspend fun getCharactersSearch(
        page: Int, limit: Int, query: String, orderBy: CharacterOrderBy, sort: SortOrder, letter: String
    ): Response<CharacterSearchResponse> {
        return apiService.getCharactersSearch(page, limit, query, orderBy.search, sort.search, letter)
    }

    override suspend fun getRandomCharacters(): Response<CharacterResponse> {
        return apiService.getRandomCharacters()
    }
}