package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.models.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubRelationsResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubSearchResponse
import com.abhinavdev.animeapp.remote.models.clubs.ClubStaffResponse
import com.abhinavdev.animeapp.remote.models.enums.ClubCategory
import com.abhinavdev.animeapp.remote.models.enums.ClubOrderBy
import com.abhinavdev.animeapp.remote.models.enums.ClubType
import com.abhinavdev.animeapp.remote.models.enums.SortOrder
import retrofit2.Response

interface ClubRepository {
    suspend fun getClubsById(
        clubId: Int,
    ): Response<ClubResponse>

    suspend fun getClubMembers(
        clubId: Int,
        page: Int,
    ): Response<ClubMembersResponse>

    suspend fun getClubStaff(
        clubId: Int,
    ): Response<ClubStaffResponse>

    suspend fun getClubRelations(
        clubId: Int,
    ): Response<ClubRelationsResponse>

    suspend fun getClubSearch(
        page: Int,
        limit: Int,
        q: String,
        type: ClubType,
        category: ClubCategory,
        orderBy: ClubOrderBy,
        sort: SortOrder,
        letter: String,
    ): Response<ClubSearchResponse>
}