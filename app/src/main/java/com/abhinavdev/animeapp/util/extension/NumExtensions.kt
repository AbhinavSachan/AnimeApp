package com.abhinavdev.animeapp.util.extension

import com.abhinavdev.animeapp.util.Const.Other.Companion.UNKNOWN_CHAR
import com.abhinavdev.animeapp.util.extension.StringExtensions.toStringOrNull
import java.text.NumberFormat

object NumExtensions {

    private val numberFormat: NumberFormat = NumberFormat.getInstance()

    fun Number.format(): String? = try {
        numberFormat.format(this)
    } catch (e: IllegalArgumentException) {
        null
    }

    /**
     * @return if true 1 else 0
     */
    fun Boolean?.toInt(): Int = if (this == true) 1 else 0

    /**
     * Returns a string representation of the Integer. If the Integer is `<= 0` returns `null`.
     * Can be called with a null receiver, in which case it returns `null`.
     */
    fun Int?.toStringPositiveValueOrNull() = if (this == 0) null else this.toStringOrNull()

    fun Int?.toStringOrZero() = this?.toString() ?: "0"

    fun Int?.toStringOrUnknown() = this?.toString() ?: UNKNOWN_CHAR

    /**
     * Returns a string representation of the Integer.
     * If the Integer is `<= 0` or `null` returns `"─"`.
     */
    fun Int?.toStringPositiveValueOrUnknown() =
        if (this.isGreaterThanZero()) this.toStringOrUnknown() else UNKNOWN_CHAR

    private fun Int?.isGreaterThanZero() = this != null && this > 0

    fun Float?.toStringOrZero() = this?.toString() ?: "0.0"

    fun Float?.toStringOrUnknown() = this?.toString() ?: UNKNOWN_CHAR

    /**
     * Returns a string representation of the Float.
     * If the Float is `<= 0` or `null` returns `"─"`.
     */
    fun Float?.toStringPositiveValueOrUnknown() =
        if (this.isGreaterThanZero()) this.toStringOrUnknown() else UNKNOWN_CHAR

    private fun Float?.isGreaterThanZero() = this != null && this > 0f

}