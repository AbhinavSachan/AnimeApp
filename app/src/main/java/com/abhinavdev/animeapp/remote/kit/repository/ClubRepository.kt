package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.model.clubs.ClubMembersResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubRelationsResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubSearchResponse
import com.abhinavdev.animeapp.remote.model.clubs.ClubStaffResponse
import com.abhinavdev.animeapp.remote.model.enums.ClubCategory
import com.abhinavdev.animeapp.remote.model.enums.ClubOrderBy
import com.abhinavdev.animeapp.remote.model.enums.ClubType
import com.abhinavdev.animeapp.remote.model.enums.SortOrder
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