package com.abhinavdev.animeapp.remote.cache

import java.time.OffsetDateTime
import java.util.Optional

/**
 * Cache abstraction for the Jikan requests.
 *
 *
 * The cache implementation is left to the developer, as to better suit their needs. Please see
 * [https://github.com/SandroHc/reactive-jikan#caching](https://github.com/SandroHc/reactive-jikan#caching)
 * for an example of a caching implementation.
 *
 * @see [https://github.com/SandroHc/reactive-jikan.caching](https://github.com/SandroHc/reactive-jikan.caching)
 * for an example of a caching implementation
 */
interface JikanCache {
    /**
     * Adds a new key to the cache with an optional expiration timestamp.
     *
     *
     * The cache implementation is not guaranteed to support the expiration timestamp. But as a rule of thumb, it is a
     * good idea to evict cache items that have existed for more than 24 hours.
     *
     * @param key the cache key
     * @param value the value
     * @param expires the expiration date
     */
    fun put(key: String?, value: Any?, expires: OffsetDateTime?)

    /**
     * Fetch a key from the cache.
     *
     * @param key the cache key
     * @return the object in the cache
     */
    operator fun get(key: String?): Optional<Any?>?
}
