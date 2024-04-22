package com.abhinavdev.animeapp.remote.kit.repository

import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import retrofit2.Response

interface OAuthRepository {

    /**
     * code	REQUIRED. Authorization code itself
     * code_verifier	REQUIRED. A minimum length of 43 characters and a maximum length of 128 characters. See the detail of PKCE code_challenge.
     * grant_type	REQUIRED. Value MUST be set to “authorization_code”.
     */
    suspend fun getAccessToken(code: String): Response<AccessToken>

    /**
     * refresh_token	REQUIRED. Refresh token code from last time
     * grant_type	REQUIRED. Value MUST be set to “refresh_token”.
     */
    suspend fun getRefreshAccessToken(refreshToken: String): Response<AccessToken>
}