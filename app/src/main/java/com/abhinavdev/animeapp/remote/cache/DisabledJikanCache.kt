package com.abhinavdev.animeapp.remote.cache

import net.sandrohc.jikan.cache.JikanCache
import java.time.OffsetDateTime
import java.util.Optional

/**
 * A dummy implementation of the Jikan cache that disables caching altogether.
 */
class DisabledJikanCache : JikanCache {
    override fun put(key: String, value: Any, expires: OffsetDateTime) {}
    override fun get(key: String): Optional<Any> {
        return Optional.empty()
    }
}
