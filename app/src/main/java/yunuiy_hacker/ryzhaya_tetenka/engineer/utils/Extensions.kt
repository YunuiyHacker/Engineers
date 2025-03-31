package yunuiy_hacker.ryzhaya_tetenka.engineer.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresPermission
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.utils.OneCDateFormat
import yunuiy_hacker.ryzhaya_tetenka.engineer.domain.common.utils.OneCDateFormatWithPlus
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.KOTLIN_PASSWORD
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.KOTLIN_USERNAME
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.ONE_C_PASSWORD
import yunuiy_hacker.ryzhaya_tetenka.engineer.utils.Constants.ONE_C_USERNAME
import java.util.Calendar
import java.util.Date

fun getRetrofitBasicAuthenticationForKotlinString(): String {
    return "Basic " + Base64.encodeToString(
        "$KOTLIN_USERNAME:$KOTLIN_PASSWORD".toByteArray(),
        Base64.NO_WRAP
    )
}

fun getRetrofitBasicAuthenticationForOneCString(): String {
    return "Basic " + Base64.encodeToString(
        "$ONE_C_USERNAME:$ONE_C_PASSWORD".toByteArray(),
        Base64.NO_WRAP
    )
}

@SuppressLint("SimpleDateFormat")
fun Calendar.toOneCDateStringFormat(): String {
    return OneCDateFormat.format(
        this
            .time.time
    )
}

@SuppressLint("SimpleDateFormat")
fun Date.toOneCDateStringFormat(): String {
    return OneCDateFormat.format(
        this.time
    )
}

fun String.toKotlinDate(): Date {
    var date: Date = Date()
    try {
        date = OneCDateFormat.parse(this)
    } catch (e: Exception) {
        try {
            date = OneCDateFormatWithPlus.parse(this)
        } catch (e: Exception) {

        }
    }
    return date
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    result = when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }

    return result
}

fun getConnectivityManager(application: Application): ConnectivityManager {
    return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}