package com.matinfard.musicmanagement.core.platform

import android.content.Context
import android.net.ConnectivityManager

open class NetworkHandler(private val context: Context) {

    fun isConnected(): Boolean? {
        val networkInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)?.activeNetworkInfo

        return networkInfo?.isConnectedOrConnecting
    }

}