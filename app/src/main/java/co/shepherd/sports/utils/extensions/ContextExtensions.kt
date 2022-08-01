package co.shepherd.sports.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import co.shepherd.sports.R
import java.io.File
import java.util.*
import kotlin.system.exitProcess

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}

fun findBinary(binaryName: String): Boolean {
    var found = false
    if (!found) {
        val places = arrayOf(
            "/sbin/", "/system/bin/", "/system/xbin/",
            "/data/local/xbin/", "/data/local/bin/",
            "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"
        )
        for (where in places) {
            if (File(where + binaryName).exists()) {
                found = true
                break
            }
        }
    }
    return found
}

fun Activity.isDeviceRooted(): Boolean {
    return if (findBinary("su")) {
        AlertDialog.Builder(this)
            .setMessage(R.string.device_rooted)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                exitProcess(0)
            }.show()
        true
    } else {
        false
    }
}

fun getEventDate(data: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var eventDate = format.parse(data)

    val now: Calendar = Calendar.getInstance()
    val smsTime: Calendar = Calendar.getInstance()
    smsTime.setTimeInMillis(eventDate.time)

    return if (now.get(Calendar.DATE) === smsTime.get(Calendar.DATE)) {
        "Today, " + SimpleDateFormat("hh:mm").format(eventDate)
    } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) === 1) {
        "Yesterday, " + SimpleDateFormat("hh:mm").format(eventDate)
    } else {
        SimpleDateFormat("dd.MM.yyyy").format(eventDate)
    }
}

fun getScheduleDate(data: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var eventDate = format.parse(data)

    val now: Calendar = Calendar.getInstance()
    val eventTime: Calendar = Calendar.getInstance()
    eventTime.setTimeInMillis(eventDate.time)

    return if (now.get(Calendar.DATE) === eventTime.get(Calendar.DATE)) {
        "Today, " + SimpleDateFormat("hh:mm").format(eventDate)
    } else if (now.get(Calendar.DATE) - eventTime.get(Calendar.DATE) === 1) {
        "Yesterday, " + SimpleDateFormat("hh:mm").format(eventDate)
    } else if (now.get(Calendar.DATE) - eventTime.get(Calendar.DATE) === 2) {
        "In three days"
    } else {
        "In to days"
    }
}