package com.abhinavdev.animeapp.remote.kit.sources

import com.abhinavdev.animeapp.BuildConfig
import com.abhinavdev.animeapp.remote.kit.ApiClient
import com.abhinavdev.animeapp.remote.kit.repository.OAuthRepository
import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.PKCEUtil
import retrofit2.Response

class OAuthRepositoryImpl : OAuthRepository {
    private val apiService = ApiClient.getOAuthApiService()

    override suspend fun getLoginUrl(): String {
        val baseUrl = Const.BaseUrls.O_AUTH
        val clientId = BuildConfig.CLIENT_ID
        val codeChallenge = PKCEUtil.getCodeChallenge()
        val state = Const.Mal.STATE
        return "${baseUrl}authorize?response_type=code&client_id=${clientId}&code_challenge=${codeChallenge}&state=${state}"
    }

    override suspend fun getAccessToken(code: String): Response<AccessToken> {
        val clientId = BuildConfig.CLIENT_ID
        val codeVerifier = PKCEUtil.getCodeVerifier()
        val grantType = "authorization_code"
       return apiService.getAccessToken(clientId,code,codeVerifier,grantType)
    }

    override suspend fun getRefreshAccessToken(refreshToken:String): Response<AccessToken> {
        val clientId = BuildConfig.CLIENT_ID
        val grantType = "refresh_token"
        return apiService.getRefreshAccessToken(clientId, refreshToken, grantType)
    }
}