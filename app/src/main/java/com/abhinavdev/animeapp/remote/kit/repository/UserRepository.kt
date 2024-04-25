package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.models.enums.UserGender
import com.abhinavdev.animeapp.remote.models.users.UserByIdResponse
import com.abhinavdev.animeapp.remote.models.users.UserFullProfileResponse
import com.abhinavdev.animeapp.remote.models.users.UsersSearchResponse
import retrofit2.Response

interface UserRepository {

    suspend fun getUserById(
        userId: Int,
    ): Response<UserByIdResponse>

    suspend fun getUserFullProfile(
        userName: String,
    ): Response<UserFullProfileResponse>

    /**
     * @param page
     * @param limit
     * @param query
     * @param gender
     * Enum: "any" "male" "female" "nonbinary" (UserGender)
     * Users Search Query Gender.
     *
     * @param location
     * @param maxAge
     * @param minAge
     */
    suspend fun getUsersSearch(
        page: Int,
        limit: Int,
        query: String,
        gender: UserGender,
        location: String,
        maxAge: Int,
        minAge: Int,
    ): Response<UsersSearchResponse>

}