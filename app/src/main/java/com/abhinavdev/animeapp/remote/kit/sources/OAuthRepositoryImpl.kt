package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.BuildConfig
import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.OAuthRepository
import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.abhinavdev.animeapp.util.ui.LoginUtil
import retrofit2.Response

class OAuthRepositoryImpl : OAuthRepository {
    private val apiService = ApiClient.getOAuthApiService()

    override suspend fun getAccessToken(code: String): Response<AccessToken> {
        val clientId = BuildConfig.CLIENT_ID
        val codeVerifier = LoginUtil.getCodeVerifier()
        val grantType = "authorization_code"
        return apiService.getAccessToken(clientId, code, codeVerifier, grantType)
    }

    override suspend fun getRefreshAccessToken(refreshToken: String): Response<AccessToken> {
        val clientId = BuildConfig.CLIENT_ID
        val grantType = "refresh_token"
        return apiService.getRefreshAccessToken(clientId, refreshToken, grantType)
    }
}