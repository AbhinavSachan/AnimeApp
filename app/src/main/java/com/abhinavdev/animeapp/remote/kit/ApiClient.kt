package com.abhinavdev.animeapp.remote.kit

import com.abhinavdev.animeapp.util.Const
import com.abhinavdev.animeapp.util.appsettings.SettingsHelper
import com.abhinavdev.animeapp.util.extension.log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * This class is used to call apis. It will return Retrofit api service for call an api
 */
object ApiClient {
    private var jikanApiService: JikanApiService? = null
    private var oAuthApiService: OAuthApiService? = null
    private var malApiService: MalApiService? = null
    private const val MAX_RETRIES = 3
    private const val RETRY_DELAY_MS = 1000L

    var addLoggingInterceptor = false

    fun init() {
        val gson = createGson()

        jikanApiService = createApiService(
            Const.BaseUrls.JIKAN, createOkHttpClient(
                addRateLimitInterceptor = true,
                addAuthInterceptor = false,
                addLoggingInterceptor = addLoggingInterceptor
            ), gson, JikanApiService::class.java
        )
        oAuthApiService = createApiService(
            Const.BaseUrls.O_AUTH, createOkHttpClient(
                addRateLimitInterceptor = false,
                addAuthInterceptor = false,
                addLoggingInterceptor = addLoggingInterceptor
            ), gson, OAuthApiService::class.java
        )
        malApiService = createApiService(
            Const.BaseUrls.MAL, createOkHttpClient(
                addRateLimitInterceptor = false,
                addAuthInterceptor = true,
                addLoggingInterceptor = addLoggingInterceptor
            ), gson, MalApiService::class.java
        )
    }

    private fun createOkHttpClient(
        addRateLimitInterceptor: Boolean,
        addAuthInterceptor: Boolean,
        addLoggingInterceptor: Boolean,
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()

        httpBuilder.connectTimeout(Const.TimeOut.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Const.TimeOut.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.TimeOut.READ_TIMEOUT, TimeUnit.SECONDS)

        if (addRateLimitInterceptor) httpBuilder.addInterceptor(RateLimitInterceptor())
        if (addAuthInterceptor) httpBuilder.addInterceptor(AuthInterceptor())
        if (addLoggingInterceptor) httpBuilder.addInterceptor(OkHttpProfilerInterceptor())

        return httpBuilder.build()
    }

    private fun createGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    private inline fun <reified T> createApiService(
        baseUrl: String, httpClient: OkHttpClient, gson: Gson, serviceClass: Class<T>
    ): T {
        return Retrofit.Builder().baseUrl(baseUrl).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(serviceClass)
    }

    fun getJikanApiService(): JikanApiService = jikanApiService
        ?: throw IllegalStateException("ApiClient not initialized. Call init() first.")

    fun getOAuthApiService(): OAuthApiService = oAuthApiService
        ?: throw IllegalStateException("ApiClient not initialized. Call init() first.")

    fun getMalApiService(): MalApiService = malApiService
        ?: throw IllegalStateException("ApiClient not initialized. Call init() first.")

    class RateLimitInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var response = chain.proceed(chain.request())
            var tryCount = 0

//            //See: https://jikan.docs.apiary.io/#introduction/http-response
            while (!response.isSuccessful && response.code == 429 && tryCount < MAX_RETRIES) {
                tryCount++

                // Exponential backoff
                val delay = RETRY_DELAY_MS * tryCount
                log { "You are being rate limited or the API is being rate limited by MyAnimeList, retrying in $delay milliseconds..." }
                try {
                    Thread.sleep(delay)
                } catch (_: InterruptedException) {
                } finally {
                    response.close()
                }

                response = chain.proceed(chain.request())
            }

            return response
        }
    }

    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val requestBuilder = chain.request().newBuilder()
            val token = SettingsHelper.getAccessToken()?.accessToken

            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            return chain.proceed(requestBuilder.build())
        }
    }

}