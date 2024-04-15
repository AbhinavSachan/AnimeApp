package com.abhinavdev.animeapp.remote.mal

import com.abhinavdev.animeapp.remote.models.anime.AnimeFullResponse
import com.abhinavdev.animeapp.util.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MalApiService {

    @GET(Const.ApiKeywords.ANIME + "/{id}/" + Const.ApiKeywords.FULL)
    suspend fun getFullAnimeById(
        @Path("id") animeId: Int,
    ): Response<AnimeFullResponse>

//    suspend fun getAccessToken(
//        clientId: String,
//        code: String,
//        codeVerifier: String,
//        grantType: String
//    ): AccessToken = client.post("${MAL_OAUTH2_URL}token") {
//        setBody(FormDataContent(Parameters.build {
//            append("client_id", clientId)
//            append("code", code)
//            append("code_verifier", codeVerifier)
//            append("grant_type", grantType)
//        }))
//    }.body()
//
//    suspend fun getAccessToken(
//        clientId: String,
//        refreshToken: String
//    ): AccessToken = client.post("${MAL_OAUTH2_URL}token") {
//        setBody(FormDataContent(Parameters.build {
//            append("client_id", clientId)
//            append("refresh_token", refreshToken)
//            append("grant_type", "refresh_token")
//        }))
//    }.body()
}