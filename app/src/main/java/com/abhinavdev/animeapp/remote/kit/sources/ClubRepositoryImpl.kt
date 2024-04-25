package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.ClubRepository
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

class ClubRepositoryImpl : ClubRepository {
    private val apiService = ApiClient.getJikanApiService()

    override suspend fun getClubsById(clubId: Int): Response<ClubResponse> {
        return apiService.getClubsById(clubId)
    }

    override suspend fun getClubMembers(clubId: Int, page: Int): Response<ClubMembersResponse> {
        return apiService.getClubMembers(clubId, page)
    }

    override suspend fun getClubStaff(clubId: Int): Response<ClubStaffResponse> {
        return apiService.getClubStaff(clubId)
    }

    override suspend fun getClubRelations(clubId: Int): Response<ClubRelationsResponse> {
        return apiService.getClubRelations(clubId)
    }

    override suspend fun getClubSearch(
        page: Int,
        limit: Int,
        query: String,
        type: ClubType,
        category: ClubCategory,
        orderBy: ClubOrderBy,
        sort: SortOrder,
        letter: String
    ): Response<ClubSearchResponse> {
        return apiService.getClubSearch(
            page, limit, query, type.search, category.search, orderBy.search, sort.search, letter
        )
    }
}