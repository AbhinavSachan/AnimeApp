package com.abhinavdev.animeapp.remote.connection

import com.abhinavdev.animeapp.util.extension.log
import okhttp3.Interceptor
import okhttp3.Response

class RateLimitInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())

        //See: https://jikan.docs.apiary.io/#introduction/http-response
        if (!response.isSuccessful && response.code == 429) {
            try {
                log { "You are being rate limited or Jikan is being rate limited by MyAnimeList, retrying in 4 seconds..." }
                Thread.sleep(4000L)
            } catch (e: InterruptedException) {
                log { e.toString() }
            }

            response = chain.proceed(chain.request())
        }

        return response
    }
}