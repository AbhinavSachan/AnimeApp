package com.abhinavdev.animeapp.remote.connection

import com.abhinavdev.animeapp.util.Const
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * This class is used to call apis. It will return Retrofit api service for call an api
 */
object JikanClient {
    private lateinit var httpBuilder: OkHttpClient.Builder
    private lateinit var retrofitBuilder: Retrofit.Builder
    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var apiService: ApiService
    private var loggingInterceptor = HttpLoggingInterceptor()

    private var BASE_URL = "https://api.jikan.moe/v4/"

    fun init() {
        retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        httpBuilder = OkHttpClient.Builder()
            .addInterceptor(RateLimitInterceptor())
//            .addInterceptor(AuthInterceptor())

        addLoggingInterceptor(true)
        setTimeout(httpBuilder)
        okHttpClient = httpBuilder.build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        retrofit = retrofitBuilder
            .client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        if (!::apiService.isInitialized) {
            throw IllegalStateException("JikanClient not initialized. Call init() first.")
        }
        return apiService
    }

    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val requestBuilder = request.newBuilder()
            val url = request.url.newBuilder()

//			val token =
//				"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMTVjMmRlMWRlYWM2NzIwMzQ3MGU5ZGNmMzgyNjkyMzc4Y2Q2YjdkYTFiZjg3MTE3NDJiOWE0NzM2NGE5MWMwODgzZDcyYTdlMzdiNWUyZDIiLCJpYXQiOjE2NzI5MDk5MzguNjU3OTcyLCJuYmYiOjE2NzI5MDk5MzguNjU3OTc2LCJleHAiOjE3MDQ0NDU5MzguNjU0NDE0LCJzdWIiOiI4Iiwic2NvcGVzIjpbXX0.W2J1W9I8hK-vVdqVrAIAoOthj1CLuhfgH6jI70LPDLKHkP9f8jynvyanDXOpLiutBxDAfHiDa1OaxonbhqrAWQMsGFB1ZmF6PirN8KDWbSbyPa2Q8ICmb2mhnHGxHg66fCliPxv0m3SjhwDCAstTcdYdUelwajsApn0iWNWBWAPJNmckrhBmuxiN1tJhVQoZjY6Dc_CZlLQ6-cnze_w2S8IP-2wKH3iMpcizfwxTFvri_WFHr_4n2_GvIlIfOeN_K3cNPj8trXU7FkvZUWM24NPq2UcaoRQdHhoVPdc2GxHJxXJIqxdXRcT4VYaI24e0vouGMTC-WFQJ7TCicTeUPnLZpUC7-12Edq51OqcHhekoFupNo0olWVDbc138c7nKEfOh8okhC1Ooe_CXg7XcHcSlfUzawmNHegNBZ8AQpC2_5GyzXBdmqS-QQb4XXhQVgMnD7MmtcOJbpWdi_vDI_JGsAaXyva5HI8bLQwrMQX7-DPGa3djz_Md40tlGHKd2VqznKSgOlD51WHRWCfz7Rc-SQ7TTBu4SuPy9Sx62anovoL4P7kd9mr8rcAZRcCOxHpr554E-BQn_l9PDvmQi5mLqF0umA_GnPoP7nzgOk2HHaIKOW6zsygKS0deQnIYvYLxvLcZREea8saCHOwU4q9-_FN6Mwwj-PU5AU26IYZ4"
//            if (token.isNotEmpty()) {
//                requestBuilder.addHeader("Authorization", token)
//            }

            // Add additional parameters to the request
//            val additionalParams = mapOf(
//                "store" to store,
//                "lang" to lang
//                // Add more parameters as needed
//            )

//            for ((key, value) in additionalParams) {
//                url.addQueryParameter(key, value)
//            }

            val newRequest = requestBuilder.url(url.build()).build()

            return chain.proceed(newRequest)
        }
    }

    private fun addLoggingInterceptor(isLoggingEnabled: Boolean) {
        if (isLoggingEnabled) {
            httpBuilder.addInterceptor(OkHttpProfilerInterceptor())
        }
    }

    private fun setTimeout(builder: OkHttpClient.Builder) {
        builder.connectTimeout(Const.TimeOut.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Const.TimeOut.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.TimeOut.READ_TIMEOUT, TimeUnit.SECONDS)
    }
}