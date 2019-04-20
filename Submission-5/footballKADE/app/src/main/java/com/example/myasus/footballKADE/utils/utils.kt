package com.example.myasus.footballKADE.utils

import android.util.Log
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun toGMTFormat(date: String?, time:String?): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    Log.d("Masuk","masuk tgl" + date + time)
    formatter.timeZone = TimeZone.getTimeZone("UTC+7")

    val dateTime = "$date $time"
    Log.d("forma", "forma adalah " + dateTime)

    return formatter.parse(dateTime)
}
fun String.dateTimeToFormat(format: String = "yyyy-MM-dd HH:mm:ss"): Long {

    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val date = formatter.parse(this)

    return date.time
}