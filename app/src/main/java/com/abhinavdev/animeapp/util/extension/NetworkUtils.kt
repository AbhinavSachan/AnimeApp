package com.abhinavdev.animeapp.util.extension

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.telephony.TelephonyManager

fun Application.hasInternetConnection(): Boolean {
    val connectivityManager = getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            return when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
    return false
}

class NetworkUtils internal constructor(private val mContext: Context) {
    /**
     * Checks if device is connected on network
     */
    val isConnected: Boolean
        get() {
            val info = networkInfo
            return info != null && info.isAvailable && info.isConnected
        }

    /**
     * Checks if there is slow connectivity
     * returns true in cases when there is 2g connection < 100kbPS
     */
    val isConnectedOnSlowNetwork: Boolean
        get() {
            val info = networkInfo
            if (info == null || !info.isConnected) {
                return true
            }
            return !hasFastConnection(info.type, info.subtype)
        }

    /**
     * Get the network info
     */
    private val networkInfo: NetworkInfo?
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetworkInfo
        }

    /**
     * Check if the connection is fast
     * returns true if connected on wifi
     * for mobile, it returns false for 2G connections (< 100 kbps)
     */
    private fun hasFastConnection(type: Int, subType: Int): Boolean {
        return if (type == ConnectivityManager.TYPE_WIFI) {
            true
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            when (subType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps
                TelephonyManager.NETWORK_TYPE_EHRPD -> true // ~ 1-2 Mbps
                TelephonyManager.NETWORK_TYPE_EVDO_B -> true // ~ 5 Mbps
                TelephonyManager.NETWORK_TYPE_HSPAP -> true // ~ 10-20 Mbps
                TelephonyManager.NETWORK_TYPE_IDEN -> false // ~25 kbps
                TelephonyManager.NETWORK_TYPE_LTE -> true // ~ 10+ Mbps
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> {
                    false
                }

                else -> {
                    false
                }
            }
        } else {
            false
        }
    }
}