package com.example.mobile_applications_project_put.functions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object UserUtility {
    fun calculateBMI(weight: Double, height: Int?): Double {
        val heightInMeters = height?.div(100.0)

        if (heightInMeters != null) {
            return weight / (heightInMeters * heightInMeters)
        }
        return 0.0
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}
