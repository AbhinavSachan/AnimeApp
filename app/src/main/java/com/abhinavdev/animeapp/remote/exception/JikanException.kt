package com.abhinavdev.animeapp.remote.exception

open class JikanException(override val message: String?, val code: Int? = null) : Exception()