package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.UserRepository
import com.abhinavdev.animeapp.remote.models.enums.UserGender
import com.abhinavdev.animeapp.remote.models.users.UserByIdResponse
import com.abhinavdev.animeapp.remote.models.users.UserFullProfileResponse
import com.abhinavdev.animeapp.remote.models.users.UsersSearchResponse
import retrofit2.Response

class UserRepositoryImpl : UserRepository {
    private val apiService = ApiClient.getJikanApiService()

    override suspend fun getUserById(userId: Int): Response<UserByIdResponse> {
        return apiService.getUserById(userId)
    }

    override suspend fun getUserFullProfile(userName: String): Response<UserFullProfileResponse> {
        return apiService.getUserFullProfile(userName)
    }

    override suspend fun getUsersSearch(
        page: Int,
        limit: Int,
        query: String,
        gender: UserGender,
        location: String,
        maxAge: Int,
        minAge: Int
    ): Response<UsersSearchResponse> {
        return apiService.getUsersSearch(page, limit, query, gender.search, location, maxAge, minAge)
    }
}