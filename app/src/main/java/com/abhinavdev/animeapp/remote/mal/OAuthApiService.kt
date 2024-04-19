package com.abhinavdev.animeapp.remote.mal

import com.abhinavdev.animeapp.remote.models.malmodels.AccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OAuthApiService {

    /**
     * code	REQUIRED. Authorization code itself
     * code_verifier	REQUIRED. A minimum length of 43 characters and a maximum length of 128 characters. See the detail of PKCE code_challenge.
     * grant_type	REQUIRED. Value MUST be set to “authorization_code”.
     */
    @FormUrlEncoded
    @POST("token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("code") code: String,
        @Field("code_verifier") codeVerifier: String,
        @Field("grant_type") grantType: String
    ): Response<AccessToken>

    /**
     * refresh_token	REQUIRED. Refresh token code from last time
     * grant_type	REQUIRED. Value MUST be set to “refresh_token”.
     */
    @FormUrlEncoded
    @POST("token")
    suspend fun getRefreshAccessToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String
    ): Response<AccessToken>

}